package configuration_tests;

import configuration.template_reader.TemplateReader;
import configuration.template_reader.template_components.Option;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/* Tests the method that parses labelsets. */
public class CP04 {

    public CP04() {
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

        Option op = (Option) tr.getTemplates().get("Temperature").getCases().get(0).getParts().get(1).getComponents().get(2);

        op.setUsed(true);

        Assert.assertEquals("fuerte del Oeste", tr.getLabelsets().get("W").getLabels().get("323").toString());
    }
}
