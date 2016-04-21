package jgaliweather.configuration.template_reader.template_components;

import jgaliweather.configuration.template_reader.Part;
import java.util.ArrayList;

/* Defines a meteorological case within a Template object. */
public class Case {

    private int id;
    private ArrayList<Part> parts;

    public Case(int id) {
        this.id = id;
        this.parts = new ArrayList();
    }

    public int getId() {
        return id;
    }

    public void setParts(ArrayList<Part> parts) {
        this.parts = parts;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Part> getParts() {
        return parts;
    }

    @Override
    public String toString() {
        String txt = "";
        for (int i = 0; i < parts.size(); i++) {
            txt = txt.concat(parts.get(i).toString());
        }
        return txt;
    }
}
