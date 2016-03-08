package jgaliweather.data.data_filters;

import jgaliweather.data.data_structures.Variable;

/*
    Implements a filter which simplifies meteorologic
    number codes to allow further processing of them
    during the application execution (only used with
    sky state values).
 */
public class MeteorologicFilter {

    Variable variable;

    /*
        Initializes a MeteorologicFilter object

        :param variable: Variable input data to be
        filtered

        :return: A new MeteorologicFilter data
     */
    public MeteorologicFilter(Variable variable) {
        this.variable = variable;
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    /* Filters the input data meteorologic codes */
    public void filter() {

        for (int i = 0; i < variable.getValues().size(); i++) {
            variable.getValues().get(i).setData((int) variable.getValues().get(i).getData());
            if (variable.getValues().get(i).getData() > 200) {
                variable.getValues().get(i).setData(variable.getValues().get(i).getData() - 100);
            }
        }
    }
}
