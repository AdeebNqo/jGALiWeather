package configuration.template_reader;

import configuration.template_reader.template_components.Case;
import configuration.template_reader.template_components.Option;
import configuration.template_reader.template_components.Time;
import configuration.template_reader.template_components.TimeLabel;
import configuration.template_reader.template_components.Variable;
import java.util.ArrayList;
import java.util.HashMap;

/* Defines a generic language template. */
public class Template {

    private int id;
    private String name;
    private ArrayList<Case> cases;
    private HashMap<String, Variable> variables;
    private HashMap<Integer, Option> options;
    private HashMap<String, TimeLabel> time_labels;
    private Case selectedCase;

    public Template(int id, String name) {
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
