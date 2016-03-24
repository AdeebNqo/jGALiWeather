package algorithm_tests.nlg_tests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import jgaliweather.algorithm.weather_operators.FogOperator;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.PartitionReader;
import jgaliweather.configuration.template_reader.TemplateReader;
import jgaliweather.configuration.variable_reader.VariableReader;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
import jgaliweather.nlg.nlg_generators.FogGenerator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/* Tests method that generates sentences describing fog predictions*/
public class CP22 {

    public CP22() {
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

        curr_var.getValues().add(new Value(106, 0));
        curr_var.getValues().add(new Value(115, 1));
        curr_var.getValues().add(new Value(120, 2));
        curr_var.getValues().add(new Value(125, 3));
        curr_var.getValues().add(new Value(106, 4));
        curr_var.getValues().add(new Value(105, 5));
        curr_var.getValues().add(new Value(106, 6));
        curr_var.getValues().add(new Value(106, 7));
        curr_var.getValues().add(new Value(130, 8));
        curr_var.getValues().add(new Value(106, 9));
        curr_var.getValues().add(new Value(101, 10));
        curr_var.getValues().add(new Value(106, 11));

        FogOperator f_op = new FogOperator(partitions.get("FOG").getSets().get(0), curr_var);

        HashMap<Integer, ArrayList<ArrayList<Integer>>> f_output = f_op.applyOperator();

        FogGenerator nssg = new FogGenerator(f_output, tr.getLabelsets().get("FOG"), tr.getLabelsets().get("PD"), tr.getLabelsets().get("DW"), Calendar.getInstance(), curr_var.getActual_data_length());

        String salida = nssg.generate();

        Assert.assertEquals("Habrá nieblas matinales el miércoles posiblemente persistentes, el viernes posiblemente persistentes y el sábado; por la tarde el jueves; y nocturnas el sábado.", salida);
    }
}
