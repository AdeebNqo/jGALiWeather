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
public class CP88 {
    
    public CP88() {
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

            String id = "27058";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-09-12");
            dates.add("2015-09-13");
            dates.add("2015-09-14");
            dates.add("2015-09-15");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-09-12");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Cielos parcialmente nubosos en general durante los próximos días, aunque ocasionalmente se encontrarán muy nubosos. Tendremos precipitaciones el domingo, el lunes y 
             *   el martes. Las temperaturas serán normales para esta época del año, con mínimas sin cambios aunque oscilarán y máximas en descenso ligero. Viento fuerte del Sudoeste
             *   el martes por la noche.
             */
            assertEquals(salida, "Partly cloudy skies in general for the next few days, although it will occasionally be cloudy. Precipitations are expected on Sunday, Monday and Tuesday. Temperature will be normal for this period of the year, with minimums without changes although they will oscillate and maximums in slight decrease. Strong Southwest wind on Tuesday night.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
