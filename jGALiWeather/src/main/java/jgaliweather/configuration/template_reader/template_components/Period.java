package jgaliweather.configuration.template_reader.template_components;

/* Defines a period of time */
public class Period implements TimeLabel{
    
    private String name;
    private String data;

    public Period(String name) {
        this.name = name;
        this.data = "";
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return data;
    }
}
