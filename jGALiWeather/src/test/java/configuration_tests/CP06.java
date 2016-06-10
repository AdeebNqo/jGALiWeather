package configuration_tests;

import java.util.ArrayList;
import jgaliweather.configuration.partition_reader.ObjectSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/* Tests the apply method in an ObjectSet object. */
public class CP06 {
    
    public CP06() {
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

        ArrayList objects = new ArrayList();
        objects.add(1);
        objects.add(3);
        objects.add(24);
        objects.add(15);
        
        ObjectSet os = new ObjectSet("os1", objects);
        Assert.assertEquals(1, os.apply(24), 1e-15);
    }
}
