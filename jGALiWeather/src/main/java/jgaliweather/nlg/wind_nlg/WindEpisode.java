package jgaliweather.nlg.wind_nlg;

import java.util.ArrayList;
import jgaliweather.configuration.template_reader.LabelSet;

/*
    Defines a wind episode, composed of a list of ordered labels, and an associated period
 */
public class WindEpisode {

    private WindPeriod duration;
    private String label;
    private ArrayList<WindChange> changes;

    public WindEpisode(WindPeriod duration, String label) {
        this.duration = duration;
        this.label = label;
        this.changes = new ArrayList();
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

    public ArrayList<WindChange> getChanges() {
        return changes;
    }

    public void setChanges(ArrayList<WindChange> changes) {
        this.changes = changes;
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
        if (changes.size() != 0) {
            return label_template.getLabels().get(label).getData() + " "
                    + duration.toText(expresion_template, day_template, time_template, "START")
                    + addTextChanges(expresion_template, label_template, day_template, time_template);
        } else {
            return label_template.getLabels().get(label).getData() + " "
                    + duration.toText(expresion_template, day_template, time_template, "");
        }
    }

    /*
        Adds wind changes to the episode textual description

        :param expression_template: A wind expression template
        :param label_template: A wind labelset
        :param day_template: A day expression template
        :param time_template: A time expression template

        :return: A natural language expression of the wind changes detected in this object
     */
    public String addTextChanges(LabelSet expresion_template, LabelSet label_template, LabelSet day_template, LabelSet time_template) {

        String text = "";

        if (changes.size() > 0) {
            text = text.concat(expresion_template.getLabels().get("separator").getData() + " "
                    + expresion_template.getLabels().get("one_change").getData() + " "
                    + changes.get(0).toText(expresion_template, label_template, day_template, time_template));

            if (changes.size() > 1) {
                for (int i = 0; i < changes.size() - 2; i++) {
                    text = text.concat(expresion_template.getLabels().get("separator").getData() + " "
                            + expresion_template.getLabels().get("multiple_change").getData() + " "
                            + changes.get(i + 1).toText(expresion_template, label_template, day_template, time_template));
                }

                text = text.concat(" " + expresion_template.getLabels().get("nexus").getData() + " "
                        + expresion_template.getLabels().get("one_change").getData() + " "
                        + changes.get(changes.size() - 1).toText(expresion_template, label_template, day_template, time_template));
            }
        }

        return text;

    }
}
