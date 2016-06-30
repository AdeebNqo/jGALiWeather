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
public class CP87 {
    
    public CP87() {
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

            String id = "36035";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-10-12");
            dates.add("2015-10-13");
            dates.add("2015-10-14");
            dates.add("2015-10-15");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-10-12");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Tendremos cielos parcialmente nubosos al principio del periodo, aunque hacia la mitad y al final del mismo tenderán a estar poco nubosos o despejados. Tendremos 
             *   precipitaciones el lunes. Las temperaturas serán normales para esta época del año, con mínimas en descenso moderado y máximas sin cambios.
             */
            assertEquals(salida, "There will be partly cloudy skies at the beginning of the term, although they will be clear towards the middle and at the end of the term. Precipitations are expected on Monday. Temperature will be normal for this period of the year, with minimums in moderate decrease and maximums without changes.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
