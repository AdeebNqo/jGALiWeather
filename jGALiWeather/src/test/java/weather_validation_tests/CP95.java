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
public class CP95 {
    
    public CP95() {
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

            String id = "27047";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-09-24");
            dates.add("2015-09-25");
            dates.add("2015-09-26");
            dates.add("2015-09-27");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-09-24");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Cielos poco nubosos o despejados en general durante los próximos días, aunque ocasionalmente se encontrarán parcialmente nubosos. Habrá nieblas matinales el jueves. 
             *   Las temperaturas serán normales para esta época del año, con valores que globalmente se encontrarán sin cambios.
             */
            assertEquals(salida, "Clear skies in general for the next few days, although it will occasionally be partly cloudy. There will be morning fog on Thursday. Temperature will be normal for this period of the year, which will globally be without changes.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
