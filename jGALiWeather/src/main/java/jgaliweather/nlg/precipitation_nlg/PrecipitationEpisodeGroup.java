package jgaliweather.nlg.precipitation_nlg;

import java.util.ArrayList;
import java.util.HashMap;
import jgaliweather.configuration.template_reader.LabelSet;

/*
    Defines a group of precipitation episodes.
 */
public class PrecipitationEpisodeGroup {

    private ArrayList<PrecipitationEpisode> episodes;
    private ArrayList<PrecipitationNuance> nuances;

    /*
        Initializes a PrecipitationEpisodeGroup object

        :param episodes: A list of PrecipitationEpisode
        objects

        :return: A new PrecipitationEpisodeGroup object
     */
    public PrecipitationEpisodeGroup(ArrayList<PrecipitationEpisode> episodes) {
        this.episodes = episodes;
        this.nuances = new ArrayList();

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
    public String generateReport(HashMap<String, LabelSet> template_labels) {

        String text = template_labels.get("RNLGE").getLabels().get("start").getData() + " "
                + episodes.get(0).toText(template_labels.get("RNLGE"), template_labels.get("R"), template_labels.get("DW"), template_labels.get("PD"), "TD");

        if (episodes.size() > 1) {
            for (int i = 0; i < episodes.size() - 2; i++) {
                text = text.concat(" " + template_labels.get("RNLGE").getLabels().get("separator").getData() + " "
                        + episodes.get(i + 1).toText(template_labels.get("RNLGE"), template_labels.get("R"), template_labels.get("DW"), template_labels.get("PD"), "TD"));
            }

            text = text.concat(" " + template_labels.get("RNLGE").getLabels().get("nexus").getData() + " "
                    + episodes.get(episodes.size() - 1).toText(template_labels.get("RNLGE"), template_labels.get("R"), template_labels.get("DW"), template_labels.get("PD"), "TD"));
        }

        text = text.concat(addTextNuances(template_labels));

        return text + ".";
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
    private String addTextNuances(HashMap<String, LabelSet> template_labels) {

        String text = "";

        if (nuances.size() > 0) {
            text = text.concat(template_labels.get("RNLGE").getLabels().get("separator").getData() + " "
                    + template_labels.get("RNLGE").getLabels().get("nuance").getData());

            text = text.concat(" " + nuances.get(0).toText(template_labels.get("RNLGE"), template_labels.get("R"), template_labels.get("DW"), template_labels.get("PD"), "TD"));

            if (nuances.size() > 1) {
                for (int i = 0; i < nuances.size() - 2; i++) {
                    text = text.concat(" " + template_labels.get("RNLGE").getLabels().get("separator").getData() + " "
                            + nuances.get(i + 1).toText(template_labels.get("RNLGE"), template_labels.get("R"), template_labels.get("DW"), template_labels.get("PD"), "TD"));
                }

                text = text.concat(" " + template_labels.get("RNLGE").getLabels().get("nexus").getData() + " "
                        + nuances.get(episodes.size() - 1).toText(template_labels.get("RNLGE"), template_labels.get("R"), template_labels.get("DW"), template_labels.get("PD"), "TD"));
            }
        }

        return text;
    }
}
