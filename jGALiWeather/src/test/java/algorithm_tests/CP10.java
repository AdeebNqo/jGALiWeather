package algorithm_tests;

import java.util.ArrayList;
import java.util.HashMap;
import jgaliweather.algorithm.weather_operators.WindOperator;
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

/* Tests Wind operators */
public class CP10 {
    
    public CP10() {
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
            
            curr_var.getValues().add(new Value(317, 0));
            curr_var.getValues().add(new Value(320, 1));
            curr_var.getValues().add(new Value(320, 2));
            curr_var.getValues().add(new Value(1, 2));//Separador
            curr_var.getValues().add(new Value(317, 3));
            curr_var.getValues().add(new Value(320, 4));
            curr_var.getValues().add(new Value(320, 5));
            curr_var.getValues().add(new Value(1, 2));//Separador
            curr_var.getValues().add(new Value(332, 6));
            curr_var.getValues().add(new Value(317, 7));
            curr_var.getValues().add(new Value(332, 8));
            curr_var.getValues().add(new Value(1, 2));//Separador
            curr_var.getValues().add(new Value(332, 9));
            curr_var.getValues().add(new Value(317, 10));
            curr_var.getValues().add(new Value(332, 11));
            l1.getVariables().put(curr_var.getName(), curr_var);
        }

        locations.put(l1.getName(), l1);
        
        ArrayList<Integer> WIND_INTERVAL = new ArrayList();       
        WIND_INTERVAL.add(317);
        WIND_INTERVAL.add(332);
        
        WindOperator w_op = new WindOperator(WIND_INTERVAL, locations.get("Prueba1").getVariables().get("Viento"));
        
        ArrayList<String> salida = w_op.applyOperator();
        
        for (int i = 0; i < salida.size(); i++) {
            System.out.println(salida.get(i));            
        }
    }
}
