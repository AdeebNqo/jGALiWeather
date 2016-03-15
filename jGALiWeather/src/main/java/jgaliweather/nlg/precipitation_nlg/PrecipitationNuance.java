package jgaliweather.nlg.precipitation_nlg;

import jgaliweather.configuration.template_reader.LabelSet;
import jgaliweather.configuration.template_reader.Template;

/*
    Defines a precipitation nuance.
*/
public class PrecipitationNuance {
    
    private PrecipitationPeriod duration;
    private String label;

    /*
        Initializes a PrecipitationNuance object

        :param duration: The duration of the nuance
        :param label: The precipitation label associated
        to the nuance

        :return: A new PrecipitationNuance object
    */
    public PrecipitationNuance(PrecipitationPeriod duration, String label) {
        this.duration = duration;
        this.label = label;
    }

    public PrecipitationPeriod getDuration() {
        return duration;
    }

    public void setDuration(PrecipitationPeriod duration) {
        this.duration = duration;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    /*
        Converts this object into a natural language expression

        :param expression_template: A wind expression template
        :param label_template: A precipitation labelset
        :param day_template: A day expression template
        :param time_template: A time expression template
        :param mode: Period text conversion mode

        :return: A natural language expression of this object
    */
    public String toText(LabelSet expresion_template, LabelSet label_template, LabelSet day_template, LabelSet time_template, String mode) {
        
        return label_template.getLabels().get(label).getData() + " " + duration.toText(expresion_template, day_template, time_template, mode);
    }
}
