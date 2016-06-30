package algorithm_tests.weather_operators;

import java.util.ArrayList;
import jgaliweather.algorithm.weather_operators.WindOperator;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
import org.javatuples.Pair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/* Tests Wind operators */
public class CP08 {

    public CP08() {
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

        curr_var.getValues().add(new Value(320, 0));
        curr_var.getValues().add(new Value(320, 1));
        curr_var.getValues().add(new Value(320, 2));
        curr_var.getValues().add(new Value(320, 3));
        curr_var.getValues().add(new Value(320, 4));
        curr_var.getValues().add(new Value(301, 5));
        curr_var.getValues().add(new Value(332, 6));
        curr_var.getValues().add(new Value(317, 7));
        curr_var.getValues().add(new Value(332, 8));
        curr_var.getValues().add(new Value(302, 9));
        curr_var.getValues().add(new Value(317, 10));
        curr_var.getValues().add(new Value(332, 11));

        Pair<Integer, Integer> WIND_INTERVAL = new Pair(317, 332);

        WindOperator w_op = new WindOperator(WIND_INTERVAL, curr_var);

        ArrayList<String> salida_esperada = new ArrayList();
        salida_esperada.add("0-4 320 320 320 320 320");
        salida_esperada.add("6-8 332 317 332");
        salida_esperada.add("10-11 317 332");

        ArrayList<String> salida = w_op.applyOperator();

        for (int i = 0; i < salida.size(); i++) {
            Assert.assertEquals(salida.get(i), salida_esperada.get(i));
        }
    }
}
