
package ica_validation_tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jgaliweather.algorithm.ICA_operators.ICAConditionsOperator;
import jgaliweather.algorithm.ICA_operators.ICAOperator;
import jgaliweather.algorithm.ICA_operators.ICARainOperator;
import jgaliweather.algorithm.ICA_operators.ICASkyStateOperator;
import jgaliweather.algorithm.ICA_operators.ICAWindOperator;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.PartitionReader;
import jgaliweather.configuration.template_reader.TemplateReader;
import jgaliweather.configuration.variable_reader.VariableReader;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
import jgaliweather.nlg.nlg_generators.ICAGenerator;
import org.javatuples.Pair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;

/**
 *
 * @author Difma
 */
public class CP41 {
    
    public CP41() {
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
            TemplateReader tr = new TemplateReader();
            tr.parseFile("Configuration/templates.xml");
            
            VariableReader vr = new VariableReader();
            vr.parseFile("Configuration/variables.xml");
            
            PartitionReader pr = new PartitionReader();
            pr.parseFile("Configuration/partitions.xml");
            HashMap<String, Partition> partitions = pr.getPartitions();
            
            Variable wind_var = new Variable("Viento");
            
            wind_var.getValues().add(new Value(302, 0));
            wind_var.getValues().add(new Value(302, 1));
            wind_var.getValues().add(new Value(304, 2));
            wind_var.getValues().add(new Value(303, 3));
            wind_var.getValues().add(new Value(303, 4));
            wind_var.getValues().add(new Value(305, 5));
            wind_var.getValues().add(new Value(313, 6));
            wind_var.getValues().add(new Value(322, 7));
            wind_var.getValues().add(new Value(322, 8));
            
            Pair<Integer, Integer> WIND_INTERVAL = new Pair(309, 332);
            
            ICAWindOperator w_op = new ICAWindOperator(WIND_INTERVAL, wind_var, 9);
            
            ArrayList<Double> wind_salida = w_op.applyOperator();
            
            Variable sky_var = new Variable("Meteoro");
            
            sky_var.getValues().add(new Value(104, 0));
            sky_var.getValues().add(new Value(116, 1));
            sky_var.getValues().add(new Value(116, 2));
            sky_var.getValues().add(new Value(103, 3));
            sky_var.getValues().add(new Value(108, 4));
            sky_var.getValues().add(new Value(103, 5));
            sky_var.getValues().add(new Value(104, 6));
            sky_var.getValues().add(new Value(103, 7));
            sky_var.getValues().add(new Value(103, 8));
            
            ICASkyStateOperator ss_op = new ICASkyStateOperator(partitions.get("C"), sky_var, 9);
            
            ArrayList<HashMap<String, Double>> sky_salida = ss_op.applyOperator();
            
            ICARainOperator r_op = new ICARainOperator(partitions.get("R"), sky_var, 9);
            
            ArrayList<Double> rain_salida = r_op.applyOperator();
            
            ICAConditionsOperator icac_op = new ICAConditionsOperator(wind_salida, rain_salida, sky_salida, 9);
            
            ArrayList<String> ica_cond_output = icac_op.defineConditions();
            
            Variable curr_var = new Variable("Meteoro");
            
            curr_var.getValues().add(new Value(5, 0));
            curr_var.getValues().add(new Value(3, 1));
            curr_var.getValues().add(new Value(1, 2));
            
            ICAOperator ica_op = new ICAOperator(partitions.get("ICA"), curr_var);
            
            String ica_output = ica_op.applyOperator();
            
            ICAGenerator nssg = new ICAGenerator(tr.getLabelsets().get("ICA"), ica_output, ica_cond_output);
            
            String salida = nssg.generate();
            
            /*
             *   En lo que se refiere al estado de la calidad del aire, se espera que mejore progresivamente a
             *   bueno, favorecido por el viento esperado para los próximos días.
             */
            assertThat(salida, anyOf(is("With respect to air quality state, it is expected to improve progressively to good, favored by the wind expected during the coming days."), 
                    is("With respect to air quality state, it is expected to improve progressively to good, favored by the wind expected during the next few days.")));
        } catch (Exception ex) {
            Logger.getLogger(CP41.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
