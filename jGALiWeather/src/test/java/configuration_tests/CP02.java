package configuration_tests;

import jgaliweather.configuration.template_reader.TemplateReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        TemplateReader tr = new TemplateReader();

        tr.parseFile("Configuration/Languages/espanol.xml");

        Assert.assertEquals("del mismo tenderán a estar", tr.getTemplates().get("Cobertura Nivel 1").getCases().get(1).getParts().get(0).getComponents().get(12).toString());
    }
}
