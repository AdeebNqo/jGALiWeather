package jgaliweather.configuration.template_reader;

/* Defines a specific time, including date and part of the day */
public class Time {

    private String name;
    private Integer time;
    private Integer day;

    public Time(String name) {
        this.name = name;
        this.time = null;
        this.day = null;
    }

    public Time(String name, Integer time, Integer day) {
        this.name = name;
        this.time = time;
        this.day = day;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
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
