package algorithm_tests.ICA_operators;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jgaliweather.algorithm.ICA_operators.ICAOperator;
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

/* Tests ICA operator */
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
            
            curr_var.getValues().add(new Value(5, 0));
            curr_var.getValues().add(new Value(1, 1));
            curr_var.getValues().add(new Value(3, 2));
            
            ICAOperator r_op = new ICAOperator(partitions.get("ICA"), curr_var);
            
            String salida = r_op.applyOperator();
            
            Assert.assertEquals("- + I", salida);
        } catch (Exception ex) {
            Logger.getLogger(CP15.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
