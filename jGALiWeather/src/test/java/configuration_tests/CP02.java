package configuration_tests;

import java.util.logging.Level;
import java.util.logging.Logger;
import jgaliweather.configuration.template_reader.TemplateReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

/* Tests methods that parses a template file. */
public class CP02 {

    public CP02() {
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
            
            tr.parseFile("Configuration/templates.xml");
            
            Assert.assertEquals("clear", tr.getLabelsets().get("C").getLabels().get("C").getData());
        } catch (Exception ex) {
            Logger.getLogger(CP02.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
