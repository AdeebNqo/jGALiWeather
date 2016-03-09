package jgaliweather.algorithm.sky_operators;

import java.util.ArrayList;
import jgaliweather.data.data_structures.Variable;
import org.javatuples.Pair;

/*
    Implements an operator which measures the ratio of
    appearance of relevant wind within the source forecast
    data.
 */
public class ICAWindOperator {

    private Pair<Integer, Integer> code_range;
    private Variable data;
    private int max_length;

    /*
        Initializes this class

        :param valid_code_range: The wind code range to be measured
        :param data: The weather forecast source data
        :param max_length: The length of the data to be used

        :return: A new ICAWindOperator object
     */
    public ICAWindOperator(Pair<Integer, Integer> valid_code_range, Variable data, int max_length) {
        this.code_range = valid_code_range;
        this.data = data;
        if (data.getValues().size() < max_length) {
            this.max_length = data.getValues().size();
        } else {
            this.max_length = max_length;
        }
    }

    /*
        Measures the appearance of relevant wind within the weather data.
        The ratio of appearance is obtained for incrementing starting days
        and decrementing data lengths.

        :return: A list including the wind percentages for each day length in
        reverse order (3 days (starting at pos. 0), 2 days (starting at pos. 1),
        1 day (starting at pos. 2)
     */
    public ArrayList<Double> applyOperator() {

        ArrayList<Double> wind_percentages = new ArrayList();
        int wind_count;

        for (int i = 0; i < max_length / 3; i++) {
            wind_count = 0;
            for (int j = i * 3; j < max_length; j++) {
                if (code_range.getValue0() <= data.getValues().get(j).getData()
                        && data.getValues().get(j).getData() <= code_range.getValue1()) {
                    wind_count += 1;
                }
            }
            wind_percentages.add(new Double(wind_count) / new Double(max_length / (3 * (i + 1))));
        }
        return wind_percentages;
    }
}
