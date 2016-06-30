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
public class CP51 {
    
    public CP51() {
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

            String id = "32013";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-08-14");
            dates.add("2015-08-15");
            dates.add("2015-08-16");
            dates.add("2015-08-17");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-08-14");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Se espera que los cielos alternen periodos muy nubosos con otros parcialmente nubosos. Habrá nieblas matinales el sábado y el domingo. Tendremos precipitaciones 
             *   el domingo por la noche. Las temperaturas serán normales para esta época del año, con valores que globalmente se encontrarán en ascenso moderado.
             */
            assertEquals(salida, "It is expected an alternance of cloudy skies periods with other partly cloudy periods. There will be morning fog on Saturday and on Sunday. Precipitations are expected on Sunday night. Temperature will be normal for this period of the year, which will globally be in moderate increase.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
