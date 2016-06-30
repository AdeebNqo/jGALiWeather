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
public class CP77 {
    
    public CP77() {
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

            String id = "15041";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-10-31");
            dates.add("2015-11-01");
            dates.add("2015-11-02");
            dates.add("2015-11-03");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-10-31");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Cielos parcialmente nubosos en general durante los próximos días. Tendremos precipitaciones el sábado, el lunes por la noche y el martes por la noche. Las 
             *   temperaturas serán altas para las mínimas y normales para las máximas respecto a lo habitual en esta época del año, con valores que globalmente se encontrarán sin 
             *   cambios aunque oscilarán.
             */
            assertEquals(salida, "Partly cloudy skies in general for the next few days. Precipitations are expected on Saturday, on Monday night and on Tuesday night. Temperature will be high for minimums and normal for maximums compared to the expected for this time of the year, which will globally be without changes although they will oscillate.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
