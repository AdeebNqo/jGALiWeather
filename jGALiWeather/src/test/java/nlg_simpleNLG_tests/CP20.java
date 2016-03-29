/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlg_simpleNLG_tests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import jgaliweather.algorithm.weather_operators.RainOperator;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.PartitionReader;
import jgaliweather.configuration.template_reader.TemplateReader;
import jgaliweather.configuration.variable_reader.VariableReader;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
import jgaliweather.nlg_simpleNLG.nlg_generators.RainGenerator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/* Tests method that generates sentences describing rain predictions*/
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

        TemplateReader tr = new TemplateReader();
        tr.parseFile("Configuration/Languages/english.xml");

        VariableReader vr = new VariableReader();
        vr.parseFile("Configuration/variables.xml");

        PartitionReader pr = new PartitionReader();
        pr.parseFile("Configuration/partitions.xml");
        HashMap<String, Partition> partitions = pr.getPartitions();

        Variable curr_var = new Variable("Meteoro");

        curr_var.getValues().add(new Value(102, 0));
        curr_var.getValues().add(new Value(101, 1));
        curr_var.getValues().add(new Value(113, 2));
        curr_var.getValues().add(new Value(104, 3));
        curr_var.getValues().add(new Value(117, 4));
        curr_var.getValues().add(new Value(111, 5));
        curr_var.getValues().add(new Value(108, 6));
        curr_var.getValues().add(new Value(108, 7));
        curr_var.getValues().add(new Value(108, 8));
        curr_var.getValues().add(new Value(107, 9));
        curr_var.getValues().add(new Value(118, 10));
        curr_var.getValues().add(new Value(104, 11));

        RainOperator r_op = new RainOperator(partitions.get("R"), curr_var);

        ArrayList<String> r_output = r_op.applyOperator();

        RainGenerator nssg = new RainGenerator(tr.getLabelsets(), Calendar.getInstance(), vr.getVariables().get("Meteoro").getActual_data_length(), r_output);

        String salida = nssg.parseAndGenerate();

        System.out.println(salida);
        Assert.assertEquals("Precipitations are expected everyday, which can be stormy on Tuesday afternoon.", salida);
    }
}
