package jgaliweather.nlg;

/*
    Utility class to merge all meteorological textual
    forecasts, which controls the existence of certain
    cases where not all variables produce a corresponding
    textual description.
 */
public class DescriptionAggregator {

    private String sky;
    private String rain;
    private String temperature;
    private String wind;
    private String fog;

    /*
        Initializes a DescriptionAggregator object

        :param skystate_prediction: A textual cloud coverage forecast
        :param rain_prediction: A textual precipitation forecast
        :param temperature_prediction: A textual temperature forecast
        :param wind_prediction: A textual wind forecast
        :param fog_prediction: A textual fog forecast

        :return: A new DescriptionAggregator object
     */
    public DescriptionAggregator(String sky, String rain, String temperature, String wind, String fog) {
        this.sky = sky;

        this.rain = "";
        if (rain != null) {
            this.rain = rain;
        }

        this.temperature = temperature;

        this.wind = "";
        if (wind != null) {
            this.wind = wind.trim();
        }

        this.fog = "";
        if (fog != null) {
            this.fog = fog;
        }
    }

    public String getSky() {
        return sky;
    }

    public void setSky(String sky) {
        this.sky = sky;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getFog() {
        return fog;
    }

    public void setFog(String fog) {
        this.fog = fog;
    }

    /*
        Merges the individual variable descriptions and
        strips unnecessary characters

        :return: A final textual description from merging
        the individual descriptions
     */
    public String mergeDescription() {
        return (sky + " " + fog + " " + rain + " " + temperature + " " + wind).replace(" " + "" + " ", " ").trim();
    }
}
