package jgaliweather.data.data_structures;

/* Defines a language template, used to store language template file paths. */
public class Language {

    private String name;
    private String path;

    /*
    Initializes a new Language object

    :param name: The language name
    :param path: The path to the language
    template file

    :return: A new Language object
     */
    public Language(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return name + ": " + path;
    }
}
