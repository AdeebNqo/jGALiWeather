package algorithm_tests.ICA_operators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import jgaliweather.algorithm.ICA_operators.ICASkyStateOperator;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.PartitionReader;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
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

        ArrayList<Double> salida_esperada = new ArrayList();
        salida_esperada.add(2.0);
        salida_esperada.add(4.0);
        salida_esperada.add(2.0);

        ArrayList<HashMap<String, Double>> salida = ss_op.applyOperator();

        for (int i = 0; i < salida.size(); i++) {
            Iterator it = salida.get(i).entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                System.out.println(pair.getKey() + ": " + pair.getValue());
                it.remove();
            }
        }

    }
}
