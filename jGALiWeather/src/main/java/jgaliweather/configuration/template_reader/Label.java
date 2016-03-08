package jgaliweather.configuration.template_reader;

/* Defines a linguistic label */
public class Label {

    private String name;
    private String data;

    public Label(String name, String data) {
        this.name = name;
        this.data = data;
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
