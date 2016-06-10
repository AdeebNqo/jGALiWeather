package jgaliweather.nlg_simpleNLG.nlg_generators;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;
import jgaliweather.configuration.template_reader.LabelSet;
import jgaliweather.configuration.template_reader.Time;
import jgaliweather.nlg_simpleNLG.wind_nlg.WindChange;
import jgaliweather.nlg_simpleNLG.wind_nlg.WindEpisode;
import jgaliweather.nlg_simpleNLG.wind_nlg.WindEpisodeGroup;
import jgaliweather.nlg_simpleNLG.wind_nlg.WindPeriod;
import org.javatuples.Pair;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.phrasespec.NPPhraseSpec;

/*
    Implements a natural language text generator
    for the wind variable.
 */
public class WindGenerator {

    private HashMap<String, LabelSet> template;
    private ArrayList<String> result_strings;
    private Calendar date;
    private ArrayList<WindEpisode> episodes;
    private NLGFactory nlgFactory;

    /*
        Initializes a WindGenerator object

        :param template: A wind natural language template
        :param date: The current date
        :param result_strings: A list of wind linguistic
        descriptions

        :return: A new WindGenerator object
     */
    public WindGenerator(HashMap<String, LabelSet> template, Calendar date, ArrayList<String> result_strings, NLGFactory nlgFactory) {
        this.template = template;
        this.result_strings = result_strings;
        this.date = date;
        this.episodes = new ArrayList();

        this.nlgFactory = nlgFactory;
    }

    /*
        Parses the intermediate linguistic descriptions
        and returns a single natural language text
        description

        :return: A natural language description of the
        wind variable forecast
     */
    public NLGElement parseAndGenerate() {
        for (String rs : result_strings) {
            StringTokenizer st = new StringTokenizer(rs);

            if (st.countTokens() == 2) {
                String time = st.nextToken();
                String label = st.nextToken();
                alternative0(time, label);
            } else {
                st = new StringTokenizer(rs, "- ");
                Pair<Integer, Integer> period = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
                ArrayList<String> labels = new ArrayList();
                while (st.hasMoreTokens()) {
                    StringTokenizer aux = new StringTokenizer(st.nextToken(), ".");
                    labels.add(aux.nextToken());
                }
                alternative1(period, labels);
            }
        }

        WindEpisodeGroup weg = new WindEpisodeGroup(episodes, nlgFactory);

        if (weg.validateGroup()) {
            CoordinatedPhraseElement aux = weg.generateReport(template);

            NPPhraseSpec output = nlgFactory.createNounPhrase();
            output.addModifier(aux);
            
            return output;
        } else {
            return null;
        }
    }

    private void alternative0(String time, String label) {
        Pair<Integer, Integer> dt = indexToDayTimeIndex(date, Integer.parseInt(time));
        WindPeriod wp = new WindPeriod(new Time("", dt.getValue1(), dt.getValue0()), new Time("", dt.getValue1(), dt.getValue0()), nlgFactory);
        WindEpisode nwe = new WindEpisode(wp, label, nlgFactory);
        episodes.add(nwe);
    }

    private void alternative1(Pair<Integer, Integer> period, ArrayList<String> labels) {
        Pair<Integer, Integer> dt1 = indexToDayTimeIndex(date, period.getValue0());
        Pair<Integer, Integer> dt2 = indexToDayTimeIndex(date, period.getValue1());
        WindPeriod wp = new WindPeriod(new Time("", dt1.getValue1(), dt1.getValue0()), new Time("", dt2.getValue1(), dt2.getValue0()), nlgFactory);
        WindEpisode nwe = new WindEpisode(wp, labels.get(0), nlgFactory);

        String change_label = labels.get(0);

        for (int i = 0; i < labels.size(); i++) {
            if (!labels.get(i).equals(change_label)) {
                change_label = labels.get(i);
                Pair<Integer, Integer> dt = indexToDayTimeIndex(date, period.getValue0() + i);
                WindPeriod aux = new WindPeriod(new Time("", dt.getValue1(), dt.getValue0()), new Time("", dt.getValue1(), dt.getValue0()), nlgFactory);
                WindChange w_change = new WindChange(aux, change_label, nlgFactory);
                nwe.getChanges().add(w_change);
            }
        }

        episodes.add(nwe);
    }

    /*
        Converts a data index into a day and
        time indices

        :param date: The date of reference
        :param index: The index to be converted

        :return: A tuple of day and time indices
     */
    private Pair<Integer, Integer> indexToDayTimeIndex(Calendar date, int index) {

        int new_index = index % 3;

        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK) - 2;
        if (dayOfWeek == -1) {
            dayOfWeek = 6;
        }

        int d = (dayOfWeek + index / 3) % 7;

        return new Pair(d, new_index);
    }
}
