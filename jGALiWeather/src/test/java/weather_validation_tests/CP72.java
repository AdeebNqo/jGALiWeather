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

            String id = "36059";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-11-24");
            dates.add("2015-11-25");
            dates.add("2015-11-26");
            dates.add("2015-11-27");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-11-24");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Se espera que los cielos alternen periodos muy nubosos con otros parcialmente nubosos. Tendremos precipitaciones el martes por la tarde y el viernes por la noche. 
             *   Las temperaturas serán normales para esta época del año, con mínimas en ascenso moderado y máximas sin cambios.
             */
            assertEquals(salida, "It is expected an alternance of cloudy skies periods with other partly cloudy periods. Precipitations are expected on Tuesday afternoon and on Friday night. Temperature will be normal for this period of the year, with minimums in moderate increase and maximums without changes.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
