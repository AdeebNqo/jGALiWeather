package configuration.template_reader.template_components;

import java.util.ArrayList;

/* Defines an optional text within a template */
public class Option {

    private int id;
    ArrayList<Object> components;
    private boolean used;

    public Option(int id) {
        this.id = id;
        this.components = new ArrayList();
        this.used = false;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Object> getComponents() {
        return components;
    }

    @Override
    public String toString() {
        String txt = "";

        if (!used) {
            return txt;
        }

        for (int i = 0; i < components.size(); i++) {
            txt = txt.concat(components.get(i).toString());
        }
        return txt;
    }
}
