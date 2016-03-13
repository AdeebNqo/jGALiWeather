package jgaliweather.algorithm.weather_operators;

import java.util.ArrayList;
import jgaliweather.data.data_structures.Value;
import jgaliweather.data.data_structures.Variable;

/*
    Implements an operator which generates a wind
    linguistic description, including information about
    the wind episodes and changes in direction and intensity.
 */
public class WindOperator {

    private ArrayList<Integer> code_range;
    private Variable data;

    /*
        Initializes a WindOperator object

        :param valid_code_range: The wind code range considered relevant
        :param data: The wind forecast source data

        :return: A new WindOperator object
     */
    public WindOperator(ArrayList<Integer> code_range, Variable data) {
        this.code_range = code_range;
        this.data = data;
    }

    /*
        Extracts wind episodes from the source data and encodes them
        into linguistic description strings which are composed of
        temporal references and wind codes (intensity and direction)

        :return: A list of linguistic descriptions as strings, containing
        index references to wind values in the source data, as well as
        the wind values associated to those indices.
     */
    public ArrayList<String> applyOperator() {

        ArrayList<ArrayList> sequences = new ArrayList();
        ArrayList<Value> current_sequence = null;

        for (int i = 0; i < data.getValues().size(); i++) {
            if (code_range.get(0) <= data.getValues().get(i).getData()
                    && data.getValues().get(i).getData() <= code_range.get(1)) {
                if (current_sequence != null) {
                    current_sequence.add(data.getValues().get(i));
                } else {
                    current_sequence = new ArrayList();
                    sequences.add(current_sequence);
                    current_sequence.add(data.getValues().get(i));
                }
            } else {
                current_sequence = null;
            }
        }

        ArrayList<String> periods = new ArrayList();
        for (int i = 0; i < sequences.size(); i++) {
            ArrayList<Value> s = sequences.get(i);

            if (s.size() == 1) {
                String text = s.get(0).getTemp_location() + " " + s.get(0).getData();
                periods.add(text);
            } else {
                String interval = s.get(0).getTemp_location() + "-" + s.get(s.size() - 1).getTemp_location();
                String labels = "";

                for (int j = 0; j < s.size(); j++) {
                    labels = labels.concat(s.get(j).getData() + " ");
                }

                labels = labels.trim();
                String text = interval + " " + labels;
                periods.add(text);
            }
        }

        return periods;
    }

}
