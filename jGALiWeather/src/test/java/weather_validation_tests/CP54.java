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
public class CP54 {
    
    public CP54() {
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

            String id = "15031";

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
             *   Tendremos cielos parcialmente nubosos con momentos poco nubosos o despejados. Tendremos precipitaciones el viernes por la mañana. Las temperaturas serán altas 
             *   para las mínimas y normales para las máximas respecto a lo habitual en esta época del año, con mínimas en descenso ligero y máximas sin cambios, a pesar de que 
             *   oscilarán.
             */
            assertEquals(salida, "There will be partly cloudy skies with clear moments. Precipitations are expected on Friday morning. Temperature will be high for minimums and normal for maximums compared to the expected for this time of the year, with minimums in slight decrease and maximums without changes, despite that they will oscillate.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
