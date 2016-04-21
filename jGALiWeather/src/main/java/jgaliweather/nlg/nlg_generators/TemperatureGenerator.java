package jgaliweather.nlg.nlg_generators;

import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.template_reader.LabelSet;
import jgaliweather.configuration.template_reader.Template;

/*
    Implements a natural language text generator
    for the temperature variable.
 */
public class TemperatureGenerator {

    private Template template;
    private LabelSet cl_var;
    private LabelSet t_var;
    private LabelSet var_var;
    private Partition var_part;
    private String cl_string;
    private String t_string;
    private String var_string;
    private String climate_grammar;
    private String temperature_grammar;
    private String variability_grammar;

    /*
        Initializes a TemperatureGenerator object

        :param template: A natural language template for temperature
        :param cl_var: A climatic temperature labelset
        :param t_var: A temperature variation labelset
        :param var_var: A temperature oscillation labelset
        :param var_part: A temperature oscillation label partition
        :param cl_string: A climatic behavior linguistic description
        :param t_string: A temperature variation linguistic description
        :param var_string: A temperature oscillation linguistic description

        :return: A new TemperatureGenerator object
    */
    public TemperatureGenerator(Template template, LabelSet cl_var, LabelSet t_var, LabelSet var_var, Partition var_part, String cl_string, String t_string, String var_string) {
        this.template = template;
        this.cl_var = cl_var;
        this.t_var = t_var;
        this.var_var = var_var;
        this.var_part = var_part;
        this.cl_string = cl_string;
        this.t_string = t_string;
        this.var_string = var_string;
        this.climate_grammar = null;
        this.temperature_grammar = null;
        this.variability_grammar = null;

        defineGrammar();
    }

    /*
        Defines the parsing grammar for the
        temperature NLG class.
    */
    private void defineGrammar() { // Necesito encontrar una libreria similar a pyparsing
        
    }

}
