package jgaliweather.data.data_structures;

/*
    Defines a single value within a variable
    input data series.
 */
public class Value {

    private int temp_location;
    private double data;

    public Value(int temp_location, double data) {
        this.temp_location = temp_location;
        this.data = data;
    }

    public int getTemp_location() {
        return temp_location;
    }

    public double getData() {
        return data;
    }

    public void setTemp_location(int temp_location) {
        this.temp_location = temp_location;
    }

    public void setData(double data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Value " + temp_location + ": " + data;
    }

    public boolean equals(Value other) {
        return this.data == other.data;
    }

}
