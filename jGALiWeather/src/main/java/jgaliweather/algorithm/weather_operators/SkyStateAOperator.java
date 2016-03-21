package jgaliweather.algorithm.weather_operators;

import java.util.ArrayList;
import java.util.Arrays;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.partition_reader.Set;
import jgaliweather.data.data_structures.Variable;

/*
    Implements an operator which generates a cloud coverage
    linguistic description, including information about
    the sky state in chronological subperiods.
 */
public class SkyStateAOperator {

    private Partition sky_partition;
    private Partition period_partition;
    private Variable data;

    /*
        Initializes a SkyStateAOperator object

        :param sky_partition: An object partition set defining
        cloud coverage labels
        :param period_partition: A partition of temporal intervals
        :param data_series: The sky state forecast source data

        :return: A new SkyStateAOperator object
     */
    public SkyStateAOperator(Partition sky_partition, Partition period_partition, Variable data) {
        this.sky_partition = sky_partition;
        this.period_partition = period_partition;
        this.data = data;
    }

    /*
        Obtains a linguistic description of the cloud coverage state,
        according to the predominance of each type of cloud coverage
        and the temporal partition of the short-term period

        :return: A linguistic description as a string, containing
        cloud coverage labels associated to temporal intervals by
        order of appearance
     */
    public String applyOperator() {

        ArrayList<double[]> evaluations = new ArrayList();

        for (Set p : sky_partition.getSets()) {
            double[] e = new double[data.getValues().size()];
            Arrays.fill(e, 0);

            for (int i = 0; i < data.getValues().size(); i++) {
                e[i] = p.apply(data.getValues().get(i).getData());
            }
            evaluations.add(e);
        }

        int aggr;
        int[][] table = new int[period_partition.getSets().size()][sky_partition.getSets().size()];

        for (int i = 0; i < table.length; i++) {
            Arrays.fill(table[i], 0);
        }

        for (int i = 0; i < period_partition.getSets().size(); i++) {
            for (int j = 0; j < evaluations.size(); j++) {
                aggr = 0;
                for (int k = 0; k < evaluations.get(j).length; k++) {
                    aggr += period_partition.getSets().get(i).apply(k) * evaluations.get(j)[k];
                }
                table[i][j] = aggr;
            }
        }

        int best_labels[] = new int[period_partition.getSets().size()];
        int best_values[] = new int[period_partition.getSets().size()];
        int max = -9999, min_value = 999999;

        // Max value on any single row
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (max < table[i][j]) {
                    max = table[i][j];
                    best_labels[i] = j;
                    best_values[i] = table[i][j];
                }

            }
            max = -9999;
        }

        for (int i = 0; i < best_values.length; i++) {
            if (best_values[i] < min_value) {
                min_value = best_values[i];
            }
        }

        String intermediate_code = "";
        if (min_value < 3) {
            intermediate_code = "V";
        } else {
            for (int i : best_labels) {
                intermediate_code = intermediate_code.concat(sky_partition.getSets().get(i).getName() + " ");
            }
        }

        return intermediate_code.trim();

    }

}
