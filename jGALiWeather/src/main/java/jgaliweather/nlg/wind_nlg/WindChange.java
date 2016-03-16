package jgaliweather.nlg.wind_nlg;

import jgaliweather.configuration.template_reader.LabelSet;

/*
    Defines a change of wind direction or intensity within a wind episode
 */
public class WindChange {

    private WindPeriod duration;
    private String label;

    /*
        Initializes a WindChange object

        :param duration: A period associated to this change
        :param label: A wind numeric code associated to this
        change

        :return: A new WindChange object
     */
    public WindChange(WindPeriod duration, String label) {
        this.duration = duration;
        this.label = label;
    }

    public WindPeriod getDuration() {
        return duration;
    }

    public void setDuration(WindPeriod duration) {
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
        :param label_template: A wind labelset
        :param day_template: A day expression template
        :param time_template: A time expression template

        :return: A natural language expression of this object
     */
    public String toText(LabelSet expresion_template, LabelSet label_template, LabelSet day_template, LabelSet time_template) {

        return label_template.getLabels().get(label).getData() + " " + duration.toText(expresion_template, day_template, time_template, "");
    }
}
