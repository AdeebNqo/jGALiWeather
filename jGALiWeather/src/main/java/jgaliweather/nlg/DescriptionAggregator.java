package jgaliweather.nlg;

import java.util.Arrays;
import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGFactory;
import simplenlg.realiser.english.Realiser;

/*
    Utility class to merge all meteorological textual
    forecasts, which controls the existence of certain
    cases where not all variables produce a corresponding
    textual description.
 */
public class DescriptionAggregator {

    private DocumentElement sky;
    private DocumentElement rain;
    private DocumentElement temperature;
    private DocumentElement wind;
    private DocumentElement fog;
    private NLGFactory nlgFactory;
    private Realiser realiser;

    /*
        Initializes a DescriptionAggregator object

        :param skystate_prediction: A textual cloud coverage forecast
        :param rain_prediction: A textual precipitation forecast
        :param temperature_prediction: A textual temperature forecast
        :param wind_prediction: A textual wind forecast
        :param fog_prediction: A textual fog forecast

        :return: A new DescriptionAggregator object
     */
    public DescriptionAggregator(DocumentElement sky, DocumentElement rain, DocumentElement temperature, DocumentElement wind, DocumentElement fog, NLGFactory nlgFactory, Realiser realiser) {
        this.sky = sky;
        this.rain = rain;
        this.temperature = temperature;
        this.wind = wind;
        this.fog = fog;
        this.realiser = realiser;
        this.nlgFactory = nlgFactory;
    }

    public DocumentElement getSky() {
        return sky;
    }

    public void setSky(DocumentElement sky) {
        this.sky = sky;
    }

    public DocumentElement getRain() {
        return rain;
    }

    public void setRain(DocumentElement rain) {
        this.rain = rain;
    }

    public DocumentElement getTemperature() {
        return temperature;
    }

    public void setTemperature(DocumentElement temperature) {
        this.temperature = temperature;
    }

    public DocumentElement getWind() {
        return wind;
    }

    public void setWind(DocumentElement wind) {
        this.wind = wind;
    }

    public DocumentElement getFog() {
        return fog;
    }

    public void setFog(DocumentElement fog) {
        this.fog = fog;
    }

    /*
        Merges the individual variable descriptions and
        strips unnecessary characters

        :return: A final textual description from merging
        the individual descriptions
     */
    public String mergeDescription() {

        DocumentElement text = nlgFactory.createParagraph(Arrays.asList(sky, fog, rain, temperature, wind));

        return realiser.realiseSentence(text).replaceAll("\\s+", " ").trim();
    }
}
