package configuration_tests;

import jgaliweather.configuration.configuration_reader.ConfigurationReader;
import jgaliweather.configuration.configuration_reader.DatabaseConfiguration;
import jgaliweather.data.Language;
import java.util.HashMap;
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
        ConfigurationReader cr = new ConfigurationReader();

        cr.parseFile("Configuration/configuration.xml");

        HashMap<String, String> inpaths = new HashMap();
        inpaths.put("variables", "../Configuration/variables.xml");
        inpaths.put("partitions", "../Configuration/partitions.xml");

        HashMap<String, String> outpaths = new HashMap();
        outpaths.put("output_dir", "C:\\\\");

        DatabaseConfiguration dc1 = new DatabaseConfiguration("prediccion", "SQL Server Native Client 11.0", "127.0.0.1", "Predicion",
                "Predicion_USC", "Alejandro,USC");
        DatabaseConfiguration dc2 = new DatabaseConfiguration("ica", "SQL Server Native Client 11.0", "127.0.0.1", "CalidadeAire",
                "CalidadeAire_TextosICA", ",Fresc05,O2");

        Language l1 = new Language("2", "../Configuration/Languages/galego.xml");
        Language l2 = new Language("2", "../Configuration/Languages/galego2.xml");

        Language l3 = new Language("1", "../Configuration/Languages/espanol.xml");
        Language l4 = new Language("1", "../Configuration/Languages/espanol2.xml");

        Assert.assertEquals(cr.getInpaths().get("variables"), inpaths.get("variables"));
        Assert.assertEquals(cr.getInpaths().get("partitions"), inpaths.get("partitions"));

        Assert.assertEquals(cr.getOutpaths().get("output_dir"), outpaths.get("output_dir"));

        Assert.assertEquals(cr.getDb_data().get("prediccion").toString(), dc1.toString());
        Assert.assertEquals(cr.getDb_data().get("ica").toString(), dc2.toString());

        Assert.assertEquals(cr.getLng_data().getLanguages().get(0).toString(), l1.toString());
        Assert.assertEquals(cr.getLng_data().getLanguages().get(1).toString(), l2.toString());
        Assert.assertEquals(cr.getLng_data().getLanguages().get(2).toString(), l3.toString());
        Assert.assertEquals(cr.getLng_data().getLanguages().get(3).toString(), l4.toString());
        Assert.assertEquals("Number of languages: 4", cr.getLng_data().toString());
    }
}
