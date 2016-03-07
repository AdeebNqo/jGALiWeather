package jgaliweather.configuration.template_reader.template_components;

import java.util.Calendar;

/* Defines a specific time, including date and part of the day */
public class Time implements TimeLabel {

    private String name;
    private Calendar time;
    private Float day;

    public Time(String name) {
        this.name = name;
        this.time = null;
        this.day = null;
    }

    public Time(String name, Calendar time, float day) {
        this.name = name;
        this.time = time;
        this.day = day;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return time.toString() + " " + day.toString();
    }
}
