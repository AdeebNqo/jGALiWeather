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

            String id = "32047";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-11-02");
            dates.add("2015-11-03");
            dates.add("2015-11-04");
            dates.add("2015-11-05");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-11-02");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Tendremos cielos parcialmente nubosos al principio y hacia la mitad del periodo, aunque al final del mismo tenderán a estar muy nubosos. Tendremos precipitaciones 
             *   todos los días. Las temperaturas serán altas para las mínimas y normales para las máximas respecto a lo habitual en esta época del año, con valores que globalmente 
             *   se encontrarán sin cambios.
             */
            assertEquals(salida, "There will be partly cloudy skies at the beginning and towards the middle of the term, although they will be cloudy at the end of the term. Precipitations are expected everyday. Temperature will be high for minimums and normal for maximums compared to the expected for this time of the year, which will globally be without changes.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
