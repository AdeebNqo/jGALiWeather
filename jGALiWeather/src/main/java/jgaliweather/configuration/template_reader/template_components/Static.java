package jgaliweather.configuration.template_reader.template_components;

/* Defines a static text within a template */
public class Static {

    private String value;

    public Static(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
