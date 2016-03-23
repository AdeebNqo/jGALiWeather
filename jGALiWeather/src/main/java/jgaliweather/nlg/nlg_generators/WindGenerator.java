package jgaliweather.nlg.nlg_generators;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;
import jgaliweather.configuration.template_reader.LabelSet;
import jgaliweather.configuration.template_reader.template_components.Time;
import jgaliweather.nlg.wind_nlg.WindChange;
import jgaliweather.nlg.wind_nlg.WindEpisode;
import jgaliweather.nlg.wind_nlg.WindEpisodeGroup;
import jgaliweather.nlg.wind_nlg.WindPeriod;
import org.javatuples.Pair;

/*
    Implements a natural language text generator
    for the wind variable.
 */
public class WindGenerator {

    private HashMap<String, LabelSet> template;
    private ArrayList<String> result_strings;
    private Calendar date;
    private ArrayList<WindEpisode> episodes;

    /*
        Initializes a WindGenerator object

        :param template: A wind natural language template
        :param date: The current date
        :param result_strings: A list of wind linguistic
        descriptions

        :return: A new WindGenerator object
     */
    public WindGenerator(HashMap<String, LabelSet> template, ArrayList<String> result_strings, Calendar date) {
        this.template = template;
        this.result_strings = result_strings;
        this.date = date;
        this.episodes = new ArrayList();
    }

    /*
        Parses the intermediate linguistic descriptions
        and returns a single natural language text
        description

        :return: A natural language description of the
        wind variable forecast
     */
    public String parseAndGenerate() {
        for (String rs : result_strings) {
            StringTokenizer st = new StringTokenizer(rs);

            if (st.countTokens() == 1) {
                String time = st.nextToken();
                String label = st.nextToken();
                alternative0(time, label);
            } else {
                st = new StringTokenizer(rs, "- ");
                Pair<Integer, Integer> period = new Pair(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
                ArrayList<String> labels = new ArrayList();
                while (st.hasMoreTokens()) {
                    labels.add(st.nextToken());
                }
                alternative1(period, labels);
            }
        }

        WindEpisodeGroup weg = new WindEpisodeGroup(episodes);

        if (weg.validateGroup()) {
            return weg.generateReport(template);
        } else {
            return null;
        }
    }

    private void alternative0(String time, String label) {
        Pair<Calendar, Integer> dt = indexToDayTimeIndex(date, Integer.parseInt(time));
        WindPeriod wp = new WindPeriod(new Time("", dt.getValue0(), dt.getValue1()), new Time("", dt.getValue0(), dt.getValue1()));
        WindEpisode nwe = new WindEpisode(wp, label);
        episodes.add(nwe);
    }

    private void alternative1(Pair<Integer, Integer> period, ArrayList<String> labels) {
        Pair<Calendar, Integer> dt1 = indexToDayTimeIndex(date, period.getValue0());
        Pair<Calendar, Integer> dt2 = indexToDayTimeIndex(date, period.getValue1());
        WindPeriod wp = new WindPeriod(new Time("", dt1.getValue0(), dt1.getValue1()), new Time("", dt2.getValue0(), dt2.getValue1()));
        WindEpisode nwe = new WindEpisode(wp, labels.get(0));

        String change_label = labels.get(0);

        for (int i = 0; i < labels.size(); i++) {
            if (!labels.get(i).equals(change_label)) {
                change_label = labels.get(i);
                Pair<Calendar, Integer> dt = indexToDayTimeIndex(date, period.getValue0() + i);
                WindPeriod aux = new WindPeriod(new Time("", dt.getValue0(), dt.getValue1()), new Time("", dt.getValue0(), dt.getValue1()));
                WindChange w_change = new WindChange(aux, change_label);
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
    private Pair<Calendar, Integer> indexToDayTimeIndex(Calendar date, int index) {

        int new_index = index % 3;
        Calendar new_date = date;
        new_date.add(Calendar.DATE, new_index);

        return new Pair(new_date, new_index);
    }
}
