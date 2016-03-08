package jgaliweather.configuration.configuration_reader;

import jgaliweather.data.data_structures.Language;
import java.util.ArrayList;

/* This class holds information about the languages defined 
   in a configuration file. */
public class LanguageConfiguration {

    private ArrayList<Language> languages;

    public LanguageConfiguration() {
        languages = new ArrayList();
    }

    public ArrayList<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<Language> languages) {
        this.languages = languages;
    }

    @Override
    public String toString() {
        return "Number of languages: " + languages.size();
    }
}
