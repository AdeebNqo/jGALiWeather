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
public class CP86 {
    
    public CP86() {
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

            String id = "36030";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-11-25");
            dates.add("2015-11-26");
            dates.add("2015-11-27");
            dates.add("2015-11-28");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-11-25");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Se espera que los cielos alternen periodos muy nubosos con otros parcialmente nubosos, aunque ocasionalmente podrán encontrarse poco nubosos o despejados. Tendremos 
             *   precipitaciones el viernes por la noche. Las temperaturas serán normales para esta época del año, con mínimas en descenso moderado y máximas en descenso ligero.
             */
            assertEquals(salida, "It is expected an alternance of cloudy skies periods with other partly cloudy periods, although they will occasionally be clear. Precipitations are expected on Friday night. Temperature will be normal for this period of the year, with minimums in moderate decrease and maximums in slight decrease.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
