/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weather_validation_tests;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import jgaliweather.PredictionSummarizer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Difma
 */
public class CP66 {
    
    public CP66() {
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

            String id = "36057";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-11-11");
            dates.add("2015-11-12");
            dates.add("2015-11-13");
            dates.add("2015-11-14");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-11-11");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Cielos poco nubosos o despejados en general durante los próximos días, aunque ocasionalmente se encontrarán parcialmente nubosos. Las temperaturas serán normales 
             *   para las mínimas y altas para las máximas respecto a lo habitual en esta época del año, con mínimas en descenso ligero y máximas sin cambios, a pesar de que oscilarán.
             */
            assertEquals(salida, "Clear skies in general for the next few days, although it will occasionally be partly cloudy. Temperature will be normal for minimums and high for maximums compared to the expected for this time of the year, with minimums in slight decrease and maximums without changes, despite that they will oscillate.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
