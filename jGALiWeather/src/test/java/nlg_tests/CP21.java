/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlg_tests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jgaliweather.algorithm.weather_operators.RainOperator;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.PartitionReader;
import jgaliweather.configuration.template_reader.TemplateReader;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
import jgaliweather.nlg.nlg_generators.RainGenerator;
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

/* Tests RainGenerator */
public class CP21 {
    
    public CP21() {
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

            Variable curr_var = new Variable("Meteoro");

            curr_var.getValues().add(new Value(104, 0));
            curr_var.getValues().add(new Value(103, 1));
            curr_var.getValues().add(new Value(106, 2));
            curr_var.getValues().add(new Value(106, 3));
            curr_var.getValues().add(new Value(101, 4));
            curr_var.getValues().add(new Value(103, 5));
            curr_var.getValues().add(new Value(102, 6));
            curr_var.getValues().add(new Value(102, 7));
            curr_var.getValues().add(new Value(102, 8));
            curr_var.getValues().add(new Value(103, 9));
            curr_var.getValues().add(new Value(108, 10));
            curr_var.getValues().add(new Value(108, 11));

            RainOperator r_op = new RainOperator(partitions.get("R"), curr_var);
            
            ArrayList<String> r_output = r_op.applyOperator();

            Lexicon lexicon = Lexicon.getDefaultLexicon();
            NLGFactory nlgFactory = new NLGFactory(lexicon);
            Realiser realiser = new Realiser(lexicon);

            Calendar current_date = Calendar.getInstance();
            current_date.setTime(new Date());
            
            RainGenerator rg = new RainGenerator(templates.getLabelsets(), current_date, 12, r_output, nlgFactory);
            NLGElement r_nlg = rg.parseAndGenerate();

            Assert.assertEquals("Precipitations are expected on Monday.", realiser.realiseSentence(r_nlg));

        } catch (Exception ex) {
            Logger.getLogger(CP17.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
