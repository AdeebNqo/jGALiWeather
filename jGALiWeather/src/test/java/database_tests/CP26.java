/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database_tests;

import java.util.ArrayList;
import jgaliweather.configuration.configuration_reader.DatabaseConfiguration;
import jgaliweather.configuration.logger.GALiLogger;
import jgaliweather.database.DatabaseConnector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/* Tests method: retrieveVariableDataForLocation*/
public class CP26 {
    
    public CP26() {
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
        
        ArrayList<String> dates = new ArrayList();
        dates.add("2015-07-25");
        dates.add("2015-07-26");
        dates.add("2015-07-27");
        dates.add("2015-07-28");
        
        //ArrayList<Integer> salida = conn.retrieveVariableDataForLocation(15001, dates);

        //Assert.assertEquals(32, salida.size());
        
        
    }
}
