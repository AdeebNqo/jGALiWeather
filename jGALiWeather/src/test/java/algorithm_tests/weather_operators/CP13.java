package algorithm_tests.weather_operators;

import java.util.HashMap;
import jgaliweather.algorithm.weather_operators.SkyStateBOperator;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.PartitionReader;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/* Tests SkyStateA operators */
public class CP13 {

    public CP13() {
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

        SkyStateBOperator nssa_op = new SkyStateBOperator(partitions.get("C"), partitions.get("CP"), curr_var);

        double[][] salida = nssa_op.applyOperator();

        Assert.assertEquals(2.0, salida[1][1], 1e-15);

    }
}
