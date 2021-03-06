package configuration_tests;

import java.util.logging.Level;
import java.util.logging.Logger;
import jgaliweather.configuration.variable_reader.VariableReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/* Tests the method that parses variables XML files. */
public class CP03 {
    
    public CP03() {
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
            VariableReader vr = new VariableReader();
            
            vr.parseFile("Configuration/variables.xml");
            
            Assert.assertEquals("Variable Viento: 50 to 61", vr.getVariables().get("Viento").toString());
        } catch (Exception ex) {
            Logger.getLogger(CP03.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
