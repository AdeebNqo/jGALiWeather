package jgaliweather.configuration.template_reader.template_components;

/* Defines a period of time */
public class Period implements TimeLabel {

    private String name;
    private String data;

    public Period(String name) {
        this.name = name;
        this.data = "";
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return data;
    }
}
