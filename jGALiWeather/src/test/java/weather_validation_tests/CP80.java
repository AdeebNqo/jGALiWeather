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
public class CP80 {
    
    public CP80() {
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

            String id = "27055";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-10-17");
            dates.add("2015-10-18");
            dates.add("2015-10-19");
            dates.add("2015-10-20");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-10-17");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Cielos parcialmente nubosos en general durante los próximos días, aunque ocasionalmente se encontrarán poco nubosos o despejados. Tendremos precipitaciones el sábado 
             *   y el domingo por la noche. Las temperaturas serán altas para esta época del año, con mínimas sin cambios y máximas en descenso moderado. Viento fuerte del Este desde 
             *   el sábado por la tarde, cambiando a fuerte del Sudeste el sábado por la noche; y fuerte del Nordeste del martes por la mañana al martes por la tarde.
             */
            assertEquals(salida, "Partly cloudy skies in general for the next few days, although it will occasionally be clear. Precipitations are expected on Saturday and on Sunday night. Temperature will be high for this period of the year, with minimums without changes and maximums in moderate decrease. Strong East wind from Saturday afternoon, changing to strong Southeast on Saturday night and strong Northeast from Tuesday morning to Tuesday afternoon.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
