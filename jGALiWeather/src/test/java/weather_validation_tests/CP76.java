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
public class CP76 {
    
    public CP76() {
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

            String id = "32045";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-11-01");
            dates.add("2015-11-02");
            dates.add("2015-11-03");
            dates.add("2015-11-04");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-11-01");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Cielos parcialmente nubosos en general durante los próximos días, aunque ocasionalmente se encontrarán muy nubosos. Tendremos precipitaciones el lunes por la noche 
             *   y del martes por la noche al miércoles por la tarde. Las temperaturas serán altas para esta época del año, con mínimas sin cambios y máximas en descenso moderado.
             */
            assertEquals(salida, "Partly cloudy skies in general for the next few days, although it will occasionally be cloudy. Precipitations are expected on Monday night and from Tuesday night to Wednesday afternoon. Temperature will be high for this period of the year, with minimums without changes and maximums in moderate decrease.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
