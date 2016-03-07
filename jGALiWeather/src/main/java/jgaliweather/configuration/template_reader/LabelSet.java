package jgaliweather.configuration.template_reader;

import java.util.HashMap;

/* Defines a set of text labels */
public class LabelSet {

    private String name;
    private HashMap<String, Label> labels;

    public LabelSet(String name) {
        this.name = name;
        this.labels = new HashMap();
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Label> getLabels() {
        return labels;
    }

}
