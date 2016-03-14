package jgaliweather.algorithm.weather_operators;

import java.util.ArrayList;
import java.util.HashMap;
import jgaliweather.configuration.partition_reader.Set;
import jgaliweather.data.data_structures.Variable;

/*
    Implements an operator which generates a fog
    linguistic description, including information about
    fog episodes and their persistence in time.
 */
public class FogOperator {

    private Set fog_set;
    private Variable data;

    /*
        Initializes a FogOperator object

        :param fog_set: An object partition set defining fog values
        :param data_series: The sky state forecast source data

        :return: A new FogOperator object
     */
    public FogOperator(Set fog_set, Variable data_series) {
        this.fog_set = fog_set;
        this.data = data_series;
    }

    /*
        Obtains a dictionary of fog episodes, where the parts of a day
        are used as keys to indicate the start of a fog episode

        :return: A dictionary of fog episodes
     */
    public HashMap<Double, ArrayList<Double>> applyOperator() {

        HashMap<Double, ArrayList<Double>> e = new HashMap();
        ArrayList<Double> currentFog = null;
        double curDay = 0;

        for (double i = 0; i < data.getValues().size(); i++) {
            if (curDay != i / 3) {
                curDay = i / 3;
                currentFog = null;
            }
            if (fog_set.apply(data.getValues().get((int) i).getData()) == 1) {
                if (currentFog == null) {
                    currentFog = new ArrayList();
                    currentFog.add(i / 3);
                    e.put(i % 3, currentFog);
                } else {
                    currentFog.add(i / 3);
                }
            } else {
                currentFog = null;
            }
        }
        
        return e;
        
    }
}
