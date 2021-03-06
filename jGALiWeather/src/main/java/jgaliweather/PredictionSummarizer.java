
/*
    This module contains the PredictionSummarizer class, which implements the natural language generation task for short-term weather forecasting.
 */
package jgaliweather;

import com.fuzzylite.term.Trapezoid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import jgaliweather.algorithm.weather_operators.FogOperator;
import jgaliweather.algorithm.weather_operators.RainOperator;
import jgaliweather.algorithm.weather_operators.SkyStateAOperator;
import jgaliweather.algorithm.weather_operators.SkyStateBOperator;
import jgaliweather.algorithm.weather_operators.TemperatureOperator;
import jgaliweather.algorithm.weather_operators.WindOperator;
import jgaliweather.configuration.configuration_reader.ConfigurationReader;
import jgaliweather.configuration.configuration_reader.DatabaseConfiguration;
import jgaliweather.configuration.configuration_reader.TemperatureReader;
import jgaliweather.configuration.logger.GALiLogger;
import jgaliweather.configuration.partition_reader.CrispInterval;
import jgaliweather.configuration.partition_reader.FuzzySet;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.PartitionReader;
import jgaliweather.configuration.template_reader.TemplateReader;
import jgaliweather.configuration.variable_reader.VariableReader;
import jgaliweather.configuration.variable_reader.XMLVariable;
import jgaliweather.data.data_filters.DataFilter;
import jgaliweather.data.data_filters.DataLengthFilter;
import jgaliweather.data.data_filters.MeteorologicFilter;
import jgaliweather.data.data_structures.Location;
import jgaliweather.data.data_structures.Temperature;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
import jgaliweather.database.DatabaseConnector;
import jgaliweather.nlg.DescriptionAggregator;
import jgaliweather.nlg.nlg_generators.FogGenerator;
import jgaliweather.nlg.nlg_generators.RainGenerator;
import jgaliweather.nlg.nlg_generators.SkyCoverageGeneratorLevel1;
import jgaliweather.nlg.nlg_generators.SkyCoverageGeneratorLevel2;
import jgaliweather.nlg.nlg_generators.TemperatureGenerator;
import jgaliweather.nlg.nlg_generators.WindGenerator;
import org.apache.commons.lang3.SerializationUtils;
import org.javatuples.Pair;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.realiser.english.Realiser;

/*
    This class implements the generation of short-term weather forecast reports, which is composed of several subtasks:
        - Reading the configuration files
        - Extracting air quality and weather data from the database source
        - Filtering the extracted data
        - Generating linguistic descriptions from data
        - Generating natural language descriptions
        - Updating the database with the new comments

    Each subtask corresponds to a method in this class (except the initial filtering, the linguistic description generation and the natural language generation, which are included in a single method).
 */
public class PredictionSummarizer {

    private Pair<Integer, Integer> WIND_INTERVAL;
    private String config_file;
    private String log_directory;
    private Calendar current_date;
    private HashMap<String, Location> locations;
    private ConfigurationReader configuration;
    private TemperatureReader temperatures;
    private HashMap<String, XMLVariable> variables;
    private DatabaseConfiguration conn_data;
    private TemplateReader templates;
    private HashMap<String, Partition> partitions;

    public PredictionSummarizer(String config_file, String log_directory) {

        this.WIND_INTERVAL = new Pair(317, 332);

        this.config_file = config_file;
        this.log_directory = log_directory;

        this.current_date = Calendar.getInstance();
        this.current_date.setTime(new Date());
        this.locations = new HashMap();
        this.configuration = null;
        this.temperatures = null;
        this.variables = null;
        this.conn_data = null;
        this.partitions = null;
        GALiLogger.initLogger(log_directory);
    }

    /*
        Reads configuration files and natural language templates, used in subsequent methods.
     */
    private void configure() {

        try {
            this.configuration = new ConfigurationReader();
            this.configuration.parseFile(this.config_file);
            this.conn_data = this.configuration.getDb_data().get("prediccion");
        } catch (Exception e) {
            GALiLogger.getLogger().log(Level.SEVERE, "\nError trying to parse %s:\n %s \nExiting...", config_file);
            System.exit(1);
        }

        try {
            VariableReader vr = new VariableReader();
            vr.parseFile(this.configuration.getInpaths().get("variables"));
            this.variables = vr.getVariables();
        } catch (Exception e) {
            GALiLogger.getLogger().log(Level.SEVERE, "\nError trying to parse %s:\n %s \nExiting...", this.configuration.getInpaths().get("variables"));
            System.exit(1);
        }

        try {
            PartitionReader prr = new PartitionReader();
            prr.parseFile(this.configuration.getInpaths().get("partitions"));
            this.partitions = prr.getPartitions();
        } catch (Exception e) {
            GALiLogger.getLogger().log(Level.SEVERE, "\nError trying to parse %s:\n %s \nExiting...", this.configuration.getInpaths().get("partitions"));
            System.exit(1);
        }

        try {
            TemplateReader tr = new TemplateReader();
            tr.parseFile(this.configuration.getInpaths().get("templates"));
            this.templates = tr;
        } catch (Exception e) {
            GALiLogger.getLogger().log(Level.SEVERE, "\nError trying to parse %s:\n %s \nExiting...", this.configuration.getInpaths().get("templates"));
            System.exit(1);
        }
    }

    /*
        Extracts weather forecast data from MeteoGalicia's database. The performed queries include:
            - The list of municipalities
            - The latest available forecast
            - The forecast data
            - Temperature climatic data for each municipality
     */
    private void extractData(String id, ArrayList<String> dates) {

        DatabaseConnector dbc = null;

        try {
            dbc = new DatabaseConnector(this.conn_data);
        } catch (Exception e) {
            GALiLogger.getLogger().log(Level.SEVERE, "\nForecast database connection with %s could not be established: %s\nExiting...", this.conn_data.getHost());
            System.exit(1);
        }

        try {
            int month = current_date.get(Calendar.MONTH) + 1;
            temperatures = new TemperatureReader();
            temperatures.parseFile(this.configuration.getInpaths().get("temperatures"));

            ArrayList<Pair<Integer, String>> db_locations;
            if (id == null) {
                db_locations = dbc.retrieveLocations();
            } else {
                db_locations = dbc.retrieveLocation(id);
            }

            for (Pair<Integer, String> l : db_locations) {
                Location new_loc = new Location(l.getValue1(), l.getValue0());

                /*ArrayList<String> dates = new ArrayList();
                dates.add("2015-07-25");
                dates.add("2015-07-26");
                dates.add("2015-07-27");
                dates.add("2015-07-28");*/
                HashMap<String, ArrayList<Integer>> values = dbc.retrieveVariableDataForLocation(l.getValue0(), dates);

                Iterator it = this.variables.entrySet().iterator();
                Iterator it1 = values.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    XMLVariable aux = (XMLVariable) pair.getValue();

                    Map.Entry pair1 = (Map.Entry) it1.next();
                    ArrayList<Integer> data = (ArrayList<Integer>) pair1.getValue();

                    Variable curr_var = new Variable(aux.getName());
                    for (int i = 0; i < data.size(); i++) {
                        curr_var.getValues().add(new Value(data.get(i), i));
                    }
                    new_loc.getVariables().put(curr_var.getName(), curr_var);
                    new_loc.setClimatic_data(temperatures.retrieveClimaticDataForLocation(new_loc.getLid(), month)); // Muy lento
                    this.locations.put(new_loc.getName(), new_loc);
                }
            }

        } catch (Exception e) {
            GALiLogger.getLogger().log(Level.SEVERE, "\nForecast database query on %s could not be completed.\nExiting...", this.conn_data.getHost());
            System.exit(1);
        } finally {
            dbc.closeConnection();
        }
    }

    /*
        Filters erroneous data, sets up both climatic temperature and cloud coverage temporal partitions. 
        Generates the air quality linguistic descriptions and converts these descriptions into natural
     */
    private void applyAlgorithm() {

        // Data filtering, climatic partition creation and cloud coverage temporal partition creation
        Iterator it = this.locations.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Location l = (Location) pair.getValue();
            try {
                Iterator it1 = l.getVariables().entrySet().iterator();
                while (it1.hasNext()) {
                    Map.Entry pair1 = (Map.Entry) it1.next();
                    Variable v = (Variable) pair1.getValue();
                    DataFilter f = new DataFilter(v, l);
                    f.filter();
                }
                MeteorologicFilter mf = new MeteorologicFilter(l.getVariables().get("Meteoro"));
                mf.filter();

                Iterator it2 = l.getVariables().entrySet().iterator();
                while (it2.hasNext()) {
                    Map.Entry pair2 = (Map.Entry) it2.next();
                    Variable v = (Variable) pair2.getValue();
                    DataLengthFilter dlf = new DataLengthFilter(l, this.variables.get(v.getName()), v);
                    dlf.filter();
                }
            } catch (Exception e) {
                GALiLogger.getLogger().log(Level.SEVERE, "\nFilter error:\n %s \n Exiting...", e);
                System.exit(1);
            }

            l.setMax_climate_partition(SerializationUtils.clone(partitions.get("T")));
            l.setMin_climate_partition(SerializationUtils.clone(partitions.get("T")));

            for (int i = 0; i < partitions.get("T").getSets().size(); i++) {
                CrispInterval min = (CrispInterval) l.getMin_climate_partition().getSets().get(i);
                CrispInterval max = (CrispInterval) l.getMax_climate_partition().getSets().get(i);
                CrispInterval partition = (CrispInterval) partitions.get("T").getSets().get(i);

                min.setA(l.getClimatic_data().getAverageMin() + l.getClimatic_data().getDeviationMin() * partition.getA());
                min.setB(l.getClimatic_data().getAverageMin() + l.getClimatic_data().getDeviationMin() * partition.getB());
                max.setA(l.getClimatic_data().getAverageMax() + l.getClimatic_data().getDeviationMax() * partition.getA());
                max.setB(l.getClimatic_data().getAverageMax() + l.getClimatic_data().getDeviationMax() * partition.getB());

                l.getMin_climate_partition().getSets().set(i, min);
                l.getMax_climate_partition().getSets().set(i, max);
            }
        }

        // Definition of term-length dependant partitions
        for (int i = 0; i < this.partitions.get("SSFTP").getSets().size(); i++) {

            FuzzySet fs = (FuzzySet) this.partitions.get("SSFTP").getSets().get(i);
            double a = fs.getFunction().getVertexA();
            double b = fs.getFunction().getVertexB();
            double c = fs.getFunction().getVertexC();
            double d = fs.getFunction().getVertexD();

            a = (double) Math.round(a * (this.variables.get("Meteoro").getActual_data_length() - 1) * 100) / 100;
            b = (double) Math.round(b * (this.variables.get("Meteoro").getActual_data_length() - 1) * 100) / 100;
            c = (double) Math.round(c * (this.variables.get("Meteoro").getActual_data_length() - 1) * 100) / 100;
            d = (double) Math.round(d * (this.variables.get("Meteoro").getActual_data_length() - 1) * 100) / 100;

            this.partitions.get("SSFTP").getSets().set(i, new FuzzySet(fs.getName(), new Trapezoid(fs.getName(), a, b, c, d)));
        }

        Lexicon lexicon = Lexicon.getDefaultLexicon();
        NLGFactory nlgFactory = new NLGFactory(lexicon);
        Realiser realiser = new Realiser(lexicon);

        // Execution of linguistic description operators
        Iterator it3 = this.locations.entrySet().iterator();
        while (it3.hasNext()) {
            Map.Entry pair = (Map.Entry) it3.next();
            Location l = (Location) pair.getValue();

            SkyStateAOperator nssa_op = new SkyStateAOperator(this.partitions.get("C"), this.partitions.get("SSFTP"), l.getVariables().get("Meteoro"));
            SkyStateBOperator nssb_op = new SkyStateBOperator(partitions.get("C"), partitions.get("CP"), l.getVariables().get("Meteoro"));
            RainOperator r_op = new RainOperator(partitions.get("R"), l.getVariables().get("Meteoro"));
            FogOperator f_op = new FogOperator(partitions.get("FOG").getSets().get(0), l.getVariables().get("Meteoro"));
            WindOperator w_op = new WindOperator(WIND_INTERVAL, l.getVariables().get("Viento"));
            TemperatureOperator t_op = new TemperatureOperator(partitions.get("V"), l.getMax_climate_partition(), l.getMin_climate_partition(), partitions.get("VAR"), l.getVariables().get("Temperatura"));

            String nssa_output = nssa_op.applyOperator();
            ArrayList<String> r_output = r_op.applyOperator();
            ArrayList<String> w_output = w_op.applyOperator();
            Temperature t_output = t_op.applyOperator();
            HashMap<Integer, ArrayList<ArrayList<Integer>>> f_output = f_op.applyOperator();

            // Conversion of linguistic descriptions into natural language texts
            SkyCoverageGeneratorLevel1 nssg = new SkyCoverageGeneratorLevel1(templates.getLabelsets().get("C"), partitions.get("SSFTP"), templates.getLabelsets().get("SSFTP"), nssa_output, nlgFactory);
            NLGElement nss_nlg = nssg.parseAndGenerate();

            if (nss_nlg == null) {
                SkyCoverageGeneratorLevel2 anssg = new SkyCoverageGeneratorLevel2(templates.getLabelsets().get("C"), partitions.get("C"), nssb_op.applyOperator(), nlgFactory);
                nss_nlg = anssg.generate();
            }

            RainGenerator rg = new RainGenerator(templates.getLabelsets(), current_date, variables.get("Meteoro").getActual_data_length(), r_output, nlgFactory);
            NLGElement r_nlg = rg.parseAndGenerate();

            TemperatureGenerator tg = new TemperatureGenerator(templates.getLabelsets().get("CT"), templates.getLabelsets().get("V"), templates.getLabelsets().get("VAR"), 
                    partitions.get("VAR"), t_output.getClim_eval(), t_output.getVariation_eval(), t_output.getVariability_eval(), nlgFactory);
            NLGElement t_nlg = tg.parseAndGenerate();

            WindGenerator wg = new WindGenerator(templates.getLabelsets(), current_date, w_output, nlgFactory);
            NLGElement w_nlg = wg.parseAndGenerate();

            FogGenerator fg = new FogGenerator(f_output, templates.getLabelsets().get("FOG"), templates.getLabelsets().get("PD"), templates.getLabelsets().get("DW"),
                    current_date, variables.get("Meteoro").getActual_data_length(), nlgFactory);
            NLGElement f_nlg = fg.generate();

            l.getSummaries().put("eng", new DescriptionAggregator(nss_nlg, r_nlg, t_nlg, w_nlg, f_nlg, nlgFactory, realiser));

        }

    }

    private void exportSummaries() {

        DatabaseConnector dbc = null;

        try {
            dbc = new DatabaseConnector(this.conn_data);
        } catch (Exception e) {
            GALiLogger.getLogger().log(Level.SEVERE, "\nForecast database connection with %s could not be established: %s\nExiting...", this.conn_data.getHost());
            System.exit(1);
        }

        SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");

        Iterator it = this.locations.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Location l = (Location) pair.getValue();

            dbc.saveData(l.getLid() + "", sdt.format(current_date.getTime()), l.getSummaries().get("eng").mergeDescription());

            System.out.println(l.getSummaries().get("eng").mergeDescription());
        }
    }

    public String generateTextualForecasts() {

        GALiLogger.getLogger().info("GALiWeather Short-Term Forecast Description Generation");
        GALiLogger.getLogger().info("Loading configuration data...");
        configure();

        GALiLogger.getLogger().info("Retrieving input forecast data...");
        ArrayList<String> dates = new ArrayList();
        dates.add("2015-07-15");
        dates.add("2015-07-16");
        dates.add("2015-07-17");
        dates.add("2015-07-18");
        extractData(null, dates);

        GALiLogger.getLogger().info("Generating natural language forecasts...");
        applyAlgorithm();

        GALiLogger.getLogger().info("Exporting forecast text files...");
        exportSummaries();

        GALiLogger.getLogger().info("All forecasts have been generated.");

        Iterator it = this.locations.entrySet().iterator();
        Location l = null;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            l = (Location) pair.getValue();
        }

        return l.getSummaries().get("eng").mergeDescription();
    }

    public String generateTextualForecastsTest(String id, ArrayList<String> dates, Calendar date) {

        current_date = date;

        GALiLogger.getLogger().info("GALiWeather Short-Term Forecast Description Generation");
        GALiLogger.getLogger().info("Loading configuration data...");
        configure();

        GALiLogger.getLogger().info("Retrieving input forecast data...");
        extractData(id, dates);

        GALiLogger.getLogger().info("Generating natural language forecasts...");
        applyAlgorithm();

        GALiLogger.getLogger().info("Exporting forecast text files...");
        exportSummaries();

        GALiLogger.getLogger().info("All forecasts have been generated.");

        Iterator it = this.locations.entrySet().iterator();
        Location l = null;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            l = (Location) pair.getValue();
        }

        return l.getSummaries().get("eng").mergeDescription();
    }

}
