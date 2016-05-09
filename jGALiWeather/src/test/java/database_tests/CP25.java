
package database_tests;

import java.util.ArrayList;
import jgaliweather.configuration.configuration_reader.DatabaseConfiguration;
import jgaliweather.configuration.logger.GALiLogger;
import jgaliweather.database.DatabaseConnector;
import org.javatuples.Pair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/* Tests method: retrieveLocations*/
public class CP25 {
    
    public CP25() {
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
        
        GALiLogger.initLogger("asdasda");
        
        DatabaseConfiguration dc = new DatabaseConfiguration("", "", "", "D:/OneDrive/Clase/TFG/jGALiWeather/jGALiWeather/estudio6-6.db", "", "");
        
        DatabaseConnector conn = new DatabaseConnector(dc);
        
        ArrayList<Pair<Integer, String>> salida = conn.retrieveLocations();

        Assert.assertEquals(316, salida.size());
    }
}
