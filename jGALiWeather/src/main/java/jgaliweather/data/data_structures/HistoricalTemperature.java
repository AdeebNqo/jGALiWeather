
package jgaliweather.data.data_structures;


public class HistoricalTemperature {

    private double averageMax;
    private double deviationMax;
    private double averageMin;
    private double deviationMin;

    public HistoricalTemperature(double averageMax, double deviationMax, double averageMin, double deviationMin) {
        this.averageMax = averageMax;
        this.deviationMax = deviationMax;
        this.averageMin = averageMin;
        this.deviationMin = deviationMin;
    }

    public double getAverageMax() {
        return averageMax;
    }

    public void setAverageMax(double averageMax) {
        this.averageMax = averageMax;
    }

    public double getDeviationMax() {
        return deviationMax;
    }

    public void setDeviationMax(double deviationMax) {
        this.deviationMax = deviationMax;
    }

    public double getAverageMin() {
        return averageMin;
    }

    public void setAverageMin(double averageMin) {
        this.averageMin = averageMin;
    }

    public double getDeviationMin() {
        return deviationMin;
    }

    public void setDeviationMin(double deviationMin) {
        this.deviationMin = deviationMin;
    }

    
}
