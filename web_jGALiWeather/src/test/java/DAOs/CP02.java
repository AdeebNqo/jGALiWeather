/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import com.diego.DAOs.DatabaseConnector;
import com.diego.VOs.MeteorologicalDataDay;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Difma
 */
public class CP02 {
    
    public CP02() {
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
        
        String data = null;
        
        try{
            
            data = DatabaseConnector.getInstance().getComment(27027, new Date());
            
        } catch (Exception e) {}
        
        //Assert.assertEquals(150, data.getSky().get(0));
        
    }
}
