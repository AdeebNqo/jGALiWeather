package jgaliweather.configuration.partition_reader;

import fuzzy4j.sets.TrapezoidalFunction;

public class FuzzySet implements Set { //Faltan funciones por implementar

    private String name;
    private TrapezoidalFunction function;

    public FuzzySet() {
        this.name = null;
        this.function = null;
    }

    public FuzzySet(String name, TrapezoidalFunction function) {
        this.name = name;
        this.function = function;
    }
    
    @Override
    public int apply(double value) {//Pendiente de implementar
        
        return 1;
    }

    @Override
    public String toString() {
        return "FuzzySet " + name + ": " + function.toString();
    }
}
