/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm_tests.weather_operators;

import java.util.ArrayList;
import java.util.HashMap;
import jgaliweather.algorithm.weather_operators.SkyStateAOperator;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.PartitionReader;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/* Tests SkyStateA operators */
public class CP12 {

    public CP12() {
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

        PartitionReader pr = new PartitionReader();
        pr.parseFile("Configuration/partitions.xml");
        HashMap<String, Partition> partitions = pr.getPartitions();

        Variable curr_var = new Variable("Meteoro");

        curr_var.getValues().add(new Value(101, 0));
        curr_var.getValues().add(new Value(102, 1));
        curr_var.getValues().add(new Value(114, 2));
        curr_var.getValues().add(new Value(1, 2));//Separador
        curr_var.getValues().add(new Value(103, 3));
        curr_var.getValues().add(new Value(119, 4));
        curr_var.getValues().add(new Value(109, 5));
        curr_var.getValues().add(new Value(1, 2));//Separador
        curr_var.getValues().add(new Value(115, 6));
        curr_var.getValues().add(new Value(101, 7));
        curr_var.getValues().add(new Value(121, 8));
        curr_var.getValues().add(new Value(1, 2));//Separador
        curr_var.getValues().add(new Value(120, 9));
        curr_var.getValues().add(new Value(116, 10));
        curr_var.getValues().add(new Value(106, 11));

        SkyStateAOperator nssa_op = new SkyStateAOperator(partitions.get("C"), partitions.get("SSFTP"), curr_var);

        String salida = nssa_op.applyOperator();

        System.out.println(salida);

    }
}
