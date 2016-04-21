package jgaliweather.configuration.template_reader;

import jgaliweather.configuration.template_reader.template_components.Case;
import jgaliweather.configuration.template_reader.template_components.Option;
import jgaliweather.configuration.template_reader.template_components.TimeLabel;
import jgaliweather.configuration.template_reader.template_components.Variable;
import java.util.ArrayList;
import java.util.HashMap;

/* Defines a generic language template. */
public class Template {

    private String id;
    private String name;
    private ArrayList<Case> cases;
    private HashMap<String, Variable> variables;
    private HashMap<Integer, Option> options;
    private HashMap<String, TimeLabel> time_labels;
    private Case selectedCase;

    public Template(String id, String name) {
        this.id = id;
        this.name = name;
        this.cases = new ArrayList();
        this.variables = new HashMap();
        this.options = new HashMap();
        this.time_labels = new HashMap();
        this.selectedCase = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCases(ArrayList<Case> cases) {
        this.cases = cases;
    }

    public void setVariables(HashMap<String, Variable> variables) {
        this.variables = variables;
    }

    public void setOptions(HashMap<Integer, Option> options) {
        this.options = options;
    }

    public void setTime_labels(HashMap<String, TimeLabel> time_labels) {
        this.time_labels = time_labels;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Case getSelectedCase() {
        return selectedCase;
    }

    public void setSelectedCase(Case selectedCase) {
        this.selectedCase = selectedCase;
    }

    public ArrayList<Case> getCases() {
        return cases;
    }

    public HashMap<Integer, Option> getOptions() {
        return options;
    }

    public HashMap<String, Variable> getVariables() {
        return variables;
    }

    public HashMap<String, TimeLabel> getTime_labels() {
        return time_labels;
    }

    @Override
    public String toString() {
        return selectedCase.toString();
    }

}
