/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlg_tests;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jgaliweather.algorithm.weather_operators.SkyStateBOperator;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.PartitionReader;
import jgaliweather.configuration.template_reader.TemplateReader;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
import jgaliweather.nlg.nlg_generators.SkyCoverageGeneratorLevel2;
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

/* Tests SkyCoverageGeneratorLevel2 */
public class CP17 {

    public CP17() {
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

            curr_var.getValues().add(new Value(103, 0));
            curr_var.getValues().add(new Value(103, 1));
            curr_var.getValues().add(new Value(101, 2));
            curr_var.getValues().add(new Value(101, 3));
            curr_var.getValues().add(new Value(101, 4));
            curr_var.getValues().add(new Value(101, 5));
            curr_var.getValues().add(new Value(101, 6));
            curr_var.getValues().add(new Value(101, 7));
            curr_var.getValues().add(new Value(101, 8));
            curr_var.getValues().add(new Value(101, 9));
            curr_var.getValues().add(new Value(103, 10));
            curr_var.getValues().add(new Value(101, 11));

            SkyStateBOperator nssb_op = new SkyStateBOperator(partitions.get("C"), partitions.get("CP"), curr_var);

            Lexicon lexicon = Lexicon.getDefaultLexicon();
            NLGFactory nlgFactory = new NLGFactory(lexicon);
            Realiser realiser = new Realiser(lexicon);

            SkyCoverageGeneratorLevel2 anssg = new SkyCoverageGeneratorLevel2(templates.getLabelsets().get("C"), partitions.get("C"), nssb_op.applyOperator(), nlgFactory);
            NLGElement nss_nlg = anssg.generate();

            Assert.assertEquals("Clear skies in general for the next few days, although it will occasionally be partly cloudy.", realiser.realiseSentence(nss_nlg));

        } catch (Exception ex) {
            Logger.getLogger(CP17.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
