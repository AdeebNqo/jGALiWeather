package jgaliweather.nlg.wind_nlg;

import java.util.ArrayList;
import java.util.HashMap;
import jgaliweather.configuration.template_reader.LabelSet;
import simplenlg.features.Feature;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.phrasespec.AdjPhraseSpec;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;


/*
    Defines a group of wind episodes.
 */
public class WindEpisodeGroup {

    private ArrayList<WindEpisode> episodes;
    private NLGFactory nlgFactory;

    /*
        Initializes a WindEpisodeGroup object

        :param episodes: A list of WindEpisode objects
        :param nlgFactory: Class that contains methods for creating syntactic phrases

        :return: A new WindEpisodeGroup object
     */
    public WindEpisodeGroup(ArrayList<WindEpisode> episodes, NLGFactory nlgFactory) {
        this.episodes = episodes;
        this.nlgFactory = nlgFactory;
    }

    public ArrayList<WindEpisode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<WindEpisode> episodes) {
        this.episodes = episodes;
    }

    public NLGFactory getNlgFactory() {
        return nlgFactory;
    }

    public void setNlgFactory(NLGFactory nlgFactory) {
        this.nlgFactory = nlgFactory;
    }

    /*
        Checks the existence of wind episodes

        :return: True if at least there is one wind episode present. False otherwise.
     */
    public boolean validateGroup() {
        if (episodes.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public CoordinatedPhraseElement generateReport(HashMap<String, LabelSet> template_labels) {

        CoordinatedPhraseElement text = nlgFactory.createCoordinatedPhrase();

        NPPhraseSpec windCondition = nlgFactory.createNounPhrase("wind");

        ArrayList<NLGElement> start = episodes.get(0).toText(template_labels.get("W"), template_labels.get("DW"), template_labels.get("PD"));
        windCondition.addModifier((AdjPhraseSpec) start.get(0));
        for (int i = 1; i < start.size(); i++) {
            windCondition.addComplement((PPPhraseSpec) start.get(i));
        }

        CoordinatedPhraseElement changes = episodes.get(0).addTextChanges(template_labels.get("W"), template_labels.get("DW"), template_labels.get("PD"));
        if (changes != null) {
            changes.setFeature(Feature.APPOSITIVE, true);
            windCondition.addPostModifier(changes);
        }
        text.addCoordinate(windCondition);

        if (episodes.size() > 1) {
            for (int i = 0; i < episodes.size() - 2; i++) {
                NPPhraseSpec windCondition_aux = nlgFactory.createNounPhrase();

                ArrayList<NLGElement> aux = episodes.get(i + 1).toText(template_labels.get("W"), template_labels.get("DW"), template_labels.get("PD"));
                windCondition_aux.addModifier((AdjPhraseSpec) aux.get(0));
                for (int j = 1; j < aux.size(); j++) {
                    windCondition_aux.addComplement((PPPhraseSpec) aux.get(j));
                }

                CoordinatedPhraseElement changes_aux = episodes.get(i + 1).addTextChanges(template_labels.get("W"), template_labels.get("DW"), template_labels.get("PD"));
                if (changes_aux != null) {
                    changes_aux.setFeature(Feature.APPOSITIVE, true);
                    windCondition_aux.addPostModifier(changes_aux);
                }
                text.addCoordinate(windCondition_aux);
            }

            //text.addCoordinate(""); // this puts a comma before the last 'and' (needs to delete duplicate white spaces with f.e. String.replaceAll("\\s+", " ");)

            NPPhraseSpec windCondition_aux = nlgFactory.createNounPhrase();

            ArrayList<NLGElement> aux = episodes.get(episodes.size() - 1).toText(template_labels.get("W"), template_labels.get("DW"), template_labels.get("PD"));
            windCondition_aux.addModifier((AdjPhraseSpec) aux.get(0));
            for (int i = 1; i < aux.size(); i++) {
                windCondition_aux.addComplement((PPPhraseSpec) aux.get(i));
            }

            CoordinatedPhraseElement changes_aux = episodes.get(episodes.size() - 1).addTextChanges(template_labels.get("W"), template_labels.get("DW"), template_labels.get("PD"));
            if (changes_aux != null) {
                changes_aux.setFeature(Feature.APPOSITIVE, true);
                windCondition_aux.addPostModifier(changes_aux);
            }
            text.addCoordinate(windCondition_aux);
        }
        return text;
    }
}
