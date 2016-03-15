package jgaliweather.nlg.precipitation_nlg;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import jgaliweather.configuration.template_reader.LabelSet;
import org.javatuples.Pair;

/*
    Defines a period of time used to
    determine a precipitation period
 */
public class PrecipitationPeriod {

    private GregorianCalendar beginning;
    private GregorianCalendar end;

    /*
        Initializes a PrecipitationPeriod object

        :param beg: Beginning of the period
        :param end: End of the period

        :return: A new PrecipitationPeriod object
     */
    public PrecipitationPeriod(GregorianCalendar beginning, GregorianCalendar end) {
        this.beginning = beginning;
        this.end = end;
    }

    public GregorianCalendar getBeginning() {
        return beginning;
    }

    public void setBeginning(GregorianCalendar beginning) {
        this.beginning = beginning;
    }

    public GregorianCalendar getEnd() {
        return end;
    }

    public void setEnd(GregorianCalendar end) {
        this.end = end;
    }

    /*
        Returns the number of days the
        period covers

        :return: The length of the period
        in days
     */
    public int numberOfDays() {
        return (int) ((end.getTime().getTime() - beginning.getTime().getTime()) / (1000 * 60 * 60 * 24));
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
            days.add(beginning.get(Calendar.DAY_OF_MONTH + i) % 7);

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

        if (beginning.get(Calendar.DAY_OF_MONTH) == end.get(Calendar.DAY_OF_MONTH) && beginning.getTimeInMillis() == end.getTimeInMillis()) {
            return new Pair(beginning.get(Calendar.DAY_OF_MONTH), beginning.getTimeInMillis());
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
                        + day_template.getLabels().get(end.get(Calendar.DAY_OF_MONTH) + "").getData();

            } else {
                String d1 = expresion_template.getLabels().get("composite_period_start").getData() + " "
                        + day_template.getLabels().get(beginning.get(Calendar.DAY_OF_MONTH) + "").getData();

                String d2 = expresion_template.getLabels().get("composite_period_end").getData() + " "
                        + day_template.getLabels().get(end.get(Calendar.DAY_OF_MONTH) + "").getData();

                d = d1 + " " + d2;
            }

            return d;

        } else if (single() != null) {
            return expresion_template.getLabels().get("single_period").getData() + " "
                    + day_template.getLabels().get(end.get(Calendar.DAY_OF_MONTH) + "").getData() + " "
                    + time_template.getLabels().get(end.get(Calendar.DAY_OF_MONTH) + "").getData();

        } else if (beginning.get(Calendar.DAY_OF_MONTH) != end.get(Calendar.DAY_OF_MONTH)) {
            String start = expresion_template.getLabels().get("composite_period_start").getData() + " "
                    + day_template.getLabels().get(beginning.get(Calendar.DAY_OF_MONTH) + "").getData() + " "
                    + time_template.getLabels().get(beginning.get(Calendar.DAY_OF_MONTH) + "").getData();

            String end = expresion_template.getLabels().get("composite_period_end").getData() + " "
                    + day_template.getLabels().get(this.end.get(Calendar.DAY_OF_MONTH) + "").getData() + " "
                    + time_template.getLabels().get(this.end.get(Calendar.DAY_OF_MONTH) + "").getData();

            return start + " " + end;

        } else {
            return expresion_template.getLabels().get("single_period").getData() + " "
                    + day_template.getLabels().get(end.get(Calendar.DAY_OF_MONTH) + "").getData();
        }
    }
}
