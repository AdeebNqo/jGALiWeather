package jgaliweather.data.data_structures;

import java.util.HashMap;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.nlg.DescriptionAggregator;

/*
    Defines a generic location, which may have
    several input variables associated, as well
    as the final output textual forecasts.
 */
public class Location {

    private String name;
    private int lid;
    private HashMap<String, Variable> variables;
    private HashMap<String, DescriptionAggregator> summaries;
    private HistoricalTemperature climatic_data;
    private Partition max_climate_partition;
    private Partition min_climate_partition;

    public Location(String name, int lid) {
        this.name = name;
        this.lid = lid;
        this.variables = new HashMap();
        this.summaries = new HashMap();
        this.climatic_data = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Variable> getVariables() {
        return variables;
    }

    public void setVariables(HashMap<String, Variable> variables) {
        this.variables = variables;
    }

    public HashMap<String, DescriptionAggregator> getSummaries() {
        return summaries;
    }

    public void setSummaries(HashMap<String, DescriptionAggregator> summaries) {
        this.summaries = summaries;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public int getLid() {
        return lid;
    }

    public HistoricalTemperature getClimatic_data() {
        return climatic_data;
    }

    public void setClimatic_data(HistoricalTemperature climatic_data) {
        this.climatic_data = climatic_data;
    }

    public Partition getMax_climate_partition() {
        return max_climate_partition;
    }

    public void setMax_climate_partition(Partition max_climate_partition) {
        this.max_climate_partition = max_climate_partition;
    }

    public Partition getMin_climate_partition() {
        return min_climate_partition;
    }

    public void setMin_climate_partition(Partition min_climate_partition) {
        this.min_climate_partition = min_climate_partition;
    }

    @Override
    public String toString() {
        return this.lid + ": " + this.name;
    }

}
