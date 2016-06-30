package jgaliweather.nlg.precipitation_nlg;

import java.util.ArrayList;
import jgaliweather.configuration.template_reader.LabelSet;
import simplenlg.framework.NLGFactory;
import simplenlg.phrasespec.AdjPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;

/*
    Defines a precipitation nuance.
 */
public class PrecipitationNuance {

    private PrecipitationPeriod duration;
    private String label;
    private NLGFactory nlgFactory;

    /*
        Initializes a PrecipitationNuance object

        :param duration: The duration of the nuance
        :param label: The precipitation label associated
        to the nuance
        :param nlgFactory: Class that contains methods for creating syntactic phrases

        :return: A new PrecipitationNuance object
     */
    public PrecipitationNuance(PrecipitationPeriod duration, String label, NLGFactory nlgFactory) {
        this.duration = duration;
        this.label = label;
        this.nlgFactory = nlgFactory;
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
    public AdjPhraseSpec toText(LabelSet label_template, LabelSet day_template, LabelSet time_template, String mode) {

        AdjPhraseSpec phrase = nlgFactory.createAdjectivePhrase(label_template.getLabels().get(label).getData());

        ArrayList<PPPhraseSpec> interval = duration.toText(day_template, time_template, mode);
        for (PPPhraseSpec p : interval) {
            phrase.addPostModifier(p);
        }

        return phrase;
    }
}
