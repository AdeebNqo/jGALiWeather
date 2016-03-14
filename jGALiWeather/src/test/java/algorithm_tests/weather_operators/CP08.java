package algorithm_tests.weather_operators;

import java.util.HashMap;
import jgaliweather.algorithm.weather_operators.TemperatureOperator;
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

/**
 *
 * @author Difma
 */
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

        VariableReader vr = new VariableReader();
        vr.parseFile("Configuration/variables.xml");
        HashMap<String, XMLVariable> variables = vr.getVariables();

        HashMap<String, Location> locations = new HashMap();
        Location l1 = new Location("Prueba1", 120);
        
        for (XMLVariable v : variables.values()) {
            Variable curr_var = new Variable(v.getName());
            
            curr_var.getValues().add(new Value(15, 0));
            curr_var.getValues().add(new Value(16, 1));
            curr_var.getValues().add(new Value(17, 2));
            curr_var.getValues().add(new Value(15, 3));
            l1.getVariables().put(curr_var.getName(), curr_var);
        }
        
        String[][] cd = new String[4][3];
        String[] aux1 = {"1", "15", "1"};
        String[] aux2 = {"2", "16", "1"};
        String[] aux3 = {"3", "17", "1"};
        String[] aux4 = {"4", "18", "1"};
        cd[0] = aux1;
        cd[1] = aux2;
        cd[2] = aux3;
        cd[3] = aux4;
        
        l1.setClimatic_data(cd);

        locations.put(l1.getName(), l1);
        
        TemperatureOperator top = new TemperatureOperator(partitions.get("V"), null, null, partitions.get("VAR"), locations.get("Prueba1").getVariables().get("Temperatura"));
        //top.setClim_partitions();
    }
}
