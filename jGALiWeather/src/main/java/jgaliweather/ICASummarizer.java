
package jgaliweather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import jgaliweather.configuration.configuration_reader.ConfigurationReader;
import jgaliweather.configuration.configuration_reader.DatabaseConfiguration;
import jgaliweather.configuration.configuration_reader.LanguageConfiguration;
import jgaliweather.configuration.logger.GALiLogger;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.PartitionReader;
import jgaliweather.configuration.template_reader.TemplateReader;
import jgaliweather.configuration.variable_reader.VariableReader;
import jgaliweather.configuration.variable_reader.XMLVariable;
import jgaliweather.data.data_structures.Location;
import jgaliweather.database.DatabaseConnector;
import org.javatuples.Pair;

/*
This class implements the generation of air quality reports, which is composed of several subtasks:
        - Reading the configuration files
        - Extracting air quality and weather data from the database source
        - Filtering the extracted data
        - Generating linguistic descriptions from data
        - Generating natural language descriptions
        - Updating the database with the new comments

    Each subtask corresponds to a method in this class (except the initial filtering, the linguistic description generation and the natural language generation, which are included in a single method).
*/
public class ICASummarizer {

    private int MAX_LENGTH;
    private Pair<Integer, Integer> WIND_INTERVAL;
    private String config_file;
    private String log_directory;
    private String current_date;
    private HashMap<String, Location> locations;
    private ConfigurationReader configuration;
    private HashMap<String, XMLVariable> variables;
    private DatabaseConfiguration conn_data;
    private TemplateReader templates;
    private HashMap<String, Partition> partitions;
    private LanguageConfiguration lng_data;
    private String output_dir;
    
    public ICASummarizer(String config_file, String log_directory) {

        this.MAX_LENGTH = 9;
        this.WIND_INTERVAL = new Pair(309, 332);

        this.config_file = config_file;
        this.log_directory = log_directory;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        this.current_date = sdf.format(now);
        this.locations = new HashMap();
        this.configuration = null;
        this.variables = null;
        this.conn_data = null;
        this.partitions = null;
        this.lng_data = null;
        this.output_dir = null;
        GALiLogger.initLogger(log_directory);
    }
    
    /*
        Reads configuration files and natural language templates, used in subsequent methods.
     */
    private void configure() {

        try {
            this.configuration = new ConfigurationReader();
            this.configuration.parseFile(this.config_file);
            this.lng_data = this.configuration.getLng_data();
            this.conn_data = this.configuration.getDb_data().get("prediccion");
            this.output_dir = this.configuration.getOutpaths().get("output_dir");
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
            tr.parseFile(this.lng_data.getLanguages().get(0).getPath());
            this.templates = tr;
        } catch (Exception e) {
            GALiLogger.getLogger().log(Level.SEVERE, "\nError trying to parse %s:\n %s \nExiting...", this.lng_data.getLanguages().get(0).getPath());
            System.exit(1);
        }
    }
    
    /*
        Extracts both air quality index data and weather forecast data from MeteoGalicia's database. The performed queries include:
            - The list of municipalities
            - The latest available weather forecast data
            - The latest available air quality data
    */
     private void extractData() {
          DatabaseConnector dbc = null;

        try {
            dbc = new DatabaseConnector(this.conn_data);
        } catch (Exception e) {
            GALiLogger.getLogger().log(Level.SEVERE, "\nForecast database connection with %s could not be established: %s\nExiting...", this.conn_data.getHost());
            System.exit(1);
        }
     }
    
}
