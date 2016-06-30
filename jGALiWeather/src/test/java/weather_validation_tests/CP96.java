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
public class CP96 {
    
    public CP96() {
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

            String id = "15075";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-08-21");
            dates.add("2015-08-22");
            dates.add("2015-08-23");
            dates.add("2015-08-24");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-08-21");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Se espera que los cielos alternen periodos muy nubosos con otros parcialmente nubosos. Tendremos precipitaciones el sábado, el domingo y el lunes. Las temperaturas 
             *   serán normales para esta época del año, con valores que globalmente se encontrarán en descenso ligero aunque oscilarán.
             */
            assertEquals(salida, "It is expected an alternance of cloudy skies periods with other partly cloudy periods. Precipitations are expected on Saturday, Sunday and Monday. Temperature will be normal for this period of the year, which will globally be in slight decrease although they will oscillate.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
