package jgaliweather.configuration.partition_reader;

import java.util.ArrayList;

public class ObjectSet implements Set {

    private String name;
    private ArrayList objects;

    public ObjectSet(String name, ArrayList objects) {
        this.name = name;
        this.objects = objects;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList getObjects() {
        return objects;
    }

    public void setObjects(ArrayList objects) {
        this.objects = objects;
    }

    @Override
    public int apply(double value) {
        if (objects.contains(new Integer((int) value))) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        String str = "ObjectSet " + name + ": ";

        str = str.concat("" + objects.get(0));
        for (int i = 1; i < objects.size(); i++) {
            str = str.concat(", " + objects.get(i));
        }

        return str;
    }
}
