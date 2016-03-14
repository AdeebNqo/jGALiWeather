package algorithm_tests.weather_operators;

import java.util.ArrayList;
import java.util.HashMap;
import jgaliweather.algorithm.weather_operators.FogOperator;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.PartitionReader;
import jgaliweather.configuration.variable_reader.VariableReader;
import jgaliweather.configuration.variable_reader.XMLVariable;
import jgaliweather.data.data_structures.Location;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
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

        PartitionReader pr = new PartitionReader();
        pr.parseFile("Configuration/partitions.xml");
        HashMap<String, Partition> partitions = pr.getPartitions();

        Variable curr_var = new Variable("Meteoro");

        curr_var.getValues().add(new Value(106, 0));
        curr_var.getValues().add(new Value(115, 1));
        curr_var.getValues().add(new Value(106, 2));
        curr_var.getValues().add(new Value(1, 0));//Separador
        curr_var.getValues().add(new Value(106, 3));
        curr_var.getValues().add(new Value(106, 4));
        curr_var.getValues().add(new Value(115, 5));
        curr_var.getValues().add(new Value(1, 0));//Separador
        curr_var.getValues().add(new Value(106, 6));
        curr_var.getValues().add(new Value(106, 7));
        curr_var.getValues().add(new Value(106, 8));
        curr_var.getValues().add(new Value(1, 0));//Separador
        curr_var.getValues().add(new Value(106, 9));
        curr_var.getValues().add(new Value(115, 10));
        curr_var.getValues().add(new Value(106, 11));

        FogOperator f_op = new FogOperator(partitions.get("FOG").getSets().get(0), curr_var);

        HashMap<Double, ArrayList<Double>> salida = f_op.applyOperator();

        int j = 0;
        for (ArrayList<Double> v : salida.values()) {
            j++;
            System.out.print(j + ": ");
            for (int i = 0; i < v.size(); i++) {
                System.out.print(v.get(i) + " ");
            }
            System.out.println("");
        }
    }
}
