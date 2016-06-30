/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlg_tests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jgaliweather.algorithm.weather_operators.WindOperator;
import jgaliweather.configuration.template_reader.TemplateReader;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
import jgaliweather.nlg.nlg_generators.WindGenerator;
import org.javatuples.Pair;
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

/* Tests WindGenerator */
public class CP20 {

    public CP20() {
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

            TemplateReader templates = new TemplateReader();
            templates.parseFile("Configuration/templates.xml");

            Variable curr_var = new Variable("Viento");

            curr_var.getValues().add(new Value(320, 0));
            curr_var.getValues().add(new Value(320, 1));
            curr_var.getValues().add(new Value(320, 2));
            curr_var.getValues().add(new Value(320, 3));
            curr_var.getValues().add(new Value(320, 4));
            curr_var.getValues().add(new Value(301, 5));
            curr_var.getValues().add(new Value(332, 6));
            curr_var.getValues().add(new Value(317, 7));
            curr_var.getValues().add(new Value(332, 8));
            curr_var.getValues().add(new Value(302, 9));
            curr_var.getValues().add(new Value(317, 10));
            curr_var.getValues().add(new Value(332, 11));

            Pair<Integer, Integer> WIND_INTERVAL = new Pair(317, 332);

            WindOperator w_op = new WindOperator(WIND_INTERVAL, curr_var);

            ArrayList<String> w_output = w_op.applyOperator();

            Lexicon lexicon = Lexicon.getDefaultLexicon();
            NLGFactory nlgFactory = new NLGFactory(lexicon);
            Realiser realiser = new Realiser(lexicon);

            Calendar current_date = Calendar.getInstance();
            current_date.setTime(new Date());

            WindGenerator wg = new WindGenerator(templates.getLabelsets(), current_date, w_output, nlgFactory);
            NLGElement w_nlg = wg.parseAndGenerate();

            Assert.assertEquals("Strong Southeast wind from Friday morning to Saturday afternoon, very strong Northwest from Sunday morning, changing to strong North on Sunday afternoon and to very strong Northwest on Sunday night and strong North from Monday afternoon, changing to very strong Northwest on Monday night.", realiser.realiseSentence(w_nlg));

        } catch (Exception ex) {
            Logger.getLogger(CP17.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
