package jgaliweather.configuration.partition_reader;

import com.fuzzylite.term.Trapezoid;

public class FuzzySet implements Set {

    private String name;
    private Trapezoid function;

    public FuzzySet() {
        this.name = null;
        this.function = null;
    }

    public FuzzySet(String name, Trapezoid function) {
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

    public Trapezoid getFunction() {
        return function;
    }

    public void setFunction(Trapezoid function) {
        this.function = function;
    }

    @Override
    public double apply(double value) {
        return function.membership(value);
    }

    @Override
    public String toString() {
        return "FuzzySet " + name + ": " + function.toString();
    }
}
