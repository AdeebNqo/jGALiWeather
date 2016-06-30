package configuration_tests;

import jgaliweather.configuration.configuration_reader.ConfigurationReader;
import jgaliweather.configuration.configuration_reader.DatabaseConfiguration;
import jgaliweather.data.data_structures.Language;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/* Tests methods which parses an XML configuration file */
public class CP01 {
    
    public CP01() {
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
            ConfigurationReader cr = new ConfigurationReader();
            
            cr.parseFile("Configuration/configuration.xml");
            
            HashMap<String, String> inpaths = new HashMap();
            inpaths.put("variables", "Configuration/variables.xml");
            inpaths.put("partitions", "Configuration/partitions.xml");
            inpaths.put("templates", "Configuration/templates.xml");
            
            
            Language l = new Language("1", "Configuration/templates.xml");
            
            Assert.assertEquals(cr.getInpaths().get("variables"), inpaths.get("variables"));
            Assert.assertEquals(cr.getInpaths().get("partitions"), inpaths.get("partitions"));
            Assert.assertEquals(cr.getInpaths().get("templates"), inpaths.get("templates"));
            
        } catch (Exception ex) {
            Logger.getLogger(CP01.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
