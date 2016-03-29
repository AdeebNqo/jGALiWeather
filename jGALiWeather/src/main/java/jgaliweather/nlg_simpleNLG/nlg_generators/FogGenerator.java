package jgaliweather.nlg_simpleNLG.nlg_generators;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import jgaliweather.configuration.template_reader.LabelSet;
import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.AdvPhraseSpec;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.english.Realiser;

public class FogGenerator {

    private HashMap<Integer, ArrayList<ArrayList<Integer>>> fog_data;
    private LabelSet fog_expr;
    private LabelSet parts_day;
    private LabelSet days_week;
    private Calendar curr_date;
    private int term_length;
    private NLGFactory nlgFactory;
    private Realiser realiser;

    /*
        Initializes a FogGenerator object

        :param fog_data: Fog relevant data
        :param fog_expr: A fog natural language expression set
        :param parts_day: A labelset for parts of the day
        :param days_week: A labelset for days of the week
        :param curdate: The forecast date
        :param term_length: The actual forecast term length

        :return: A new FogGenerator object
     */
    public FogGenerator(HashMap<Integer, ArrayList<ArrayList<Integer>>> fog_data, LabelSet fog_expr, LabelSet parts_day, LabelSet days_week, Calendar curr_date, int term_length) {
        this.fog_data = fog_data;
        this.fog_expr = fog_expr;
        this.parts_day = parts_day;
        this.days_week = days_week;
        this.curr_date = curr_date;
        this.term_length = term_length;

        Lexicon lexicon = Lexicon.getDefaultLexicon();
        this.nlgFactory = new NLGFactory(lexicon);
        this.realiser = new Realiser(lexicon);
    }

    /*
        Returns a single natural language text
        description

        :return: A natural language description of the
        fog variable forecast
     */
    public String generate() {

        LinkedHashMap<Integer, NPPhraseSpec> parts = new LinkedHashMap();
        Map<Integer, ArrayList<ArrayList<Integer>>> fog_data_sorted = new TreeMap(fog_data);

        if (fog_data.size() > 0) {
            for (int k : fog_data_sorted.keySet()) {
                if (parts.containsKey(k)) {
                    parts.replace(k, generatePart(k, fog_data.get(k)));
                } else {
                    parts.put(k, generatePart(k, fog_data.get(k)));
                }
            }

            SPhraseSpec text = nlgFactory.createClause(fog_expr.getLabels().get("subject").getData(),
                    fog_expr.getLabels().get("verb").getData());
            text.setFeature(Feature.TENSE, Tense.FUTURE);

            CoordinatedPhraseElement part_list = nlgFactory.createCoordinatedPhrase();

            for (int i = 0; i < parts.size() - 2; i++) {
                part_list.addCoordinate(parts.get(i));
            }

            part_list.addCoordinate(""); // this puts a comma before the last 'and' (needs to delete duplicate white spaces with f.e. String.replaceAll("\\s+", " ");)
            part_list.addCoordinate(parts.get(parts.size() - 1));
            
            text.addPostModifier(part_list);

            return realiser.realiseSentence(text).replaceAll("\\s+", " ");
        } else {
            return null;
        }
    }

    /*
        Generates a fog expression for a given
        part of a day

        :param part: A part of the day index
        :param days: Fog information associated to
        the part of the day parameter

        :return: A natural language expression
        for the part given by the part index
     */
    private NPPhraseSpec generatePart(int part, ArrayList<ArrayList<Integer>> days) {

        NPPhraseSpec text = nlgFactory.createNounPhrase(fog_expr.getLabels().get("fog").getData());
        text.addModifier(nlgFactory.createAdjectivePhrase(fog_expr.getLabels().get(part + "").getData()));
        
        

        int dayOfWeek = curr_date.get(Calendar.DAY_OF_WEEK) - 2;
        if (dayOfWeek == 0) {
            dayOfWeek = 6;
        } else if (dayOfWeek == -1) {
            dayOfWeek = 5;
        }

        if (days.size() == term_length / 3) {
            boolean persistent = true;
            int semi_persistent = 0;

            for (ArrayList<Integer> d : days) {
                persistent = persistent && d.size() > 1;
                semi_persistent += d.size();
            }

            if (persistent) {
                AdvPhraseSpec day = nlgFactory.createAdverbPhrase(fog_expr.getLabels().get("persistent").getData());
                day.addPostModifier(fog_expr.getLabels().get("everyday").getData());
                day.setFeature(Feature.APPOSITIVE, true);
                text.addPostModifier(day);
            } else if (semi_persistent > term_length / 3) {
                ArrayList<Integer> p_days = new ArrayList();
                for (ArrayList<Integer> d : days) {
                    if (d.size() > 1) {
                        p_days.add(d.get(0));
                    }
                }

                CoordinatedPhraseElement day_list = nlgFactory.createCoordinatedPhrase();
                
                day_list.addCoordinate(fog_expr.getLabels().get("everyday").getData());

                for (int i = 0; i < p_days.size(); i++) {
                    day_list.addCoordinate(nlgFactory.createPrepositionPhrase(fog_expr.getLabels().get("single_period").getData(),
                            days_week.getLabels().get(((p_days.get(i) + dayOfWeek) % 7) + "").getData()));
                }

                text.addPostModifier(day_list);
                
            } else {
                text.addPostModifier(nlgFactory.createAdverbPhrase(fog_expr.getLabels().get("eveyday").getData()));
            }
        } else {
            
            CoordinatedPhraseElement day_list = nlgFactory.createCoordinatedPhrase();

            for (int i = 0; i < days.size(); i++) {
                PPPhraseSpec day = nlgFactory.createPrepositionPhrase(fog_expr.getLabels().get("single_period").getData(),
                        days_week.getLabels().get(((days.get(i).get(0) + dayOfWeek) % 7) + "").getData());

                if (days.get(i).size() > 1) {
                    AdvPhraseSpec persistent = nlgFactory.createAdverbPhrase(fog_expr.getLabels().get("persistent").getData());
                    //persistent.setFeature(Feature.APPOSITIVE, true); //Para introducir una coma antes del "and": for (int i = 0; i < days.size() - 2; i++) y sacar la ultima iteracion del bucle y añadir day_list.addCoordinate("");
                    day.addPostModifier(persistent);
                }

                day_list.addCoordinate(day);
            }

            text.addPostModifier(day_list);
        }

        return text;
    }
}
