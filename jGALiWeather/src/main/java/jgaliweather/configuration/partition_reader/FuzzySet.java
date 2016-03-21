package jgaliweather.configuration.partition_reader;

import fuzzy4j.sets.TrapezoidalFunction;

public class FuzzySet implements Set {

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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrapezoidalFunction getFunction() {
        return function;
    }

    public void setFunction(TrapezoidalFunction function) {
        this.function = function;
    }

    @Override
    public double apply(double value) {
        return function.apply(value);
    }

    @Override
    public String toString() {
        return "FuzzySet " + name + ": " + function.toString();
    }
}
