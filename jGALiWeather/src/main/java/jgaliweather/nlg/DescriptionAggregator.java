package jgaliweather.nlg;

import java.util.Arrays;
import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.realiser.english.Realiser;

/*
    Utility class to merge all meteorological textual
    forecasts, which controls the existence of certain
    cases where not all variables produce a corresponding
    textual description.
 */
public class DescriptionAggregator {

    private NLGElement sky;
    private NLGElement rain;
    private NLGElement temperature;
    private NLGElement wind;
    private NLGElement fog;
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
    public DescriptionAggregator(NLGElement sky, NLGElement rain, NLGElement temperature, NLGElement wind, NLGElement fog, NLGFactory nlgFactory, Realiser realiser) {
        this.sky = sky;
        this.rain = rain;
        this.temperature = temperature;
        this.wind = wind;
        this.fog = fog;
        this.realiser = realiser;
        this.nlgFactory = nlgFactory;
    }

    public NLGElement getSky() {
        return sky;
    }

    public void setSky(NLGElement sky) {
        this.sky = sky;
    }

    public NLGElement getRain() {
        return rain;
    }

    public void setRain(NLGElement rain) {
        this.rain = rain;
    }

    public NLGElement getTemperature() {
        return temperature;
    }

    public void setTemperature(NLGElement temperature) {
        this.temperature = temperature;
    }

    public NLGElement getWind() {
        return wind;
    }

    public void setWind(NLGElement wind) {
        this.wind = wind;
    }

    public NLGElement getFog() {
        return fog;
    }

    public void setFog(NLGElement fog) {
        this.fog = fog;
    }

    /*
        Merges the individual variable descriptions and
        strips unnecessary characters

        :return: A final textual description from merging
        the individual descriptions
     */
    public String mergeDescription() {

        DocumentElement text = nlgFactory.createParagraph();
        text.addComponent(sky);
        text.addComponent(fog);
        text.addComponent(rain);
        text.addComponent(temperature);
        text.addComponent(wind);

        return realiser.realiseSentence(text).replaceAll("\\s+", " ").trim();
    }
}
