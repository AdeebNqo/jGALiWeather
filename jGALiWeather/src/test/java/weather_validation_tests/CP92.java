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
public class CP92 {
    
    public CP92() {
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

            String id = "36018";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-07-15");
            dates.add("2015-07-16");
            dates.add("2015-07-17");
            dates.add("2015-07-18");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-07-15");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Tendremos cielos poco nubosos o despejados al principio y hacia la mitad del periodo, aunque al final del mismo tenderán a estar parcialmente nubosos. Las 
             *   temperaturas serán altas para las mínimas y normales para las máximas respecto a lo habitual en esta época del año, con mínimas sin cambios y máximas en descenso 
             *   moderado, a pesar de que oscilarán.
             */
            assertEquals(salida, "There will be clear skies at the beginning and towards the middle of the term, although they will be partly cloudy at the end of the term. Temperature will be high for minimums and normal for maximums compared to the expected for this time of the year, with minimums without changes and maximums in moderate decrease, despite that they will oscillate.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
