package jgaliweather.algorithm.weather_operators;

import java.util.Arrays;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;
import org.apache.commons.lang3.ArrayUtils;

/*
    Implements an operator which generates a cloud coverage
    linguistic description, including information about
    the sky state for the whole short-term, using quantifiers.
 */
public class SkyStateBOperator {

    private Partition sky_partition;
    private Partition quantifiers_partition;
    private Variable data;

    /*
        Initializes a SkyStateBOperator object

        :param sky_partition: An object partition set defining
        cloud coverage labels
        :param quantifiers_partition: A quantifier partition set
        :param data_series: The sky state forecast source data

        :return: A new SkyStateBOperator object
     */
    public SkyStateBOperator(Partition sky_partition, Partition quantifiers_partition, Variable data) {
        this.sky_partition = sky_partition;
        this.quantifiers_partition = quantifiers_partition;
        this.data = data;
    }

    /*
        Obtains a linguistic description of the cloud coverage state,
        as a matrix of index labels associated to quantifiers, including
        the percentage of cloud coverage labels

        :return: A linguistic description as a numpy matrix, containing
        indices to the most predominant cloud coverage labels and their
        associated numeric percentages
     */
    public double[][] applyOperator() {

        double[] e = new double[sky_partition.getSets().size()];
        Arrays.fill(e, 0);

        for (int i = 0; i < e.length; i++) {
            for (Value v : data.getValues()) {
                e[i] += sky_partition.getSets().get(i).apply(v.getData());
            }
        }

        for (int i = 0; i < e.length; i++) {
            e[i] = e[i] / data.getValues().size();
        }

        double[][] table = new double[sky_partition.getSets().size()][quantifiers_partition.getSets().size()];

        for (int i = 0; i < sky_partition.getSets().size(); i++) {
            for (int j = 0; j < quantifiers_partition.getSets().size(); j++) {
                table[i][j] = quantifiers_partition.getSets().get(j).apply(e[i]);
            }
        }

        double[] best_labels = new double[table.length];
        double[] aux = new double[table.length];
        int max = -9999;

        // Max value on any single row
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (max < table[i][j]) {
                    best_labels[i] = j;
                }

            }
            aux[i] = i;
            max = -9999;
        }

        //Vertical Stack
        double[][] best_labels_vs = new double[3][table.length];
        best_labels_vs[0] = best_labels;
        best_labels_vs[1] = aux;
        best_labels_vs[2] = e;

        //Matrix Transposed
        double[][] best_labels_vs_tranposed = new double[table.length][3];

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < 3; j++) {
                best_labels_vs_tranposed[i][j] = best_labels_vs[j][i];
            }
        }

        //Matrix flipped
        double[] best_labels_vs_tranposed_first_column = new double[best_labels_vs_tranposed.length];
        for (int i = 0; i < best_labels_vs_tranposed.length; i++) {
            best_labels_vs_tranposed_first_column[i] = best_labels_vs_tranposed[i][0];
        }

        double[][] best_labels_vs_tranposed_aux = new double[best_labels_vs_tranposed.length][3];
        System.arraycopy(best_labels_vs_tranposed, 0, best_labels_vs_tranposed_aux, 0, best_labels_vs_tranposed_first_column.length);

        double[][] best_labels_vs_tranposed_aux_tranposed = new double[3][best_labels_vs_tranposed.length];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < best_labels_vs_tranposed.length; j++) {
                best_labels_vs_tranposed_aux_tranposed[i][j] = best_labels_vs_tranposed[j][i];
            }
        }

        double[][] best_labels_vs_tranposed_aux_tranposed_flipped = new double[table.length][3];
        for (int i = 0; i < best_labels_vs_tranposed_aux_tranposed_flipped.length; i++) {
            ArrayUtils.reverse(best_labels_vs_tranposed_aux_tranposed_flipped[i]);
        }

        return best_labels_vs_tranposed_aux_tranposed_flipped;
    }
}
