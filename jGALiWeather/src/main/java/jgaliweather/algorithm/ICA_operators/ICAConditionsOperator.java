package jgaliweather.algorithm.ICA_operators;

import java.util.ArrayList;
import java.util.HashMap;

/*
    Implements an operator which includes expert rules
    in order to discern the fittest weather situation,
    according to the wind, rain and cloud coverage values
    obtained by the weather operators.
 */
public class ICAConditionsOperator {

    private final String RAIN_AND_WIND = "RW";
    private final String RAIN = "R";
    private final String WIND = "W";
    private final String SUNNY = "S";
    private final String DRY = "D";

    private ArrayList<Double> wind_data;
    private ArrayList<Double> rain_data;
    private ArrayList<HashMap<String, Double>> sky_data;
    private int max_length;

    /*
        Initializes this class

        :param wind_data: A list of wind appearance percentages
        :param rain_data: A list of precipitation appearance percentages
        :param sky_data: A list of cloud coverage state appearance
        percentages

        :return: A new ICAConditionsOperator object
     */
    public ICAConditionsOperator(ArrayList<Double> wind_data, ArrayList<Double> rain_data, ArrayList<HashMap<String, Double>> sky_data, int max_length) {
        this.wind_data = wind_data;
        this.rain_data = rain_data;
        this.sky_data = sky_data;
        this.max_length = max_length;
    }

    /*
        Applies rules to determine the fittest situation for a given
        set of weather values.

        :return: A label which defines a weather situation
     */
    private String selectCondition(double wind_value, double rain_value, HashMap<String, Double> sky_value) {

        if (rain_value > 0.4 && wind_value > 0.4) {
            return RAIN_AND_WIND;
        } else if (rain_value > 0.4) {
            return RAIN;
        } else if (wind_value > 0.4) {
            return WIND;
        } else if (sky_value.get("C") > 0.6 && rain_value < 0.4 && wind_value < 0.4) {
            return SUNNY;
        } else if ((sky_value.get("C") + sky_value.get("CL")) > 0.8 && rain_value < 0.4 && wind_value < 0.4) {
            return DRY;
        }

        return "Failed";
    }

    /*
        Obtains a weather condition for for each day length in reverse order (3 days (starting
        at pos. 0), 2 days (starting at pos. 1), 1 day (starting at pos. 2)

        :return: A list of weather condition labels for each day length in reverser order
     */
    public ArrayList<String> defineConditions() {

        ArrayList<String> conditions = new ArrayList();

        for (int i = 0; i < max_length / 3; i++) {
            conditions.add(selectCondition(wind_data.get(i), rain_data.get(i), sky_data.get(i)));
        }

        return conditions;
    }
}
