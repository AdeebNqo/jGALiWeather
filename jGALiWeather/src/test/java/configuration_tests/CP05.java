package configuration_tests;

import jgaliweather.configuration.variable_reader.VariableReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/* Tests the method that parses variables XML files. */
public class CP05 {
    
    public CP05() {
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
        VariableReader vr = new VariableReader();

        vr.parseFile("Configuration/variables.xml");

        Assert.assertEquals("Number of languages: 3", vr.toString());
        Assert.assertEquals("Variable Viento: 50 to 61", vr.getVariables().get("Viento").toString());
    }
}
