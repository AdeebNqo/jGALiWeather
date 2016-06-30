/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlg_tests;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jgaliweather.algorithm.weather_operators.TemperatureOperator;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.PartitionReader;
import jgaliweather.configuration.template_reader.TemplateReader;
import jgaliweather.data.data_structures.Temperature;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
import jgaliweather.nlg.nlg_generators.TemperatureGenerator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.realiser.english.Realiser;

/* Tests temperatureGenerator */
public class CP19 {

    public CP19() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test() {

        try {
            PartitionReader pr = new PartitionReader();
            pr.parseFile("Configuration/partitions.xml");
            HashMap<String, Partition> partitions = pr.getPartitions();

            TemplateReader templates = new TemplateReader();
            templates.parseFile("Configuration/templates.xml");

            Variable curr_var = new Variable("Temperatura");

            curr_var.getValues().add(new Value(15, 0));
            curr_var.getValues().add(new Value(16, 1));
            curr_var.getValues().add(new Value(17, 2));
            curr_var.getValues().add(new Value(15, 3));
            curr_var.getValues().add(new Value(15, 4));
            curr_var.getValues().add(new Value(16, 5));
            curr_var.getValues().add(new Value(17, 6));
            curr_var.getValues().add(new Value(15, 7));

            Partition max_climate_partition = partitions.get("T");
            Partition min_climate_partition = partitions.get("T");

            TemperatureOperator top = new TemperatureOperator(partitions.get("V"), max_climate_partition, min_climate_partition, partitions.get("VAR"), curr_var);

            Temperature t_output = top.applyOperator();

            Lexicon lexicon = Lexicon.getDefaultLexicon();
            NLGFactory nlgFactory = new NLGFactory(lexicon);
            Realiser realiser = new Realiser(lexicon);

            TemperatureGenerator tg = new TemperatureGenerator(templates.getLabelsets().get("CT"), templates.getLabelsets().get("V"), templates.getLabelsets().get("VAR"),
                    partitions.get("VAR"), t_output.getClim_eval(), t_output.getVariation_eval(), t_output.getVariability_eval(), nlgFactory);
            NLGElement t_nlg = tg.parseAndGenerate();

            Assert.assertEquals("Temperature will be very high for this period of the year, with minimums in slight increase although they will oscillate and maximums without changes.", realiser.realiseSentence(t_nlg));

        } catch (Exception ex) {
            Logger.getLogger(CP17.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
