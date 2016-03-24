/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlg_tests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import jgaliweather.algorithm.weather_operators.WindOperator;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.PartitionReader;
import jgaliweather.configuration.template_reader.TemplateReader;
import jgaliweather.configuration.variable_reader.VariableReader;
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

/* Tests method that generates sentences describing wind predictions*/
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

        TemplateReader tr = new TemplateReader();
        tr.parseFile("Configuration/Languages/espanol.xml");

        VariableReader vr = new VariableReader();
        vr.parseFile("Configuration/variables.xml");

        PartitionReader pr = new PartitionReader();
        pr.parseFile("Configuration/partitions.xml");
        HashMap<String, Partition> partitions = pr.getPartitions();

        Variable curr_var = new Variable("Meteoro");

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

        WindGenerator nssg = new WindGenerator(tr.getLabelsets(), Calendar.getInstance(), w_output);

        String salida = nssg.parseAndGenerate();

        System.out.println(salida);
        Assert.assertEquals("Viento fuerte del Sudeste del miércoles por la mañana al jueves por la tarde; muy fuerte del Noroeste desde el viernes por la mañana, cambiando a fuerte del Norte el viernes por la tarde y a muy fuerte del Noroeste el viernes por la noche; y fuerte del Norte desde el sábado por la tarde, cambiando a muy fuerte del Noroeste el sábado por la noche.", salida);
    }
}
