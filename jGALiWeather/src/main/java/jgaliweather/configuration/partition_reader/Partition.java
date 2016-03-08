package jgaliweather.configuration.partition_reader;

import java.util.ArrayList;

public class Partition {

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

    public ArrayList<Integer> evalValue(double value) {
        ArrayList<Integer> res = new ArrayList();

        for (Set fp : sets) {
            res.add(fp.apply(value));
        }

        return res;
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
