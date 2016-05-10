/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import com.diego.jgaliweather_rest.DAOs.DatabaseConnector;
import com.diego.jgaliweather_rest.VOs.MeteorologicalDataDay;
import java.text.SimpleDateFormat;
import org.junit.After;
import org.junit.Assert;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author Difma
 */
public class CP01 {
    
    public CP01() {
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
        
        MeteorologicalDataDay data = null;
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        try{
            
            data = DatabaseConnector.getInstance().retrieveVariableDataForLocation(27027, sdf.parse("2015-07-25"));
            
        } catch (Exception e) {}
        
        //Assert.assertEquals(150, data.getSky().get(0));
        
    }
}
