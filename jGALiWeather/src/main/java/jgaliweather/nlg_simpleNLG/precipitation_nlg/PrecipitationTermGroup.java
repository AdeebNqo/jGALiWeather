package jgaliweather.nlg_simpleNLG.precipitation_nlg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import jgaliweather.configuration.template_reader.LabelSet;
import org.javatuples.Pair;
import simplenlg.features.Feature;
import simplenlg.features.NumberAgreement;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.NLGFactory;
import simplenlg.phrasespec.SPhraseSpec;

/*
    Defines a group of precipitation episodes
    which aggregates the whole forecast term.
 */
public class PrecipitationTermGroup {

    private int length;
    private ArrayList<PrecipitationEpisode> episodes;
    private ArrayList<PrecipitationNuance> nuances;
    private int term_length;
    private int recurring_time;
    private LinkedHashMap<Integer, Integer> days;
    private LinkedHashMap<Integer, Integer> times;
    private NLGFactory nlgFactory;

    /*
        Initializes a PrecipitationDayGroup object

        :param episodes: A list of PrecipitationEpisode
        objects
        :param term_length: The length of the forecast
        term
        :param nlgFactory: Class that contains methods for creating syntactic phrases

        :return: A new PrecipitationDayGroup object
     */
    public PrecipitationTermGroup(ArrayList<PrecipitationEpisode> episodes, int term_length, NLGFactory nlgFactory) {
        this.length = 1;
        this.episodes = episodes;
        this.nuances = new ArrayList();
        this.term_length = term_length;
        this.recurring_time = 0;
        this.days = new LinkedHashMap();
        this.times = new LinkedHashMap();
        this.nlgFactory = nlgFactory;

        for (PrecipitationEpisode e : episodes) {
            if (!e.getLabel().equals("I") && !e.getLabel().equals("P")) {
                nuances.add(new PrecipitationNuance(e.getDuration(), e.getLabel(), this.nlgFactory));
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

    /*
        Checks if the precipitation episodes
        can be grouped by the whole term

        :return: True if there are precipitations
        in each day of the term. False otherwise.
     */
    public boolean validateGroup() {

        days.clear();
        times.clear();

        for (PrecipitationEpisode e : episodes) {
            Pair<Integer, Integer> inst = e.getDuration().single();

            if (inst != null) {
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
    public SPhraseSpec generateReport(HashMap<String, LabelSet> template_labels) {

        String mode = "TD";

        SPhraseSpec text = nlgFactory.createClause(template_labels.get("RNLGE").getLabels().get("subject").getData(),
                template_labels.get("RNLGE").getLabels().get("verb").getData());
        text.addComplement(nlgFactory.createAdverbPhrase(template_labels.get("RNLGE").getLabels().get("frequency").getData()));
        text.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);

        if (recurring_time > 0) {
            text.addComplement(template_labels.get("PD").getLabels().get(recurring_time - 1 + "").getData());
            mode = "D";
        }

        if (nuances.size() > 0) {
            SPhraseSpec main_nuance = nlgFactory.createClause(template_labels.get("RNLGE").getLabels().get("nuance_subject").getData(),
                    template_labels.get("RNLGE").getLabels().get("nuance_verb").getData());
            main_nuance.setFeature(Feature.MODAL, template_labels.get("RNLGE").getLabels().get("nuance_modal").getData());
            main_nuance.setFeature(Feature.APPOSITIVE, true);

            CoordinatedPhraseElement nuances_list = nlgFactory.createCoordinatedPhrase();

            for (int i = 0; i < nuances.size(); i++) {
                nuances_list.addCoordinate(nuances.get(i).toText(template_labels.get("RNLGE"), template_labels.get("R"), template_labels.get("DW"), template_labels.get("PD"), mode));
            }

            main_nuance.addComplement(nuances_list);

            text.addPostModifier(main_nuance);
        }

        return text;
    }
}
