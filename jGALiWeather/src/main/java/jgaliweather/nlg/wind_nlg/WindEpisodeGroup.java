package jgaliweather.nlg.wind_nlg;

import java.util.ArrayList;
import java.util.HashMap;
import jgaliweather.configuration.template_reader.LabelSet;

/*
    Defines a group of wind episodes.
 */
public class WindEpisodeGroup {

    private ArrayList<WindEpisode> episodes;

    /*
        Initializes a WindEpisodeGroup object

        :param episodes: A list of WindEpisode objects

        :return: A new WindEpisodeGroup object
     */
    public WindEpisodeGroup(ArrayList<WindEpisode> episodes) {
        this.episodes = episodes;
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

    public String generateReport(HashMap<String, LabelSet> template_labels) {

        String text = template_labels.get("WNLGE").getLabels().get("start").getData() + " "
                + episodes.get(0).toText(template_labels.get("WNLGE"), template_labels.get("W"), template_labels.get("DW"), template_labels.get("PD"));

        if (episodes.size() > 1) {
            for (int i = 0; i < episodes.size() - 2; i++) {
                text = text.concat(template_labels.get("WNLGE").getLabels().get("part_separator").getData() + " "
                        + episodes.get(i + 1).toText(template_labels.get("WNLGE"), template_labels.get("W"), template_labels.get("DW"), template_labels.get("PD")));
            }

            text = text.concat(template_labels.get("WNLGE").getLabels().get("part_separator").getData() + " "
                    + template_labels.get("WNLGE").getLabels().get("nexus").getData() + " "
                    + episodes.get(episodes.size() - 1).toText(template_labels.get("WNLGE"), template_labels.get("W"), template_labels.get("DW"), template_labels.get("PD")));
        }

        return text + ".";
    }
}
