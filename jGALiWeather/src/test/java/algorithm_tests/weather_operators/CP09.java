package algorithm_tests.weather_operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jgaliweather.algorithm.weather_operators.FogOperator;
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

/* Tests FOG operators */
public class CP09 {

    public CP09() {
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
            
            HashMap<Integer, ArrayList<ArrayList<Integer>>> salida = f_op.applyOperator();
            
            ArrayList<String> salida_esperada = new ArrayList();
            salida_esperada.add("[[0, 0], [2, 2], [3]]");
            salida_esperada.add("[[1]]");
            salida_esperada.add("[[3]]");
            
            int i = 0;
            for (ArrayList<ArrayList<Integer>> v : salida.values()) {
                Assert.assertEquals(salida_esperada.get(i), v.toString());
                i++;
            }
        } catch (Exception ex) {
            Logger.getLogger(CP09.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
