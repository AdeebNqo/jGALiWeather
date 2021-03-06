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
public class CP81 {
    
    public CP81() {
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

            String id = "27902";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-10-21");
            dates.add("2015-10-22");
            dates.add("2015-10-23");
            dates.add("2015-10-24");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-10-21");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Cielos parcialmente nubosos al principio, poco nubosos o despejados hacia la mitad y muy nubosos al final del periodo. Tendremos precipitaciones el sábado. Las 
             *   temperaturas serán normales para esta época del año, con mínimas en ascenso moderado y máximas en ascenso ligero, a pesar de que oscilarán.
             */
            assertEquals(salida, "There will be partly cloudy skies at the beginning, clear skies towards the middle and cloudy skies at the end of the term. Precipitations are expected on Saturday. Temperature will be normal for this period of the year, with minimums in moderate increase and maximums in slight increase, despite that they will oscillate.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
