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
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class CP46 {

    public CP46() {
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

            String id = "15009";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-08-22");
            dates.add("2015-08-23");
            dates.add("2015-08-24");
            dates.add("2015-08-25");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-08-22");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Cielos parcialmente nubosos en general durante los próximos días, aunque ocasionalmente se encontrarán muy nubosos. Tendremos precipitaciones el sábado, 
             *   el domingo y el lunes. Las temperaturas serán normales para esta época del año, con mínimas en ascenso ligero y máximas en ascenso moderado.
             */
            assertEquals(salida, "Partly cloudy skies in general for the next few days, although it will occasionally be cloudy. Precipitations are expected on Saturday, Sunday and Monday. Temperature will be normal for this period of the year, with minimums in slight increase and maximums in moderate increase.");
        } catch (Exception ex) {
            Logger.getLogger(CP46.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
