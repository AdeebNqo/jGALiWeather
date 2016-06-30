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
public class CP59 {
    
    public CP59() {
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

            String id = "36011";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-12-20");
            dates.add("2015-12-21");
            dates.add("2015-12-22");
            dates.add("2015-12-23");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-12-20");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            
            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);;

            /*
             *   Tendremos cielos parcialmente nubosos al principio y hacia la mitad del periodo, aunque al final del mismo tenderán a estar muy nubosos. Tendremos precipitaciones 
             *   el domingo, del martes por la tarde al miércoles por la mañana y el miércoles por la noche. Las temperaturas serán altas para las mínimas y normales para las 
             *   máximas respecto a lo habitual en esta época del año, con mínimas en ascenso ligero y máximas sin cambios. Viento fuerte del Sur el lunes por la tarde; fuerte 
             *   del Sur del martes por la mañana al martes por la noche; y fuerte del Sudoeste el miércoles por la noche.
             */
            assertEquals(salida, "There will be partly cloudy skies at the beginning and towards the middle of the term, although they will be cloudy at the end of the term. Precipitations are expected on Sunday, from Tuesday afternoon to Wednesday morning and on Wednesday night. Temperature will be high for minimums and normal for maximums compared to the expected for this time of the year, with minimums in slight increase and maximums without changes. Strong South wind on Monday afternoon, strong South from Tuesday morning to Tuesday night and strong Southwest on Wednesday night.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
