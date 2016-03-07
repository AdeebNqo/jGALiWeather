package configuration_tests;

import configuration.configuration_reader.VariableReader;
import configuration.template_reader.template_components.Option;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Difma
 */
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
