
package jgaliweather;


public class Jgaliweather {

    public static void main(String[] args) {
        PredictionSummarizer ps = new PredictionSummarizer("Configuration/configuration.xml", "");
        ps.generateTextualForecasts();
    }
}
