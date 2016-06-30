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
public class CP84 {
    
    public CP84() {
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

            String id = "27034";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-12-18");
            dates.add("2015-12-19");
            dates.add("2015-12-20");
            dates.add("2015-12-21");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-12-18");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Cielos parcialmente nubosos en general durante los próximos días. Tendremos precipitaciones el sábado y el domingo. Las temperaturas serán muy altas para las mínimas 
             *   y altas para las máximas respecto a lo habitual en esta época del año, con mínimas en descenso notable y máximas en descenso moderado, a pesar de que oscilarán. 
             *   Viento fuerte del Sur del viernes por la tarde al sábado por la tarde.
             */
            assertEquals(salida, "Partly cloudy skies in general for the next few days. Precipitations are expected on Saturday and on Sunday. Temperature will be very high for minimums and high for maximums compared to the expected for this time of the year, with minimums in notable decrease and maximums in moderate decrease, despite that they will oscillate. Strong South wind from Friday afternoon to Saturday afternoon.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
