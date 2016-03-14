package jgaliweather.algorithm.ICA_operators;

import java.util.ArrayList;
import java.util.HashMap;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.Set;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;

/*
    Implements an operator which measures the ratio of
    appearance of each possible cloud coverage state
    within the source forecast data.
 */
public class ICASkyStateOperator {

    private Partition sky_partition;
    private Variable data;
    private int max_length;

    public ICASkyStateOperator(Partition sky_partition, Variable data, int max_length) {
        this.sky_partition = sky_partition;
        this.data = data;
        if (data.getValues().size() < max_length) {
            this.max_length = data.getValues().size();
        } else {
            this.max_length = max_length;
        }
    }

    /*
        Measures the appearance of the different cloud coverage states
        within the weather data. The ratio of appearance is obtained for
        incrementing starting days and decrementing data lengths, for each
        cloud coverage state.

        :return: A list of dictionaries including the cloud coverage state
        percentages for each day length in reverse order (3 days (starting
        at pos. 0), 2 days (starting at pos. 1), 1 day (starting at pos. 2)
     */
    public ArrayList<HashMap<String, Double>> applyOperator() {

        ArrayList<HashMap<String, Double>> sky_percentages = new ArrayList();
        HashMap<String, Double> e;
        double aux;
        Value v;

        for (int i = 0; i < max_length / 3; i++) {
            e = new HashMap();
            for (Set s : sky_partition.getSets()) {
                for (int j = i*3; j < max_length; j++) {
                    v = data.getValues().get(j);
                    if (!e.containsKey(s.getName())) {
                        aux = 0;
                    } else {
                        aux = e.get(s.getName());
                    }                  
                    e.put(s.getName(), aux + new Double(s.apply(v.getData())) / new Double(max_length / (3 * (i + 1))));
                }
            }
            sky_percentages.add(e);
        }
        return sky_percentages;
    }
}
