package jgaliweather.data.data_filters;

import jgaliweather.data.data_structures.Location;
import jgaliweather.data.data_structures.Variable;

/*  
    Implements a filter which determines if the input data
    is invalid and also removes invalid data, if possible.
 */
public class DataFilter {

    private Variable variable;
    private Location location;

    /*
        Initializes a DataFilter object

        :param location: Location identifier associated
        to the input data
        :param variable: Variable input data to be filtered

        :return: A new DataFilter object
     */
    public DataFilter(Variable variable, Location location) {
        this.variable = variable;
        this.location = location;
    }

    /* 
        Filters the input data, trimming invalid data
        and checking if it is still present. An
        InvalidVariableException is raised if this occurs.
     */
    public void filter() throws InvalidVariableException {

        for (int i = 0; i < variable.getValues().size(); i++) {
            if (variable.getValues().get(i).getData() == -9999) {
                variable.getValues().remove(i);
                i--;
            }
        }

        variable.setActual_data_length(variable.getValues().size());

        for (int i = 0; i < variable.getValues().size(); i++) {
            if (variable.getValues().get(i).getTemp_location() != i) {
                throw new InvalidVariableException(location, variable.getValues().get(i), i);
            }
        }
    }
}
