package jgaliweather.nlg.wind_nlg;

import java.util.ArrayList;
import java.util.Calendar;
import jgaliweather.configuration.template_reader.LabelSet;
import jgaliweather.configuration.template_reader.template_components.Time;
import org.javatuples.Pair;

/*
    Defines a period of time used to determine a wind period
 */
public class WindPeriod {

    private Time beginning;
    private Time end;

    /*
        Initializes a WindPeriod object

        :param beg: Beginning of the period
        :param end: End of the period

        :return: A new WindPeriod object
     */
    public WindPeriod(Time beginning, Time end) {
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
        Returns the number of days the period covers

        :return: The length of the period in days
     */
    public int numberOfDays() {
        return (int) ((end.getTime().getTime().getTime() - beginning.getTime().getTime().getTime()) / (1000 * 60 * 60 * 24)) + 1;
    }

    /*
        Obtains a list of the days the period covers

        :return: A list of the days included in the period
     */
    public ArrayList<Integer> days() {

        ArrayList<Integer> days = new ArrayList();

        for (int i = 0; i < numberOfDays(); i++) {
            days.add(beginning.getTime().get(Calendar.DAY_OF_MONTH) + i);

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

        if (beginning.getTime().get(Calendar.DAY_OF_MONTH) == end.getTime().get(Calendar.DAY_OF_MONTH) && beginning.getTime().getTimeInMillis() == end.getTime().getTimeInMillis()) {
            return new Pair(beginning.getTime().get(Calendar.DAY_OF_MONTH), beginning.getTime().getTimeInMillis());
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

        if (mode.equals("START")) {
            d = expresion_template.getLabels().get("change_start").getData() + " "
                    + day_template.getLabels().get(beginning.getTime().get(Calendar.DAY_OF_MONTH) + "").getData() + " "
                    + time_template.getLabels().get(beginning.getTime().getTime() + "").getData();
        } else if (single() != null) {
            d = expresion_template.getLabels().get("single_period").getData() + " "
                    + day_template.getLabels().get(end.getTime().get(Calendar.DAY_OF_MONTH) + "").getData() + " "
                    + time_template.getLabels().get(end.getTime().getTime() + "").getData();
        } else {
            String d1 = expresion_template.getLabels().get("composite_period_start").getData() + " "
                    + day_template.getLabels().get(beginning.getTime().get(Calendar.DAY_OF_MONTH) + "").getData() + " "
                    + time_template.getLabels().get(beginning.getTime().getTime() + "").getData();

            String d2 = expresion_template.getLabels().get("composite_period_end").getData() + " "
                    + day_template.getLabels().get(end.getTime().get(Calendar.DAY_OF_MONTH) + "").getData() + " "
                    + time_template.getLabels().get(end.getTime().getTime() + "").getData();

            d = d1 + " " + d2;
        }

        return d;

    }
}
