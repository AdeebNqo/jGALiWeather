package jgaliweather.data.data_structures;

import java.util.HashMap;

/*
    Defines a generic location, which may have
    several input variables associated, as well
    as the final output textual forecasts.
 */
public class Location {

    private String name;
    private int lid;
    private HashMap<String, Variable> variables;
    private HashMap<String, String> summaries;

    public Location(String name, int lid) {
        this.name = name;
        this.lid = lid;
        this.variables = new HashMap();
        this.summaries = new HashMap();
    }

    public int getLid() {
        return lid;
    }

    @Override
    public String toString() {
        return this.lid + ": " + this.name;
    }

}
