package jgaliweather.nlg.precipitation_nlg;

import java.util.ArrayList;
import java.util.HashMap;
import jgaliweather.configuration.template_reader.LabelSet;
import simplenlg.features.Feature;
import simplenlg.features.NumberAgreement;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.NLGFactory;
import simplenlg.phrasespec.SPhraseSpec;

/*
    Defines a group of precipitation episodes.
 */
public class PrecipitationEpisodeGroup {

    private ArrayList<PrecipitationEpisode> episodes;
    private ArrayList<PrecipitationNuance> nuances;
    private NLGFactory nlgFactory;

    /*
        Initializes a PrecipitationEpisodeGroup object

        :param episodes: A list of PrecipitationEpisode
        objects
        :param nlgFactory: Class that contains methods for creating syntactic phrases

        :return: A new PrecipitationEpisodeGroup object
     */
    public PrecipitationEpisodeGroup(ArrayList<PrecipitationEpisode> episodes, NLGFactory nlgFactory) {
        this.episodes = episodes;
        this.nuances = new ArrayList();
        this.nlgFactory = nlgFactory;

        for (PrecipitationEpisode e : episodes) {
            for (PrecipitationNuance n : e.getNuances()) {
                nuances.add(n);
            }
        }
    }

    public ArrayList<PrecipitationEpisode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<PrecipitationEpisode> episodes) {
        this.episodes = episodes;
    }

    public ArrayList<PrecipitationNuance> getNuances() {
        return nuances;
    }

    public void setNuances(ArrayList<PrecipitationNuance> nuances) {
        this.nuances = nuances;
    }

    /*
        Checks that there is more than one precipitation
        episode

        :return: True if the size of the episodes list is
        greater than one. False otherwise.
     */
    public boolean validateGroup() {
        if (episodes.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /*
        Converts this object into a natural language expression

        :param template_labels: A set of template labels and
        expressions used in the natural language conversion

        :return: A natural language textual description of this
        object
     */
    public SPhraseSpec generateReport(HashMap<String, LabelSet> template_labels) {

        SPhraseSpec text = nlgFactory.createClause("precipitations", "be expected");
        text.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);

        CoordinatedPhraseElement episodes_list = nlgFactory.createCoordinatedPhrase();

        for (int i = 0; i < episodes.size(); i++) {
            episodes_list.addCoordinate(episodes.get(i).toText(template_labels.get("R"), template_labels.get("DW"), template_labels.get("PD"), "TD"));
        }

        text.addPostModifier(episodes_list);

        SPhraseSpec nuances = addTextNuances(template_labels);
        if (nuances != null) {
            text.addPostModifier(nuances);
        }

        return text;
    }

    /*
        Creates a natural language text description of precipitation
        nuances

        :param nuances: A list of PrecipitationNuance objects
        :param template_labels: A set of template labels and
        expressions used in the natural language conversion

        :return: A natural language expression of precipitation
        nuances
     */
    private SPhraseSpec addTextNuances(HashMap<String, LabelSet> template_labels) {
        if (nuances.size() > 0) {
            SPhraseSpec main_nuance = nlgFactory.createClause("which", "be");
            main_nuance.setFeature(Feature.MODAL, "can");
            main_nuance.setFeature(Feature.APPOSITIVE, true);

            CoordinatedPhraseElement nuances_list = nlgFactory.createCoordinatedPhrase();

            for (int i = 0; i < nuances.size(); i++) {
                nuances_list.addCoordinate(nuances.get(i).toText(template_labels.get("R"), template_labels.get("DW"), template_labels.get("PD"), "TD"));
            }

            main_nuance.addComplement(nuances_list);

            return main_nuance;
        } else {
            return null;
        }
    }
}
