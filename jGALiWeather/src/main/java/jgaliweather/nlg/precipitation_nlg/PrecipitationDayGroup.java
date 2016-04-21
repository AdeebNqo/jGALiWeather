package jgaliweather.nlg.precipitation_nlg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import jgaliweather.configuration.template_reader.LabelSet;
import org.javatuples.Pair;


/*
    Defines a group of precipitation episodes
    grouped by days
 */
public class PrecipitationDayGroup {

    private int length;
    private ArrayList<PrecipitationEpisode> episodes;
    private ArrayList<PrecipitationNuance> nuances;
    private int term_length;
    private int recurring_time;
    private LinkedHashMap<Integer, Integer> days;
    private LinkedHashMap<Integer, Integer> times;
    private int all_nuances;

    /*
        Initializes a PrecipitationDayGroup object

        :param episodes: A list of PrecipitationEpisode
        objects
        :param term_length: The length of the forecast
        term

        :return: A new PrecipitationDayGroup object
     */
    public PrecipitationDayGroup(ArrayList<PrecipitationEpisode> episodes, int term_length) {
        this.length = 1;
        this.episodes = episodes;
        this.nuances = new ArrayList();
        this.term_length = term_length;
        this.recurring_time = 0;
        this.days = new LinkedHashMap();
        this.times = new LinkedHashMap();
        this.all_nuances = 0;

        for (PrecipitationEpisode e : episodes) {
            if (!e.getLabel().equals("I") && !e.getLabel().equals("P")) {
                nuances.add(new PrecipitationNuance(e.getDuration(), e.getLabel()));
                all_nuances++;
            }

            for (PrecipitationNuance n : e.getNuances()) {
                nuances.add(n);
            }
        }
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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

    public int getTerm_length() {
        return term_length;
    }

    public void setTerm_length(int term_length) {
        this.term_length = term_length;
    }

    public int getRecurring_time() {
        return recurring_time;
    }

    public void setRecurring_time(int recurring_time) {
        this.recurring_time = recurring_time;
    }

    public LinkedHashMap<Integer, Integer> getDays() {
        return days;
    }

    public void setDays(LinkedHashMap<Integer, Integer> days) {
        this.days = days;
    }

    public LinkedHashMap<Integer, Integer> getTimes() {
        return times;
    }

    public void setTimes(LinkedHashMap<Integer, Integer> times) {
        this.times = times;
    }

    public int getAll_nuances() {
        return all_nuances;
    }

    public void setAll_nuances(int all_nuances) {
        this.all_nuances = all_nuances;
    }

    /*
        Checks if the precipitation episodes
        can be grouped by days

        :return: True if every episode is
        a single moment or if the number of
        nuance episodes is less than the number
        of total episodes. False otherwise.
     */
    public boolean validateGroup() {

        days.clear();
        times.clear();

        int singles = 0;

        for (PrecipitationEpisode e : episodes) {
            Pair<Integer, Integer> inst = e.getDuration().single();

            if (inst != null) {
                singles++;
                if (days.containsKey(inst.getValue0())) {
                    days.replace(inst.getValue0(), days.get(inst.getValue0()) + 1);
                } else {
                    days.put(inst.getValue0(), 1);
                }

                if (times.containsKey(inst.getValue1())) {
                    times.replace(inst.getValue1(), times.get(inst.getValue1()) + 1);
                } else {
                    times.put(inst.getValue1(), 1);
                }
            } else {
                for (int d : e.getDuration().days()) {
                    if (days.containsKey(d)) {
                        days.replace(d, days.get(d) + 1);
                    } else {
                        days.put(d, 1);
                    }
                }
            }
        }

        if (days.size() == term_length / 3) {
            for (int ts : times.keySet()) {
                if (times.get(ts) == days.size()) {
                    recurring_time = ts + 1;
                    break;
                }
            }

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

        String mode = "TD";

        String text = template_labels.get("RNLGE").getLabels().get("start").getData() + " "
                + template_labels.get("RNLGE").getLabels().get("everyday").getData();

        if (recurring_time > 0) {
            text = text.concat(" " + template_labels.get("RNLGE").getLabels().get(recurring_time - 1 + "").getData());
            mode = "D";
        }

        if (nuances.size() > 0) {
            text = text.concat(template_labels.get("RNLGE").getLabels().get("separator").getData() + " "
                    + template_labels.get("RNLGE").getLabels().get("nuance").getData());

            text = text.concat(" " + nuances.get(0).toText(template_labels.get("RNLGE"), template_labels.get("R"), template_labels.get("DW"), template_labels.get("PD"), mode));

            if (nuances.size() > 1) {
                for (int i = 0; i < nuances.size() - 2; i++) {
                    text = text.concat(" " + template_labels.get("RNLGE").getLabels().get("separator").getData() + " "
                            + nuances.get(i + 1).toText(template_labels.get("RNLGE"), template_labels.get("R"), template_labels.get("DW"), template_labels.get("PD"), mode));
                }

                text = text.concat(" " + template_labels.get("RNLGE").getLabels().get("nexus").getData() + " "
                        + nuances.get(episodes.size() - 1).toText(template_labels.get("RNLGE"), template_labels.get("R"), template_labels.get("DW"), template_labels.get("PD"), mode));
            }
        }

        return text + ".";
    }
}
