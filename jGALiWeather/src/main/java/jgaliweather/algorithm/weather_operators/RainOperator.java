package jgaliweather.algorithm.weather_operators;

import java.util.ArrayList;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;

/*
    Implements an operator which generates a precipitation
    linguistic description, including information about
    the kind of precipitation.
 */
public class RainOperator {

    private Partition rain_partition;
    private Variable data;

    /*
        Initializes a RainOperator object

        :param rain_partition: An object set partition defining
        precipitation labels
        :param data_series: The precipitation forecast source
        data

        :return: A new RainOperator object
     */
    public RainOperator(Partition rain_partition, Variable data) {
        this.rain_partition = rain_partition;
        this.data = data;
    }

    /*
        Extracts precipitation episodes from the source data and
        encodes them into linguistic description strings, composed of
        temporal references and precipitation labels (type of
        precipitation)

        :return: A list of linguistic descriptions as strings, containing
        index references to precipitation values in the source data, as
        well as the precipitation labels associated to those indices.
     */
    public ArrayList<String> applyOperator() {

        ArrayList<ArrayList> sequences = new ArrayList();
        ArrayList<Value> current_sequence = null;

        for (int i = 0; i < data.getValues().size(); i++) {
            for (int j = 0; j < rain_partition.getSets().size(); j++) {
                if (rain_partition.getSets().get(j).apply(data.getValues().get(i).getData()) > 0) {
                    if (current_sequence != null) {
                        current_sequence.add(data.getValues().get(i));
                    } else {
                        current_sequence = new ArrayList();
                        current_sequence.add(data.getValues().get(i));
                    }
                    break;
                } else {
                    sequences.add(current_sequence);
                    current_sequence = null;
                }

            }

        }
        if (current_sequence != null) {
            sequences.add(current_sequence);
        }

        ArrayList<String> periods = new ArrayList();
        for (int i = 0; i < sequences.size(); i++) {
            ArrayList<Value> s = sequences.get(i);

            if (s.size() == 1) {
                String text = s.get(0).getTemp_location() + " " + rain_partition.bestEvaluatedLabel(s.get(0).getData()).getName();
                periods.add(text);
            } else {
                String interval = s.get(0).getTemp_location() + "-" + s.get(s.size() - 1).getTemp_location();
                String labels = "";

                for (int j = 0; j < s.size(); j++) {
                    labels = labels.concat(rain_partition.bestEvaluatedLabel(s.get(j).getData()).getName() + " ");
                }

                labels = labels.trim();
                String text = interval + " " + labels;
                periods.add(text);
            }
        }

        return periods;

    }
}
