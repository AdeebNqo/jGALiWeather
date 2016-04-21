package jgaliweather.data.data_filters;

import jgaliweather.configuration.variable_reader.XMLVariable;
import jgaliweather.data.data_structures.Location;
import jgaliweather.data.data_structures.Variable;

/*
    Implements a filter which detects if the length
    of the date contained in a input variable is supported
    by the application.
 */
public class DataLengthFilter {

    private Location location;
    private XMLVariable template_variable;
    private Variable real_variable;

    /*
        Initializes a DataLengthFilter object

        :param location: The location identifier associated to the
        input variable
        :param template_variable: The input variable definition
         found in the variable definition file
        :param real_variable: The input variable data

        :return: A new DataLengthFilter object
     */
    public DataLengthFilter(Location location, XMLVariable template_variable, Variable real_variable) {
        this.location = location;
        this.template_variable = template_variable;
        this.real_variable = real_variable;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public XMLVariable getTemplate_variable() {
        return template_variable;
    }

    public void setTemplate_variable(XMLVariable template_variable) {
        this.template_variable = template_variable;
    }

    public Variable getReal_variable() {
        return real_variable;
    }

    public void setReal_variable(Variable real_variable) {
        this.real_variable = real_variable;
    }

    /*
        Filters the input variable data length by checking the
        supported data lengths defined in the variable definition. If
        the actual input data length is not supported, an
        InvalidDataLengthException is raised.
     */
    public void filter() throws InvalidDataLengthException {

        boolean length_validation = false;

        for (Integer valid_length : template_variable.getValid_lengths()) {
            if (real_variable.getActual_data_length() == valid_length) {
                length_validation = true;
            }
        }

        if (real_variable.getActual_data_length() < template_variable.getActual_data_length()) {
            template_variable.setActual_data_length(real_variable.getActual_data_length());
        }

        if (!length_validation) {
            throw new InvalidDataLengthException(location, real_variable.getName(), template_variable.getValid_lengths(),
                    real_variable.getActual_data_length());
        }
    }
}
