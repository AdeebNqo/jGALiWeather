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
public class CP72 {
    
    public CP72() {
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

            String id = "27010";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-07-20");
            dates.add("2015-07-21");
            dates.add("2015-07-22");
            dates.add("2015-07-23");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-07-20");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Tendremos cielos parcialmente nubosos con momentos muy nubosos. Habrá nieblas matinales el martes, el miércoles y el jueves. Tendremos precipitaciones el martes por 
             *   la tarde, que podrán ser tormentosas el martes. Las temperaturas serán altas para las mínimas y normales para las máximas respecto a lo habitual en esta época del 
             *   año, con valores que globalmente se encontrarán en descenso moderado aunque oscilarán.
             */
            assertEquals(salida, "There will be partly cloudy skies with cloudy moments. There will be morning fog on Tuesday, on Wednesday and on Thursday. Precipitations are expected on Tuesday afternoon, which can be stormy on Tuesday. Temperature will be high for minimums and normal for maximums compared to the expected for this time of the year, which will globally be in moderate decrease although they will oscillate.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
