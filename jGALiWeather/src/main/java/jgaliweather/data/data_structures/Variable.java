package jgaliweather.data.data_structures;

import java.util.ArrayList;

/*
    Defines an input variable, which has a
    list of data values associated.
 */
public class Variable {

    private String name;
    private ArrayList<Value> values;
    private int actual_data_length;

    public Variable(String name) {
        this.name = name;
        this.values = new ArrayList();
        this.actual_data_length = 0;
    }

    public int getActual_data_length() {
        return actual_data_length;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValues(ArrayList<Value> values) {
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Value> getValues() {
        return values;
    }

    public void setActual_data_length(int actual_data_length) {
        this.actual_data_length = actual_data_length;
    }

    @Override
    public String toString() {
        return "Variable " + name + ": " + values.size() + " values";
    }
}
