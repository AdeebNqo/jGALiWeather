package algorithm_tests.ICA_operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jgaliweather.algorithm.ICA_operators.ICARainOperator;
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

/* Tests ICARain operators */
public class CP14 {

    public CP14() {
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
            
            ICARainOperator r_op = new ICARainOperator(partitions.get("R"), curr_var, 9);
            
            ArrayList<Double> salida_esperada = new ArrayList();
            salida_esperada.add(2.0);
            salida_esperada.add(4.0);
            salida_esperada.add(2.0);
            
            ArrayList<Double> salida = r_op.applyOperator();
            
            for (int i = 0; i < salida.size(); i++) {
                Assert.assertEquals(salida.get(i), salida_esperada.get(i));
            }
        } catch (Exception ex) {
            Logger.getLogger(CP14.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
