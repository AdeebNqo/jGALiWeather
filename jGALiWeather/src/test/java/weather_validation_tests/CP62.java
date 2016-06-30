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
public class CP62 {
    
    public CP62() {
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

            String id = "32064";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-08-07");
            dates.add("2015-08-08");
            dates.add("2015-08-09");
            dates.add("2015-08-10");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-08-07");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Cielos poco nubosos o despejados en general durante los próximos días, aunque ocasionalmente se encontrarán parcialmente nubosos. Las temperaturas serán altas 
             *   para esta época del año, con mínimas en ascenso moderado aunque oscilarán y máximas en ascenso notable.
             */
            assertEquals(salida, "Clear skies in general for the next few days, although it will occasionally be partly cloudy. Temperature will be high for this period of the year, with minimums in moderate increase although they will oscillate and maximums in notable increase.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
