package jgaliweather.configuration.template_reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/* Implements a template manager, which organizes
   templates by language and, when requested by a
   template for a given language, returns a random
   template.
 */
public class TemplateManager {

    private HashMap<String, ArrayList<Template>> templates;
    private Random generator;

    public TemplateManager() {
        this.templates = new HashMap();
        this.generator = new Random();
    }

    public HashMap<String, ArrayList<Template>> getTemplates() {
        return templates;
    }

    public void setTemplates(HashMap<String, ArrayList<Template>> templates) {
        this.templates = templates;
    }

    public Random getGenerator() {
        return generator;
    }

    public void setGenerator(Random generator) {
        this.generator = generator;
    }

    /*
        Returns a random template for a given language

        :param language: A language string identifier

        :return: A random Template object
     */
    public Template getTemplate(String language) {

        ArrayList<Template> temps = templates.get(language);

        return temps.get(generator.nextInt(temps.size()));
    }

    /*
        Adds a new language template to the manager

        :param language: The language of the template as
        a string identifier
    
        :param template: The Template object to be added
     */
    public void addTemplate(String language, Template template) {

        if (templates.containsKey(language)) {
            templates.get(language).add(template);
        } else {
            templates.put(language, new ArrayList());
            templates.get(language).add(template);
        }
    }

    /*
        Returns the list of available languages stored
        by the manager

        :return: A list of language identifiers
     */
    public ArrayList<String> getLanguages() {

        return new ArrayList(templates.keySet());
    }
}
