package jgaliweather.nlg.nlg_generators;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;
import jgaliweather.configuration.template_reader.LabelSet;
import jgaliweather.configuration.template_reader.template_components.Time;
import jgaliweather.nlg.precipitation_nlg.PrecipitationDayGroup;
import jgaliweather.nlg.precipitation_nlg.PrecipitationEpisode;
import jgaliweather.nlg.precipitation_nlg.PrecipitationEpisodeGroup;
import jgaliweather.nlg.precipitation_nlg.PrecipitationNuance;
import jgaliweather.nlg.precipitation_nlg.PrecipitationPeriod;
import jgaliweather.nlg.precipitation_nlg.PrecipitationTermGroup;
import org.javatuples.Pair;

/*
    Implements a natural language text generator
    for the precipitation variable.
 */
public class RainGenerator {

    private HashMap<String, LabelSet> template;
    private int term_length;
    private ArrayList<String> result_strings;
    private Calendar date;
    private ArrayList<PrecipitationEpisode> episodes;

    /*
        Initializes a RainGenerator object

        :param template: A precipitation natural language template
        :param date: The date of the forecast
        :param term_length: The term lenght of the forecast
        :param result_strings: A precipitation linguistic description

        :return: A new RainGenerator object
     */
    public RainGenerator(HashMap<String, LabelSet> template, int term_length, ArrayList<String> result_strings, Calendar date) {
        this.template = template;
        this.term_length = term_length;
        this.result_strings = result_strings;
        this.date = date;
        this.episodes = new ArrayList();
    }

    /*
        Parses the intermediate linguistic descriptions
        and returns a single natural language text
        description

        :return: A natural language description of the
        precipitation variable forecast
     */
    public String parseAndGenerate() {

        if (result_strings.size() > 0) {
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
            PrecipitationDayGroup pdg = new PrecipitationDayGroup(episodes, term_length);
            PrecipitationEpisodeGroup peg = new PrecipitationEpisodeGroup(episodes);
            PrecipitationTermGroup ptg = new PrecipitationTermGroup(episodes, term_length);

            String final_forecast = null;
            if (ptg.validateGroup()) {
                final_forecast = ptg.generateReport(template);
            } else if (pdg.validateGroup()) {
                final_forecast = pdg.generateReport(template);
            } else {
                final_forecast = peg.generateReport(template);
            }

            return final_forecast;

        } else {
            return null;
        }
    }

    private void alternative0(String time, String label) {
        Pair<Calendar, Integer> dt = indexToDayTimeIndex(date, Integer.parseInt(time));
        PrecipitationPeriod pp = new PrecipitationPeriod(new Time("", dt.getValue0(), dt.getValue1()), new Time("", dt.getValue0(), dt.getValue1()));
        PrecipitationEpisode prec_ep = new PrecipitationEpisode(pp, label);
        episodes.add(prec_ep);
    }

    private void alternative1(Pair<Integer, Integer> period, ArrayList<String> labels) {

        PrecipitationEpisode prec_ep = new PrecipitationEpisode();

        int l_common = 0;
        HashMap<String, Integer> apparitions = new HashMap();
        for (String label : labels) {
            apparitions.merge(label, 1, Integer::sum);
        }

        if (apparitions.size() == 1) {
            for (String label : labels) {
                if (l_common < apparitions.getOrDefault(label, 0)) {
                    l_common = apparitions.getOrDefault(label, 0);
                    prec_ep.setLabel(label);
                }
            }
        } else {
            PrecipitationNuance curr_nuance = null;
            prec_ep.setLabel("I");

            for (int i = 0; i < labels.size(); i++) {
                String l = labels.get(i);
                if (!l.equals("I") && !l.equals("P")) {
                    if (curr_nuance == null) {
                        Pair<Calendar, Integer> beg_nuance_dt = indexToDayTimeIndex(date, period.getValue0() + i);
                        PrecipitationPeriod pp = new PrecipitationPeriod(new Time("", beg_nuance_dt.getValue0(), beg_nuance_dt.getValue1()), null);
                        curr_nuance = new PrecipitationNuance(pp, l);
                    } else if (curr_nuance.getLabel() != l) {
                        Pair<Calendar, Integer> end_nuance_dt = indexToDayTimeIndex(date, period.getValue0() + i - 1);
                        curr_nuance.getDuration().setEnd(new Time("", end_nuance_dt.getValue0(), end_nuance_dt.getValue1()));
                        prec_ep.getNuances().add(curr_nuance);

                        Pair<Calendar, Integer> beg_nuance_dt = indexToDayTimeIndex(date, period.getValue0() + i);
                        PrecipitationPeriod pp = new PrecipitationPeriod(new Time("", beg_nuance_dt.getValue0(), beg_nuance_dt.getValue1()), null);
                        curr_nuance = new PrecipitationNuance(pp, l);
                    }
                } else if (curr_nuance != null) {
                    Pair<Calendar, Integer> end_nuance_dt = indexToDayTimeIndex(date, period.getValue0() + i - 1);
                    curr_nuance.getDuration().setEnd(new Time("", end_nuance_dt.getValue0(), end_nuance_dt.getValue1()));
                    prec_ep.getNuances().add(curr_nuance);
                    curr_nuance = null;
                }
            }

            if (curr_nuance != null) {
                Pair<Calendar, Integer> end_nuance_dt = indexToDayTimeIndex(date, period.getValue1());
                curr_nuance.getDuration().setEnd(new Time("", end_nuance_dt.getValue0(), end_nuance_dt.getValue1()));
                prec_ep.getNuances().add(curr_nuance);
            }
            
            Pair<Calendar, Integer> dt1 = indexToDayTimeIndex(date, period.getValue0());
            Pair<Calendar, Integer> dt2 = indexToDayTimeIndex(date, period.getValue1());
            prec_ep.setDuration(new PrecipitationPeriod(new Time("", dt1.getValue0(), dt1.getValue1()), new Time("", dt2.getValue0(), dt2.getValue1())));
            episodes.add(prec_ep);
        }
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
