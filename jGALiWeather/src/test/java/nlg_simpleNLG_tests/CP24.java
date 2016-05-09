/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlg_simpleNLG_tests;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jgaliweather.algorithm.weather_operators.TemperatureOperator;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.PartitionReader;
import jgaliweather.configuration.template_reader.TemplateReader;
import jgaliweather.configuration.variable_reader.VariableReader;
import jgaliweather.data.data_structures.Temperature;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
import jgaliweather.nlg_simpleNLG.nlg_generators.TemperatureGenerator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.realiser.english.Realiser;

/* Tests TemperatureGenerator*/
public class CP24 {
    
    public CP24() {
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
            TemplateReader tr = new TemplateReader();
            tr.parseFile("Configuration/Languages/english.xml");
            
            VariableReader vr = new VariableReader();
            vr.parseFile("Configuration/variables.xml");
            
            PartitionReader pr = new PartitionReader();
            pr.parseFile("Configuration/partitions.xml");
            HashMap<String, Partition> partitions = pr.getPartitions();
            
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
            
            TemperatureGenerator t_nlg = new TemperatureGenerator(tr.getLabelsets().get("T"), tr.getLabelsets().get("CT"), tr.getLabelsets().get("V"), tr.getLabelsets().get("VAR"), partitions.get("VAR"), t_output.getClim_eval(), t_output.getVariation_eval(), t_output.getVariability_eval(), nlgFactory);
            
            String salida = realiser.realiseSentence(t_nlg.parseAndGenerate()).replaceAll("\\s+", " ").trim();
            
            System.out.println(salida);
            Assert.assertEquals(null, salida);
        } catch (Exception ex) {
            Logger.getLogger(CP24.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
