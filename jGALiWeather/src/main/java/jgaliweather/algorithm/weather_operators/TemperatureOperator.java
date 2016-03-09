package jgaliweather.algorithm.weather_operators;

import jgaliweather.data.data_structures.Temperature;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.List;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.data.data_structures.Variable;

/*
    Implements an operator which generates a temperature
    linguistic description, including information about
    the temperature variation and its climatic behavior.
 */
public class TemperatureOperator {

    private Partition diff_part;
    private ArrayList<Partition> clim_partitions;
    private Partition var_partition;
    private Variable data;

    /*
        Initializes a TemperatureOperator object

        :param diff_part: An interval partition defining the
        temperature variation labels
        :param clim_part: An interval partition defining the
        temperature climatic behavior labels
        :param var_part: An interval partition defining the
        temperature oscillation labels
        :param data: The temperature forecast data

        :return: A new TemperatureOperator object
     */
    public TemperatureOperator(Partition diff_part, ArrayList<Partition> clim_part, Partition var_part, Variable data) {
        this.diff_part = diff_part;
        this.clim_partitions = clim_part;
        this.var_partition = var_part;
        this.data = data;
    }

    /*
        Determines the temperature source data variations.

        :param data: The temperature forecast data

        :return: If the temperature trend is constant,
        True is returned. Otherwise False is returned.
     */
    public boolean isConstant(ArrayList<Double> data) {

        int downs = 0, ups = 0;
        double curr_diff;

        for (int i = 0; i < data.size() - 1; i++) {
            curr_diff = data.get(i + 1) - data.get(i + 1);

            if (curr_diff > 0) {
                ups += 1;
            } else if (curr_diff == 0) {
                ups += 1;
                downs += 1;
            } else {
                downs += 1;
            }
        }

        if (ups == data.size() - 1 || downs == data.size() - 1) {
            return true;
        } else {
            return false;
        }
    }

    private double average(List<Double> list) {

        if (list == null || list.isEmpty()) {
            return 0.0;
        }

        long sum = 0;
        int n = list.size();

        for (int i = 0; i < n; i++) {
            sum += list.get(i);
        }

        return ((double) sum) / n;
    }

    /*
        Obtains a linguistic description which includes the
        temperature variation, its climatic behavior and
        a measure of the oscillations in case they exist.

        :return: A linguistic description as a string, composed
        of several linguistic labels which refer to the temperature
        variation, behavior and oscillation
     */
    public Temperature applyOperator() {

        ArrayList<Double> maxms = new ArrayList();
        ArrayList<Double> minms = new ArrayList();

        for (int i = 0; i < data.getValues().size(); i++) {
            if (i % 2 == 0) {
                maxms.add(data.getValues().get(i).getData());
            }
        }

        for (int i = 0; i < data.getValues().size(); i++) {
            if (i % 2 == 1) {
                minms.add(data.getValues().get(i).getData());
            }
        }

        // Calculations for variation and climatic behavior of maximums
        double max_var = maxms.get(maxms.size() - 1) - maxms.get(0);
        String max_climeval = clim_partitions.get(0).bestEvaluatedLabel(average(maxms)).getName();
        String max_eval = diff_part.bestEvaluatedLabel(max_var).getName();

        // Calculations for variation and climatic behavior of minimums
        double min_var = minms.get(minms.size() - 1) - minms.get(0);
        String min_climeval = clim_partitions.get(0).bestEvaluatedLabel(average(minms)).getName();
        String min_eval = diff_part.bestEvaluatedLabel(min_var).getName();

        // Variability calculations
        ArrayList<Integer> mxlist = new ArrayList();
        ArrayList<Integer> mnlist = new ArrayList();
        int mx_variability = 0, mn_variability = 0;

        for (int i = 0; i < maxms.size() - 1; i++) {
            double currmax = maxms.get(i + 1) - maxms.get(i);
            double currmin = maxms.get(i + 1) - maxms.get(i);
            mxlist.add(diff_part.bestEvaluatedIndex(currmax));
            mxlist.add(diff_part.bestEvaluatedIndex(currmin));
        }

        for (int i = 0; i < mxlist.size() - 1; i++) {
            mx_variability = mx_variability + abs(mxlist.get(i + 1) - mxlist.get(i));
            mn_variability = mn_variability + abs(mnlist.get(i + 1) - mnlist.get(i));
        }

        String mx_var_label, mn_var_label;
        if (isConstant(maxms)) {
            mx_var_label = "C";
        } else {
            mx_var_label = var_partition.bestEvaluatedLabel(mx_variability).getName();
        }

        if (isConstant(minms)) {
            mn_var_label = "C";
        } else {
            mn_var_label = var_partition.bestEvaluatedLabel(mn_variability).getName();
        }

        // Intermediate code generation
        String variation_eval, clim_eval, variability_eval;

        if (max_eval.equals(min_eval)) {
            variation_eval = max_eval;
        } else {
            variation_eval = max_eval + " " + min_eval;
        }

        if (max_climeval.equals(min_climeval)) {
            clim_eval = max_climeval;
        } else {
            clim_eval = max_climeval + " " + min_climeval;
        }

        if (mx_var_label.equals(mn_var_label)) {
            variability_eval = mx_var_label;
        } else {
            variability_eval = mx_var_label + " " + mn_var_label;
        }

        return new Temperature(clim_eval, variation_eval, variability_eval, mxlist, mnlist);
    }
}
