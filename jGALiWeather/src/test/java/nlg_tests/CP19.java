package nlg_tests;

import java.util.HashMap;
import jgaliweather.algorithm.weather_operators.SkyStateBOperator;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.PartitionReader;
import jgaliweather.configuration.template_reader.TemplateReader;
import jgaliweather.configuration.variable_reader.VariableReader;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
import jgaliweather.nlg.nlg_generators.SkyCoverageGeneratorLevel2;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/* Tests SkyCoverageGeneratorLevel2*/
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

        TemplateReader tr = new TemplateReader();
        tr.parseFile("Configuration/Languages/espanol.xml");

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

        SkyStateBOperator nssb_op = new SkyStateBOperator(partitions.get("C"), partitions.get("CP"), curr_var);

        double[][] nssb_otuput = nssb_op.applyOperator();

        SkyCoverageGeneratorLevel2 nssg = new SkyCoverageGeneratorLevel2(tr.getTemplates().get("C2"), tr.getLabelsets().get("C"), partitions.get("C"), nssb_otuput);

        String salida = nssg.generate();

        Assert.assertEquals("Tendremos cielos parcialmente nubosos con momentos muy nubosos o poco nubosos o despejados.", salida);
    }
}
