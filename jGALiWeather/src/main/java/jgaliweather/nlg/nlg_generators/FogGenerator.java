package jgaliweather.nlg.nlg_generators;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import jgaliweather.configuration.template_reader.LabelSet;

public class FogGenerator {

    private HashMap<Integer, ArrayList<ArrayList<Integer>>> fog_data;
    private LabelSet fog_expr;
    private LabelSet parts_day;
    private LabelSet days_week;
    private Calendar curr_date;
    private int term_length;

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
    }

    /*
        Returns a single natural language text
        description

        :return: A natural language description of the
        fog variable forecast
     */
    public String generate() {

        LinkedHashMap<Integer, String> parts = new LinkedHashMap();
        Map<Integer, ArrayList<ArrayList<Integer>>> fog_data_sorted = new TreeMap(fog_data);

        if (fog_data.size() > 0) {
            for (int k : fog_data_sorted.keySet()) {
                if (parts.containsKey(k)) {
                    parts.replace(k, generatePart(k, fog_data.get(k)));
                } else {
                    parts.put(k, generatePart(k, fog_data.get(k)));
                }
            }

            String text = fog_expr.getLabels().get("start").getData();
            text = text.concat(" " + parts.get(0).toString());
            if (parts.size() > 1) {
                for (int i = 0; i < parts.size() - 2; i++) {
                    text = text.concat(fog_expr.getLabels().get("part_separator").getData() + " " + parts.get(i + 1).toString());
                }
                text = text.concat(fog_expr.getLabels().get("part_separator").getData() + " " + fog_expr.getLabels().get("nexus").getData() + " " + parts.get(parts.size() - 1).toString());
            }
            return text + ".";
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
    private String generatePart(int part, ArrayList<ArrayList<Integer>> days) {

        String text = fog_expr.getLabels().get(part + "").getData() + " ";

        if (days.size() == term_length / 3) {
            boolean persistent = true;
            int semi_persistent = 0;

            for (ArrayList<Integer> d : days) {
                persistent = persistent && d.size() > 1;
                semi_persistent += d.size();
            }

            if (persistent) {
                text = text.concat(fog_expr.getLabels().get("persistent").getData() + " " + fog_expr.getLabels().get("everyday").getData());
            } else if (semi_persistent > term_length / 3) {
                ArrayList<Integer> p_days = new ArrayList();
                for (ArrayList<Integer> d : days) {
                    if (d.size() > 1) {
                        p_days.add(d.get(0));
                    }
                }

                text = text.concat(fog_expr.getLabels().get("everyday").getData() + fog_expr.getLabels().get("separator").getData() + " "
                        + fog_expr.getLabels().get("single_period").getData() + " " + days_week.getLabels().get((p_days.get(0) + curr_date.get(Calendar.DAY_OF_WEEK) % 7) + " ").getData());

                if (p_days.size() > 1) {
                    for (int i = 0; i < p_days.size() - 2; i++) {
                        text = text.concat(fog_expr.getLabels().get("separator").getData() + " " + fog_expr.getLabels().get("single_period").getData() + " "
                                + days_week.getLabels().get((p_days.get(i + 1) + curr_date.get(Calendar.DAY_OF_WEEK) % 7) + " ").getData());

                    }
                    text = text.concat(" " + fog_expr.getLabels().get("nexus").getData() + " " + fog_expr.getLabels().get("single_period").getData() + " "
                            + days_week.getLabels().get((p_days.get(p_days.size() - 1) + curr_date.get(Calendar.DAY_OF_WEEK) % 7) + " ").getData());

                }
            } else {
                text = text.concat(fog_expr.getLabels().get("everyday").getData());
            }
        } else {
            text = text.concat(fog_expr.getLabels().get("single_period").getData() + " "
                    + days_week.getLabels().get((days.get(0).get(0) + curr_date.get(Calendar.DAY_OF_WEEK) % 7) + " ").getData());

            if (days.get(0).size() > 1) {
                text = text.concat(fog_expr.getLabels().get("persistent").getData() + " ");
            }
            if (days.size() > 1) {
                for (int i = 0; i < days.size() - 2; i++) {
                    text = text.concat(fog_expr.getLabels().get("separator").getData() + " " + fog_expr.getLabels().get("single_period").getData() + " "
                            + days_week.getLabels().get((days.get(i + 1).get(0) + curr_date.get(Calendar.DAY_OF_WEEK) % 7) + " ").getData());
                    if (days.get(i + 1).size() > 1) {
                        text = text.concat(" " + fog_expr.getLabels().get("persistent").getData());
                    }
                }
                
                text = text.concat(" " + fog_expr.getLabels().get("nexus").getData() + " " + fog_expr.getLabels().get("single_period").getData() + " "
                            + days_week.getLabels().get((days.get(days.size() - 1).get(0) + curr_date.get(Calendar.DAY_OF_WEEK) % 7) + " ").getData());
                
                if(days.get(days.size()-1).size() > 1) {
                    text = text.concat(" (" + fog_expr.getLabels().get("persistent").getData() + ")");
                }
            }
        }
        return text;
    }
}
