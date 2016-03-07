package configuration_tests;

import jgaliweather.configuration.partition_reader.PartitionReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Difma
 */
public class CP07 {

    public CP07() {
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

        Assert.assertEquals("ObjectSet P: 107, 108, 111, 120", pr.getPartitions().get("R").getSets().get(1).toString());
        Assert.assertEquals("CrispInterval MD: LeftClosed {-6.0, -2.0}", pr.getPartitions().get("V").getSets().get(2).toString());
        Assert.assertEquals("FuzzySet REL: _/¯\\_(0.2, 0.4, 0.6, 0.2)", pr.getPartitions().get("CP").getSets().get(1).toString());
    }
}
