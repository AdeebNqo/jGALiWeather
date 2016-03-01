package data;

/* Defines a language template, used to store language template file paths. */
public class Language {

    private final String name;
    private final String path;

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
    
    public String getDataAsString() {
        return name + ": " + path;
    }
}
