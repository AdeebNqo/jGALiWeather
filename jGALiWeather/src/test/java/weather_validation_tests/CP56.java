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
public class CP56 {
    
    public CP56() {
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

            String id = "32018";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-09-22");
            dates.add("2015-09-23");
            dates.add("2015-09-24");
            dates.add("2015-09-25");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-09-22");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Tendremos cielos parcialmente nubosos al principio del periodo, aunque hacia la mitad y al final del mismo tenderán a estar poco nubosos o despejados. Las 
             *   temperaturas serán normales para esta época del año, con mínimas en ascenso moderado y máximas en ascenso notable.
             */
            assertEquals(salida, "There will be partly cloudy skies at the beginning of the term, although they will be clear towards the middle and at the end of the term. Temperature will be normal for this period of the year, with minimums in moderate increase and maximums in notable increase.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
