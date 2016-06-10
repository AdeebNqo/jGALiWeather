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
public class CP79 {
    
    public CP79() {
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

            String id = "27029";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-10-21");
            dates.add("2015-10-22");
            dates.add("2015-10-23");
            dates.add("2015-10-24");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-10-21");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Se espera que los cielos alternen periodos muy nubosos con otros poco nubosos o despejados, aunque ocasionalmente podrán encontrarse parcialmente nubosos. Habrá 
             *   nieblas matinales el miércoles y el jueves. Tendremos precipitaciones el sábado por la tarde. Las temperaturas serán normales para esta época del año, con mínimas 
             *   en ascenso ligero y máximas sin cambios, a pesar de que oscilarán.
             */
            assertEquals(salida, "It is expected an alternance of cloudy skies periods with other clear periods, although they will occasionally be partly cloudy. There will be morning fog on Wednesday and on Thursday. Precipitations are expected on Saturday afternoon. Temperature will be normal for this period of the year, with minimums in slight increase and maximums without changes, despite that they will oscillate.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
