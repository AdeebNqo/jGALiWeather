package jgaliweather.nlg.precipitation_nlg;

import java.util.ArrayList;
import java.util.Objects;
import jgaliweather.configuration.template_reader.LabelSet;
import jgaliweather.configuration.template_reader.Time;
import org.javatuples.Pair;
import simplenlg.framework.NLGFactory;
import simplenlg.phrasespec.PPPhraseSpec;

/*
    Defines a period of time used to
    determine a precipitation period
 */
public class PrecipitationPeriod {

    private Time beginning;
    private Time end;
    private NLGFactory nlgFactory;

    /*
        Initializes a PrecipitationPeriod object

        :param beg: Beginning of the period
        :param end: End of the period
        :param nlgFactory: Class that contains methods for creating syntactic phrases

        :return: A new PrecipitationPeriod object
     */
    public PrecipitationPeriod(Time beginning, Time end, NLGFactory nlgFactory) {
        this.beginning = beginning;
        this.end = end;
        this.nlgFactory = nlgFactory;
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
        int aux = end.getDay() - beginning.getDay();

        if (aux < 0) {
            aux = 7 + aux;
        } else {
            aux = aux % 7;
        }
        return aux + 1;
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

        :return: A list of prepositional phrases describing this object
     */
    public ArrayList<PPPhraseSpec> toText(LabelSet day_template, LabelSet time_template, String mode) {

        ArrayList<PPPhraseSpec> phrases = new ArrayList();

        if (mode.equals("D")) {
            if (single() != null) {
                phrases.add(nlgFactory.createPrepositionPhrase("on", day_template.getLabels().get(end.getDay() + "").getData()));

            } else {
                phrases.add(nlgFactory.createPrepositionPhrase("from", day_template.getLabels().get(beginning.getDay() + "").getData()));

                phrases.add(nlgFactory.createPrepositionPhrase("to", day_template.getLabels().get(end.getDay() + "").getData()));
            }

        } else if (single() != null) {
            phrases.add(nlgFactory.createPrepositionPhrase("on", day_template.getLabels().get(end.getDay() + "").getData() 
                    + " " + time_template.getLabels().get(end.getTime() + "").getData()));

        } else if (!Objects.equals(beginning.getDay(), end.getDay())) {
            phrases.add(nlgFactory.createPrepositionPhrase("from", day_template.getLabels().get(beginning.getDay() + "").getData() 
                    + " " + time_template.getLabels().get(beginning.getTime() + "").getData()));

            phrases.add(nlgFactory.createPrepositionPhrase("to", day_template.getLabels().get(this.end.getDay() + "").getData() 
                    + " " + time_template.getLabels().get(this.end.getTime() + "").getData()));

        } else {
            phrases.add(nlgFactory.createPrepositionPhrase("on", day_template.getLabels().get(end.getDay() + "").getData()));
        }

        return phrases;
    }
}
