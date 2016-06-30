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
public class CP93 {
    
    public CP93() {
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

            String id = "27033";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-10-11");
            dates.add("2015-10-12");
            dates.add("2015-10-13");
            dates.add("2015-10-14");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-10-11");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Se espera que los cielos alternen periodos muy nubosos con otros parcialmente nubosos. Tendremos precipitaciones el domingo, el lunes y el martes. Las temperaturas 
             *   serán altas para las mínimas y normales para las máximas respecto a lo habitual en esta época del año, con mínimas en descenso moderado y máximas en descenso ligero.
             */
            assertEquals(salida, "It is expected an alternance of cloudy skies periods with other partly cloudy periods. Precipitations are expected on Sunday, Monday and Tuesday. Temperature will be high for minimums and normal for maximums compared to the expected for this time of the year, with minimums in moderate decrease and maximums in slight decrease.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
