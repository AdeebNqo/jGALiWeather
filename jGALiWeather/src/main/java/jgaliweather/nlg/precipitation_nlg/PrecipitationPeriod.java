package jgaliweather.nlg.precipitation_nlg;

import java.util.ArrayList;
import jgaliweather.configuration.template_reader.LabelSet;
import jgaliweather.configuration.template_reader.template_components.Time;
import org.javatuples.Pair;

/*
    Defines a period of time used to
    determine a precipitation period
 */
public class PrecipitationPeriod {

    private Time beginning;
    private Time end;

    /*
        Initializes a PrecipitationPeriod object

        :param beg: Beginning of the period
        :param end: End of the period

        :return: A new PrecipitationPeriod object
     */
    public PrecipitationPeriod(Time beginning, Time end) {
        this.beginning = beginning;
        this.end = end;
    }

    public Time getBeginning() {
        return beginning;
    }

    public void setBeginning(Time beginning) {
        this.beginning = beginning;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    /*
        Returns the number of days the
        period covers

        :return: The length of the period
        in days
     */
    public int numberOfDays() {
        return (end.getDay() - beginning.getDay()) % 7 + 1;
    }

    /*
        Obtains a list of the days
        the period covers

        :return: A list of the days included
        in the period
     */
    public ArrayList<Integer> days() {

        ArrayList<Integer> days = new ArrayList();

        for (int i = 0; i < numberOfDays(); i++) {
            days.add((beginning.getDay() + i) % 7);
        }

        return days;
    }

    /*
        Determines if this period covers a single
        moment and returns the date and time

        :return: A tuple including the moment
        date and time if the period is single.
        Otherwise, null is returned
     */
    public Pair<Integer, Integer> single() {

        if (beginning.getDay().equals(end.getDay()) && beginning.getTime().equals(end.getTime())) {
            return new Pair(beginning.getDay(), beginning.getTime());
        } else {
            return null;
        }
    }

    /*
        Converts this object into a natural language expression

        :param expression_template: An expression template
        :param day_template: A day expression template
        :param time_template: A time expression template
        :param mode: Period text conversion mode

        :return: A natural language expression of this object
     */
    public String toText(LabelSet expresion_template, LabelSet day_template, LabelSet time_template, String mode) {

        String d = "";

        if (mode.equals("D")) {
            if (single() != null) {
                d = expresion_template.getLabels().get("single_period").getData() + " "
                        + day_template.getLabels().get(end.getDay() + "").getData();

            } else {
                String d1 = expresion_template.getLabels().get("composite_period_start").getData() + " "
                        + day_template.getLabels().get(beginning.getDay() + "").getData();

                String d2 = expresion_template.getLabels().get("composite_period_end").getData() + " "
                        + day_template.getLabels().get(end.getDay() + "").getData();

                d = d1 + " " + d2;
            }

            return d;

        } else if (single() != null) {
            return expresion_template.getLabels().get("single_period").getData() + " "
                    + day_template.getLabels().get(end.getDay() + "").getData() + " "
                    + time_template.getLabels().get(end.getDay() + "").getData();

        } else if (beginning.getDay() != end.getDay()) {
            String start = expresion_template.getLabels().get("composite_period_start").getData() + " "
                    + day_template.getLabels().get(beginning.getDay() + "").getData() + " "
                    + time_template.getLabels().get(beginning.getDay() + "").getData();

            String end = expresion_template.getLabels().get("composite_period_end").getData() + " "
                    + day_template.getLabels().get(this.end.getDay() + "").getData() + " "
                    + time_template.getLabels().get(this.end.getDay() + "").getData();

            return start + " " + end;

        } else {
            return expresion_template.getLabels().get("single_period").getData() + " "
                    + day_template.getLabels().get(end.getDay() + "").getData();
        }
    }
}
