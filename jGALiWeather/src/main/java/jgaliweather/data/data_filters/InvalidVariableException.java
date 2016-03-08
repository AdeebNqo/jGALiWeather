package jgaliweather.data.data_filters;

import jgaliweather.data.data_structures.Location;
import jgaliweather.data.data_structures.Value;

/*
    Defines an exception raised when invalid data
    is detected within the application input data
    series.
 */
public class InvalidVariableException extends Exception {

    private Location location;
    private int index;
    private Value value;

    /*
        Initializes a InvalidVariableException object

        :param location: The location identifier associated to
        the data which caused the exception
        :param value: The value which caused the exception
        :param index: The actual index of the value which
        caused the exception

        :return: A new InvalidVariableException object
     */
    public InvalidVariableException(Location location, Value value, int index) {
        super("Incorrect variable data in " + location.getLid() + " " + index
                + " " + value.getTemp_location() + "," + value.getData());
        this.location = location;
        this.index = index;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Incorrect variable data in " + location.getLid() + " " + index
                + " " + value.getTemp_location() + "," + value.getData();
    }
}
