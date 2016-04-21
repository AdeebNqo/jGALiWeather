package jgaliweather.configuration.template_reader.template_components;

/* Defines a text object which other objects can fill in with proper data */
public class Variable {

    private String name;
    private String value;

    public Variable(String name) {
        this.name = name;
        this.value = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (value != null) {
            return value;
        } else {
            return "";
        }
    }
}
