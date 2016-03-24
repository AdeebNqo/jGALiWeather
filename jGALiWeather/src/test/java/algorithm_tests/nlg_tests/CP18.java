package algorithm_tests.nlg_tests;

import java.util.HashMap;
import jgaliweather.algorithm.weather_operators.SkyStateAOperator;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.PartitionReader;
import jgaliweather.configuration.template_reader.TemplateReader;
import jgaliweather.configuration.variable_reader.VariableReader;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
import jgaliweather.nlg.nlg_generators.SkyCoverageGeneratorLevel1;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/* Tests SkyCoverageGeneratorLevel1*/
public class CP18 {

    public CP18() {
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

        curr_var.getValues().add(new Value(115, 0));
        curr_var.getValues().add(new Value(115, 1));
        curr_var.getValues().add(new Value(115, 2));
        curr_var.getValues().add(new Value(115, 3));
        curr_var.getValues().add(new Value(115, 4));
        curr_var.getValues().add(new Value(115, 5));
        curr_var.getValues().add(new Value(115, 6));
        curr_var.getValues().add(new Value(115, 7));
        curr_var.getValues().add(new Value(115, 8));
        curr_var.getValues().add(new Value(115, 9));
        curr_var.getValues().add(new Value(115, 10));
        curr_var.getValues().add(new Value(115, 11));

        SkyStateAOperator nssa_op = new SkyStateAOperator(partitions.get("C"), partitions.get("SSFTP"), curr_var);

        String nssa_output = nssa_op.applyOperator();

        SkyCoverageGeneratorLevel1 nssg = new SkyCoverageGeneratorLevel1(tr.getTemplates().get("C1"), tr.getLabelsets().get("C"), partitions.get("SSFTP"), tr.getLabelsets().get("SSFTP"), nssa_output);

        String salida = nssg.parseAndGenerate();

        Assert.assertEquals(null, salida);
    }
}
