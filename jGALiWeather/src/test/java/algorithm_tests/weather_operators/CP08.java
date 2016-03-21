package algorithm_tests.weather_operators;

import java.util.HashMap;
import jgaliweather.algorithm.weather_operators.TemperatureOperator;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.PartitionReader;
import jgaliweather.data.data_structures.Temperature;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/* Tests Temperature operators */
public class CP08 {

    public CP08() {
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

        Variable curr_var = new Variable("Temperatura");

        curr_var.getValues().add(new Value(15, 0));
        curr_var.getValues().add(new Value(16, 1));
        curr_var.getValues().add(new Value(17, 2));
        curr_var.getValues().add(new Value(15, 3));
        curr_var.getValues().add(new Value(15, 4));
        curr_var.getValues().add(new Value(16, 5));
        curr_var.getValues().add(new Value(17, 6));
        curr_var.getValues().add(new Value(15, 7));

        Partition max_climate_partition = partitions.get("T");
        Partition min_climate_partition = partitions.get("T");
        
        /*for (int i = 0; i < partitions.get("T").getSets().size(); i++) {
            CrispInterval min = (CrispInterval)min_climate_partition.getSets().get(i);
            CrispInterval max = (CrispInterval)max_climate_partition.getSets().get(i);
            
            min.setA(i);
            
        }*/

        TemperatureOperator top = new TemperatureOperator(partitions.get("V"), max_climate_partition, min_climate_partition, partitions.get("VAR"), curr_var);
        
        Temperature salida = top.applyOperator();
        
        System.out.println(salida.getClim_eval() + ", " + salida.getVariability_eval() + ", " + salida.getVariation_eval());
    }
}
