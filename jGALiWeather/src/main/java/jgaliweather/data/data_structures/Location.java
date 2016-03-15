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
    private String[][] climatic_data;
    private HashMap<String, Variable> variables;
    private HashMap<String, String> summaries;

    public Location(String name, int lid) {
        this.name = name;
        this.lid = lid;
        this.climatic_data = null;
        this.variables = new HashMap();
        this.summaries = new HashMap();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Variable> getVariables() {
        return variables;
    }

    public void setVariables(HashMap<String, Variable> variables) {
        this.variables = variables;
    }

    public HashMap<String, String> getSummaries() {
        return summaries;
    }

    public void setSummaries(HashMap<String, String> summaries) {
        this.summaries = summaries;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public int getLid() {
        return lid;
    }

    public String[][] getClimatic_data() {
        return climatic_data;
    }

    public void setClimatic_data(String[][] climatic_data) {
        this.climatic_data = climatic_data;
    }

    @Override
    public String toString() {
        return this.lid + ": " + this.name;
    }

}
