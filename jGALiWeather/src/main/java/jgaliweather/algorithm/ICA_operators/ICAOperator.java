package jgaliweather.algorithm.ICA_operators;

import java.util.ArrayList;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.data.data_structures.Variable;

/*
    Implements an operator which obtains the air
    quality state trend from a given source data.
 */
public class ICAOperator {

    private Partition ica_partition;
    private Variable data;

    /*
        Initializes this class

        :param ica_partition: A list of air quality state labels
        :param data_series: An air quality state data source

        :return: A new ICAOperator object
     */
    public ICAOperator(Partition ica_partition, Variable data) {
        this.ica_partition = ica_partition;
        this.data = data;
    }

    /*
        Calculates and encodes the air quality state trend
        into a linguistic description, based on its variations.

        :return: A 3-element tuple composed of two trend labels
        and the air quality state label which prevails.
     */
    public String applyOperator() {

        ArrayList<String> diffs = new ArrayList();
        double curr_diff;

        for (int i = 0; i < data.getValues().size() - 1; i++) {
            curr_diff = data.getValues().get(i + 1).getData() - data.getValues().get(i).getData();
            if (curr_diff > 0) {
                diffs.add("+");
            } else if (curr_diff == 0) {
                diffs.add("0");
            } else {
                diffs.add("-");
            }
        }

        return diffs.get(0) + " " + diffs.get(1) + " "
                + ica_partition.bestEvaluatedLabel(data.getValues().get(data.getValues().size() - 1).getData()).getName();
    }
}
