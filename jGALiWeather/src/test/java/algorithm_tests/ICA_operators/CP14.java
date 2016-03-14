package algorithm_tests.ICA_operators;

import java.util.ArrayList;
import jgaliweather.algorithm.ICA_operators.ICAWindOperator;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
import org.javatuples.Pair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/* Tests ICAWind operators */
public class CP14 {

    public CP14() {
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

        Variable curr_var = new Variable("Viento");

        curr_var.getValues().add(new Value(317, 0));
        curr_var.getValues().add(new Value(320, 1));
        curr_var.getValues().add(new Value(320, 2));
        curr_var.getValues().add(new Value(317, 3));
        curr_var.getValues().add(new Value(320, 4));
        curr_var.getValues().add(new Value(320, 5));
        curr_var.getValues().add(new Value(332, 6));
        curr_var.getValues().add(new Value(317, 7));
        curr_var.getValues().add(new Value(332, 8));

        Pair<Integer, Integer> WIND_INTERVAL = new Pair(309, 332);

        ICAWindOperator w_op = new ICAWindOperator(WIND_INTERVAL, curr_var, 9);

        ArrayList<Double> salida_esperada = new ArrayList();
        salida_esperada.add(3.0);
        salida_esperada.add(6.0);
        salida_esperada.add(3.0);

        ArrayList<Double> salida = w_op.applyOperator();

        for (int i = 0; i < salida.size(); i++) {
            Assert.assertEquals(salida.get(i), salida_esperada.get(i));
        }
    }
}
