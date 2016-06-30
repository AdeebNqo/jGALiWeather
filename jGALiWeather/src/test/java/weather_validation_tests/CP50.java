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
public class CP50 {
    
    public CP50() {
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

            String id = "15061";

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
             *   Cielos parcialmente nubosos en general durante los próximos días, aunque ocasionalmente se encontrarán poco nubosos o despejados. Tendremos precipitaciones el miércoles 
             *   por la mañana. Las temperaturas serán altas para las mínimas y normales para las máximas respecto a lo habitual en esta época del año, con mínimas sin cambios aunque oscilarán y 
             *   máximas en ascenso moderado.
             */
            assertEquals(salida, "Partly cloudy skies in general for the next few days, although it will occasionally be clear. Precipitations are expected on Wednesday morning. Temperature will be high for minimums and normal for maximums compared to the expected for this time of the year, with minimums without changes although they will oscillate and maximums in moderate increase.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
