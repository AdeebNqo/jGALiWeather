package jgaliweather.nlg_simpleNLG.wind_nlg;

import java.util.ArrayList;
import jgaliweather.configuration.template_reader.LabelSet;
import simplenlg.framework.NLGFactory;
import simplenlg.phrasespec.PPPhraseSpec;

/*
    Defines a change of wind direction or intensity within a wind episode
 */
public class WindChange {

    private WindPeriod duration;
    private String label;
    private NLGFactory nlgFactory;

    /*
        Initializes a WindChange object

        :param duration: A period associated to this change
        :param label: A wind numeric code associated to this
        change
        :param nlgFactory: Class that contains methods for creating syntactic phrases

        :return: A new WindChange object
     */
    public WindChange(WindPeriod duration, String label, NLGFactory nlgFactory) {
        this.duration = duration;
        this.label = label;
        this.nlgFactory = nlgFactory;
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

        :return: A list of prepositional phrases describing this object
     */
    public ArrayList<PPPhraseSpec> toText(LabelSet expresion_template, LabelSet label_template, LabelSet day_template, LabelSet time_template) {

        ArrayList<PPPhraseSpec> elements = new ArrayList();
        elements.add(nlgFactory.createPrepositionPhrase(expresion_template.getLabels().get("change").getData(), label_template.getLabels().get(label).getData()));
        elements.addAll(duration.toText(expresion_template, day_template, time_template, "N"));
        
        return elements;
    }
}
