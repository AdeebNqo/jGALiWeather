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
public class CP83 {
    
    public CP83() {
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

            String id = "32039";

            ArrayList<String> dates = new ArrayList();
            dates.add("2016-02-27");
            dates.add("2016-02-28");
            dates.add("2016-02-29");
            dates.add("2016-03-01");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2016-02-27");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Tendremos cielos parcialmente nubosos al principio y hacia la mitad del periodo, aunque al final del mismo tenderán a estar poco nubosos o despejados. Tendremos 
             *   precipitaciones el sábado, que podrán ser de nieve el sábado. Las temperaturas serán normales para esta época del año, con mínimas en ascenso ligero aunque oscilarán 
             *   y máximas en ascenso moderado. Viento fuerte del Noroeste desde el sábado por la tarde, cambiando a fuerte del Norte el domingo por la mañana; y fuerte del Norte 
             *   del domingo por la noche al lunes por la mañana.
             */
            assertEquals(salida, "There will be partly cloudy skies at the beginning and towards the middle of the term, although they will be clear at the end of the term. Precipitations are expected on Saturday, which can be of snow on Saturday. Temperature will be normal for this period of the year, with minimums in slight increase although they will oscillate and maximums in moderate increase. Strong Northwest wind from Saturday afternoon, changing to strong North on Sunday morning and strong North from Sunday night to Monday morning.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
