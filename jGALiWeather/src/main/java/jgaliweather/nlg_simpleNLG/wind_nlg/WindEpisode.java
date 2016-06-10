package jgaliweather.nlg_simpleNLG.wind_nlg;

import java.util.ArrayList;
import jgaliweather.configuration.template_reader.LabelSet;
import simplenlg.features.Feature;
import simplenlg.features.Form;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.framework.PhraseElement;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;

/*
    Defines a wind episode, composed of a list of ordered labels, and an associated period
 */
public class WindEpisode {

    private WindPeriod duration;
    private String label;
    private ArrayList<WindChange> changes;
    private NLGFactory nlgFactory;

    /*
        Initializes a WindEpisode object
    
        :param duration: A period associated to this episode
        :param label: A wind numeric code associated to this
        episode
        :param nlgFactory: Class that contains methods for creating syntactic phrases

        :return: A new WindEpisode object
     */
    public WindEpisode(WindPeriod duration, String label, NLGFactory nlgFactory) {
        this.duration = duration;
        this.label = label;
        this.changes = new ArrayList();
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

        :return: A list containing phrases describing this object
     */
    public ArrayList<NLGElement> toText(LabelSet expresion_template, LabelSet label_template, LabelSet day_template, LabelSet time_template) {

        ArrayList<NLGElement> phrases = new ArrayList();

        if (!changes.isEmpty()) {
            phrases.add(nlgFactory.createAdjectivePhrase(label_template.getLabels().get(label).getData()));
            phrases.addAll(duration.toText(expresion_template, day_template, time_template, "START"));

        } else {
            phrases.add(nlgFactory.createAdjectivePhrase(label_template.getLabels().get(label).getData()));
            phrases.addAll(duration.toText(expresion_template, day_template, time_template, "N"));
        }
        
        return phrases;
    }

    /*
        Adds wind changes to the episode textual description

        :param expression_template: A wind expression template
        :param label_template: A wind labelset
        :param day_template: A day expression template
        :param time_template: A time expression template

        :return: A list of phrase elements describing wind changes detected in this object
     */
    public CoordinatedPhraseElement addTextChanges(LabelSet expresion_template, LabelSet label_template, LabelSet day_template, LabelSet time_template) {
        
        CoordinatedPhraseElement phrase = null;

        if (changes.size() > 0) {

            phrase = nlgFactory.createCoordinatedPhrase();
            
            VPPhraseSpec first = nlgFactory.createVerbPhrase(expresion_template.getLabels().get("one_change").getData());
            first.setFeature(Feature.FORM, Form.GERUND);
            ArrayList<PPPhraseSpec> change_text_list = changes.get(0).toText(expresion_template, label_template, day_template, time_template);
            for (PPPhraseSpec prep : change_text_list) {
                first.addComplement(prep);
            }
            phrase.addCoordinate(first);
            
            if (changes.size() > 1) {
                for (int i = 0; i < changes.size() - 2; i++) {
                    ArrayList<PPPhraseSpec> aux_list = changes.get(i + 1).toText(expresion_template, label_template, day_template, time_template);
                    SPhraseSpec aux_phrase = nlgFactory.createClause();
                    for (PPPhraseSpec prep : aux_list) {
                        aux_phrase.addComplement(prep);
                    }
                    phrase.addCoordinate(aux_phrase);
                }

                ArrayList<PPPhraseSpec> aux_list = changes.get(changes.size() - 1).toText(expresion_template, label_template, day_template, time_template);
                SPhraseSpec aux_phrase = nlgFactory.createClause();
                for (PPPhraseSpec prep : aux_list) {
                    aux_phrase.addComplement(prep);
                }
                phrase.addCoordinate(aux_phrase);
            }
        } 
        
        return phrase;

    }
}
