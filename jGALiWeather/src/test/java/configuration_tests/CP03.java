package configuration_tests;

import java.util.logging.Level;
import java.util.logging.Logger;
import jgaliweather.configuration.template_reader.TemplateReader;
import jgaliweather.configuration.template_reader.template_components.Option;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/* Tests the method that parses an option element in a template file. */
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
            TemplateReader tr = new TemplateReader();
            
            tr.parseFile("Configuration/Languages/espanol.xml");
            
            Assert.assertEquals("poco nubosos o despejados", tr.getLabelsets().get("C").getLabels().get("C").getData());
        } catch (Exception ex) {
            Logger.getLogger(CP03.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
