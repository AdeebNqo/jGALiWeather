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
public class CP91 {

    public CP91() {
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

            String id = "27051";

            ArrayList<String> dates = new ArrayList();
            dates.add("2015-10-16");
            dates.add("2015-10-17");
            dates.add("2015-10-18");
            dates.add("2015-10-19");

            SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdt.parse("2015-10-16");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
            String salida = ps.generateTextualForecastsTest(id, dates, cal);

            /*
             *   Tendremos cielos poco nubosos o despejados al principio del periodo, aunque hacia la mitad y al final del mismo tenderán a estar parcialmente nubosos. Tendremos 
             *   precipitaciones el sábado por la noche y el lunes. Las temperaturas serán normales para esta época del año, con mínimas en ascenso moderado y máximas en descenso 
             *   ligero, a pesar de que oscilarán.
             */
            assertEquals(salida, "There will be clear skies at the beginning of the term, although they will be partly cloudy towards the middle and at the end of the term. Precipitations are expected on Saturday night and on Monday. Temperature will be normal for this period of the year, with minimums in moderate increase and maximums in slight decrease, despite that they will oscillate.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
