package algorithm_tests;

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

        VariableReader vr = new VariableReader();
        vr.parseFile("Configuration/variables.xml");
        HashMap<String, XMLVariable> variables = vr.getVariables();

        HashMap<String, Location> locations = new HashMap();
        Location l1 = new Location("Prueba1", 120);
        
        for (XMLVariable v : variables.values()) {
            Variable curr_var = new Variable(v.getName());
            
            curr_var.getValues().add(new Value(106, 0));
            curr_var.getValues().add(new Value(115, 1));
            curr_var.getValues().add(new Value(106, 2));
            curr_var.getValues().add(new Value(106, 3));
            curr_var.getValues().add(new Value(106, 4));
            curr_var.getValues().add(new Value(115, 5));
            curr_var.getValues().add(new Value(106, 6));
            curr_var.getValues().add(new Value(106, 7));
            curr_var.getValues().add(new Value(106, 8));
            curr_var.getValues().add(new Value(106, 9));
            curr_var.getValues().add(new Value(115, 10));
            curr_var.getValues().add(new Value(106, 11));
            l1.getVariables().put(curr_var.getName(), curr_var);
        }

        locations.put(l1.getName(), l1);
        
        FogOperator f_op = new FogOperator(partitions.get("FOG").getSets().get(0), locations.get("Prueba1").getVariables().get("Meteoro"));
        
        HashMap<Double, ArrayList<Double>> salida = f_op.applyOperator();
        
        for (ArrayList<Double> v : salida.values()) {
            for (int i = 0; i < v.size(); i++) {
                System.out.println(i + ": " + v.get(i));
                
            }
        }
    }
}
