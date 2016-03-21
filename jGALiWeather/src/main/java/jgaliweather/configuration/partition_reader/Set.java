package jgaliweather.configuration.partition_reader;

public interface Set {
    
    public String getName();
    
    @Override
    public String toString();
    
    public double apply(double value);
}
