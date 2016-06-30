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
public class CP67 {
    
    public CP67() {
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

            String id = "27026";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-10-07");
            dates.add("2015-10-08");
            dates.add("2015-10-09");
            dates.add("2015-10-10");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-10-07");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Se espera que los cielos alternen periodos parcialmente nubosos con otros poco nubosos o despejados, aunque ocasionalmente podrán encontrarse muy nubosos. 
             *   Habrá nieblas matinales el jueves; y nocturnas el miércoles. Tendremos precipitaciones el sábado. Las temperaturas serán normales para esta época del año, con 
             *   mínimas en ascenso moderado y máximas en ascenso ligero.
             */
            assertEquals(salida, "It is expected an alternance of partly cloudy skies periods with other clear periods, although they will occasionally be cloudy. There will be morning fog on Thursday and night fog on Wednesday. Precipitations are expected on Saturday. Temperature will be normal for this period of the year, with minimums in moderate increase and maximums in slight increase.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
