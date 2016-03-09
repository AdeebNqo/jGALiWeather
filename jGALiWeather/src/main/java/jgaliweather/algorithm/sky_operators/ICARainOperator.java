package jgaliweather.algorithm.sky_operators;

import java.util.ArrayList;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.Set;
import jgaliweather.data.data_structures.Variable;

/*
    Implements an operator which measures the ratio of
    appearance of relevant precipitations within the
    source forecast data.
 */
public class ICARainOperator {

    private Partition rain_partition;
    private Variable data;
    private int max_length;

    /*
        Initializes this class

        :param rain_partition: A partition object set which
        defines the valid precipitation symbols.
        :param data: The weather forecast source data
        :param max_length: The length of the data to be used

        :return: A new ICARainOperatorObject
     */
    public ICARainOperator(Partition rain_partition, Variable data, int max_length) {
        this.rain_partition = rain_partition;
        this.data = data;
        if (data.getValues().size() < max_length) {
            this.max_length = data.getValues().size();
        } else {
            this.max_length = max_length;
        }
    }

    /*
        Measures the appearance of relevant precipitations within the weather data.
        The ratio of appearance is obtained for incrementing starting days
        and decrementing data lengths.

        :return: A list including the precipitation percentages for each day length
        in reverse order (3 days (starting at pos. 0), 2 days (starting at pos. 1),
        1 day (starting at pos. 2)
     */
    public ArrayList<Double> applyOperator() {

        ArrayList<Double> rain_percentages = new ArrayList();
        int rain_count;

        for (int i = 0; i < max_length / 3; i++) {
            rain_count = 0;
            for (int j = i * 3; j < max_length; j++) {
                for (Set s : rain_partition.getSets()) {
                    if (s.apply(data.getValues().get(j).getData()) > 0) {
                        rain_count += 1;
                    }
                }
            }
            rain_percentages.add(new Double(rain_count) / new Double(max_length / (3 * (i + 1))));
        }
        return rain_percentages;
    }
}
