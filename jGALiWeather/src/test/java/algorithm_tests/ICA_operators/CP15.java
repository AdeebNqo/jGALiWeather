package algorithm_tests.ICA_operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jgaliweather.algorithm.ICA_operators.ICASkyStateOperator;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.PartitionReader;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
import org.junit.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/* Tests ICASkyState operators */
public class CP15 {

    public CP15() {
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
            
            Variable curr_var = new Variable("Meteoro");
            
            curr_var.getValues().add(new Value(110, 0));
            curr_var.getValues().add(new Value(103, 1));
            curr_var.getValues().add(new Value(111, 2));
            curr_var.getValues().add(new Value(107, 3));
            curr_var.getValues().add(new Value(118, 4));
            curr_var.getValues().add(new Value(101, 5));
            curr_var.getValues().add(new Value(109, 6));
            curr_var.getValues().add(new Value(102, 7));
            curr_var.getValues().add(new Value(113, 8));
            
            ICASkyStateOperator ss_op = new ICASkyStateOperator(partitions.get("C"), curr_var, 9);
            
            ArrayList<HashMap<String, Double>> salida = ss_op.applyOperator();
            
            double DELTA = 1e-15;
            Assert.assertEquals(1.3333333333333333, salida.get(0).get("CL"), DELTA);
            Assert.assertEquals(1.0, salida.get(2).get("C"), DELTA);
        } catch (Exception ex) {
            Logger.getLogger(CP15.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
