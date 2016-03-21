package jgaliweather.nlg.nlg_generators;

import java.util.StringTokenizer;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.template_reader.LabelSet;
import jgaliweather.configuration.template_reader.Template;
import jgaliweather.configuration.template_reader.template_components.Option;

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
    }

    /*
        Parses the intermediate linguistic descriptions
        and returns a single natural language text
        description

        :return: A natural language description of the
        temperature variable forecast
     */
    public String parseAndGenerate() {

        template.setSelectedCase(template.getCases().get(0));
        StringTokenizer cl_st = new StringTokenizer(cl_string);
        if (cl_st.countTokens() == 1) {
            climateAlternative1(cl_st.nextToken());
        } else if (cl_st.countTokens() == 2) {
            climateAlternative2(cl_st.nextToken(), cl_st.nextToken());
        }

        StringTokenizer t_st = new StringTokenizer(t_string);
        if (t_st.countTokens() == 1) {
            temperatureAlternative1(t_st.nextToken());
        } else if (t_st.countTokens() == 2) {
            temperatureAlternative2(t_st.nextToken(), t_st.nextToken());
        }

        StringTokenizer var_st = new StringTokenizer(var_string);
        if (var_st.countTokens() == 1) {
            variabilityAlternative1(var_st.nextToken());
        } else if (var_st.countTokens() == 2) {
            variabilityAlternative2(var_st.nextToken(), var_st.nextToken());
        }

        String text = template.toString();

        for (Option o : template.getOptions().values()) {
            o.setUsed(false);
        }

        return text;
    }

    private void climateAlternative1(String token) {
        template.getOptions().get(1).setUsed(true);
        template.getVariables().get("norT").setValue(cl_var.getLabels().get(token).getData());
    }

    private void climateAlternative2(String token1, String token2) {
        template.getOptions().get(2).setUsed(true);
        template.getVariables().get("maxT").setValue(cl_var.getLabels().get(token1).getData());
        template.getVariables().get("minT").setValue(cl_var.getLabels().get(token2).getData());
    }

    private void temperatureAlternative1(String token) {
        template.getOptions().get(3).setUsed(true);
        template.getVariables().get("norV").setValue(t_var.getLabels().get(token).getData());
    }

    private void temperatureAlternative2(String token1, String token2) {
        template.getOptions().get(5).setUsed(true);
        template.getOptions().get(7).setUsed(true);
        template.getVariables().get("maxV").setValue(t_var.getLabels().get(token1).getData());
        template.getVariables().get("minV").setValue(t_var.getLabels().get(token2).getData());
    }

    private void variabilityAlternative1(String token) {

        if (!token.equals("C")) {
            if (template.getOptions().get(3).isUsed()) {
                template.getOptions().get(4).setUsed(true);
                template.getVariables().get("norO").setValue(var_var.getLabels().get(token).getData());
            } else {
                template.getOptions().get(6).setUsed(true);
                template.getOptions().get(7).setUsed(true);
                template.getVariables().get("maxO").setValue(t_var.getLabels().get(token).getData());
                template.getVariables().get("minO").setValue(t_var.getLabels().get(token).getData());
            }
        }
    }

    private void variabilityAlternative2(String token1, String token2) {
        if (template.getOptions().get(3).isUsed()) {
            if (!token1.equals("C")) {
                template.getVariables().get("norO").setValue(var_var.getLabels().get(token1).getData());
            } else {
                template.getVariables().get("norO").setValue(var_var.getLabels().get(token2).getData());
            }
            template.getOptions().get(4).setUsed(true);
        } else {
            if (!token2.equals("C") && template.getOptions().get(5).isUsed()) {
                template.getVariables().get("minO").setValue(var_var.getLabels().get(token2).getData());
                template.getOptions().get(6).setUsed(true);
            }
            if (!token1.equals("C") && template.getOptions().get(7).isUsed()) {
                template.getVariables().get("maxO").setValue(var_var.getLabels().get(token1).getData());
                template.getOptions().get(8).setUsed(true);
            }
        }
    }
}
