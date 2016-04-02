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
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;


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
    public PrecipitationDayGroup(ArrayList<PrecipitationEpisode> episodes, int term_length, NLGFactory nlgFactory) {
        this.length = 1;
        this.episodes = episodes;
        this.nuances = new ArrayList();
        this.term_length = term_length;
        this.recurring_time = 0;
        this.days = new LinkedHashMap();
        this.times = new LinkedHashMap();
        this.all_nuances = 0;
        this.nlgFactory = nlgFactory;

        for (PrecipitationEpisode e : episodes) {
            if (!e.getLabel().equals("I") && !e.getLabel().equals("P")) {
                nuances.add(new PrecipitationNuance(e.getDuration(), e.getLabel(), this.nlgFactory));
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

        for (int ts : times.keySet()) {
            if (times.get(ts) == days.size()) {
                recurring_time = (int) ts + 1;
                return true;
            }
        }

        if (singles == 0 && all_nuances < episodes.size()) {
            return true;
        }

        return false;

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
        ArrayList<String> ord_days = new ArrayList();

        for (Integer d : days.keySet()) {
            ord_days.add(d.toString());
        }

        SPhraseSpec text = nlgFactory.createClause(template_labels.get("RNLGE").getLabels().get("subject").getData(),
                template_labels.get("RNLGE").getLabels().get("verb").getData());
        text.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);

        if (days.size() == 1) {
            text.addPostModifier(nlgFactory.createPrepositionPhrase(template_labels.get("RNLGE").getLabels().get("single_period").getData(),
                    template_labels.get("DW").getLabels().get(ord_days.get(0)).getData()));

        } else if (days.size() == 2) {
            CoordinatedPhraseElement days_list = nlgFactory.createCoordinatedPhrase();
            
            PPPhraseSpec day1 = nlgFactory.createPrepositionPhrase(template_labels.get("RNLGE").getLabels().get("single_period").getData(),
                    template_labels.get("DW").getLabels().get(ord_days.get(0)).getData());
            days_list.addCoordinate(day1);
            
            PPPhraseSpec day2 = nlgFactory.createPrepositionPhrase(template_labels.get("RNLGE").getLabels().get("single_period").getData(),
                    template_labels.get("DW").getLabels().get(ord_days.get(1)).getData());    
            days_list.addCoordinate(day2);
            
            text.addPostModifier(days_list);

        } else {
            CoordinatedPhraseElement days_list = nlgFactory.createCoordinatedPhrase(); 

            for (int i = 0; i < ord_days.size(); i++) {
                days_list.addCoordinate(nlgFactory.createPrepositionPhrase(template_labels.get("RNLGE").getLabels().get("single_period").getData(),
                    template_labels.get("DW").getLabels().get(ord_days.get(i)).getData()));
            }
            
            text.addPostModifier(days_list);
        }
        if (recurring_time > 0 && days.size() > 0) {
            NPPhraseSpec day = nlgFactory.createNounPhrase(template_labels.get("PD").getLabels().get(recurring_time - 1 + "").getData());
            text.addPostModifier(day);
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
