package jgaliweather.data.data_filters;

import java.util.ArrayList;
import jgaliweather.data.data_structures.Location;

/*
    Defines an exception raised when the input
    data length is not supported by the application.
 */
public class InvalidDataLengthException extends Exception {

    private Location location;
    private String variable_name;
    private ArrayList<Integer> expected_length;
    private int actual_length;

    /*
        Initializes an InvalidDataLengthException object

        :param location: The location identifier associated to
        the data which caused the exception
        :param variable_name: Name of the variable which
        caused the exception
        :param expected_length: List of the supported lengths
        for *variable_name*
        :param actual_length: Actual input data length

        :return: A new InvalidDataLength object
     */
    public InvalidDataLengthException(Location location, String variable_name, ArrayList<Integer> expected_length, int actual_length) {
        super("Incorrect variable length in " + location.getLid() + " for " + variable_name
                + ". Expected: " + expected_length + ". Actual: " + actual_length);
        this.location = location;
        this.variable_name = variable_name;
        this.expected_length = expected_length;
        this.actual_length = actual_length;
    }

    @Override
    public String toString() {
        return "Incorrect variable length in " + location.getLid() + " for " + variable_name
                + ". Expected: " + expected_length.toString() + ". Actual: " + actual_length;
    }
}
