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
public class CP69 {
    
    public CP69() {
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

            String id = "27026";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-10-09");
            dates.add("2015-10-10");
            dates.add("2015-10-11");
            dates.add("2015-10-12");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-10-09");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Tendremos cielos poco nubosos o despejados al principio del periodo, aunque hacia la mitad y al final del mismo tenderán a estar parcialmente nubosos. Tendremos 
             *   precipitaciones el sábado, el domingo y el lunes. Las temperaturas serán altas para las mínimas y normales para las máximas respecto a lo habitual en esta época 
             *   del año, con mínimas en ascenso moderado aunque oscilarán y máximas en descenso moderado.
             */
            assertEquals(salida, "There will be clear skies at the beginning of the term, although they will be partly cloudy towards the middle and at the end of the term. Precipitations are expected on Saturday, Sunday and Monday. Temperature will be high for minimums and normal for maximums compared to the expected for this time of the year, with minimums in moderate increase although they will oscillate and maximums in moderate decrease.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
