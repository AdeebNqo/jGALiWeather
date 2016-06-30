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
public class CP97 {
    
    public CP97() {
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

            String id = "32035";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-09-21");
            dates.add("2015-09-22");
            dates.add("2015-09-23");
            dates.add("2015-09-24");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-09-21");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Se espera que los cielos alternen periodos parcialmente nubosos con otros poco nubosos o despejados. Las temperaturas serán normales para esta época del año, 
             *   con valores que globalmente se encontrarán en ascenso ligero aunque oscilarán.
             */
            assertEquals(salida, "It is expected an alternance of partly cloudy skies periods with other clear periods. Temperature will be normal for this period of the year, which will globally be in slight increase although they will oscillate.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
