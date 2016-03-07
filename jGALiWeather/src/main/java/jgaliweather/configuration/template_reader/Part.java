package jgaliweather.configuration.template_reader;

import java.util.ArrayList;

/* Defines a case part within a Case object. */
public class Part {

    private ArrayList<Object> components;

    public Part() {
        this.components = new ArrayList();
    }

    public ArrayList<Object> getComponents() {
        return components;
    }

    @Override
    public String toString() {
        String txt = "";
        for (int i = 0; i < components.size(); i++) {
            txt = txt.concat(components.get(i).toString());
        }
        return txt;
    }

}
