package jgaliweather.configuration.partition_reader;

public interface Set {
    
    @Override
    public String toString();
    
    public int apply(double value);
}
