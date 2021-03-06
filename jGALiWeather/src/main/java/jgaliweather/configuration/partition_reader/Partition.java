package jgaliweather.configuration.partition_reader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Partition implements Serializable {

    private String name;
    private ArrayList<Set> sets;

    public Partition(String name) {
        this.name = name;
        this.sets = new ArrayList();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSets(ArrayList<Set> sets) {
        this.sets = sets;
    }

    public ArrayList<Set> getSets() {
        return sets;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Double> evalValue(double value) {
        ArrayList<Double> res = new ArrayList();

        for (Set fp : sets) {
            res.add(fp.apply(value));
        }

        return res;
    }

    public Set bestEvaluatedLabel(double value) {
        ArrayList<Double> res = new ArrayList(Collections.nCopies(sets.size(), 0));

        for (int i = 0; i < sets.size(); i++) {
            res.set(i, sets.get(i).apply(value));
        }

        Double best_value = Collections.max(res);
        int best_label = res.indexOf(best_value);
        return sets.get(best_label);
    }

    public int bestEvaluatedIndex(double value) {
        ArrayList<Double> res = new ArrayList(Collections.nCopies(sets.size(), 0));
        int best_label = 0;
        double max = -9999;

        for (int i = 0; i < sets.size(); i++) {
            res.set(i, sets.get(i).apply(value));
        }

        for (int i = 0; i < res.size(); i++) {
            if (res.get(i) > max) {
                best_label = i;
                max = res.get(i);
            }
        }

        return best_label;
    }

    @Override
    public String toString() {
        String tostr = "Partition " + name + ":\n";

        for (int i = 0; i < sets.size(); i++) {
            tostr = tostr + " " + sets.get(i).toString() + "\n";
        }

        return tostr;
    }
}
