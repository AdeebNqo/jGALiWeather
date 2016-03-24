package jgaliweather.nlg.nlg_generators;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;
import jgaliweather.configuration.template_reader.LabelSet;

/*
    Implements a natural language text generator
    for the air quality state variable.
 */
public class ICAGenerator {

    private final String NO_NUANCE = "nuance_nonuance";
    private final String IMPROVE = "improve";
    private final String CHANGE = "change";

    private LabelSet ica_template;
    private String ica_expr;
    private ArrayList<String> weather_info;
    private String text;
    private String nuance;
    private String change_label;

    /*
        Initializes an ICAGenerator object

        :param ica_template: An air quality state
        natural language expression set
        :param ica_expr: An air quality state
        linguistic description
        :param weather_info: Additional meteorological
        information

        :return: A new ICAGenerator object
     */
    public ICAGenerator(LabelSet ica_template, String ica_expr, ArrayList<String> weather_info) {
        this.ica_template = ica_template;
        this.ica_expr = ica_expr;
        this.weather_info = weather_info;
        this.nuance = NO_NUANCE;
        this.text = null;
        this.change_label = null;
    }

    /*
        Parses the intermediate linguistic descriptions
        and returns a single natural language text
        description

        :return: A natural language description of the
        air quality state forecast
     */
    public String generate() {

        boolean finish = false;
        StringTokenizer st = new StringTokenizer(ica_expr);
        st.nextToken();
        st.nextToken();
        String ica = st.nextToken();

        if (ica_expr.contains("-")) {
            improving();
            finish = true;
        }
        if (ica_expr.contains("+")) {
            change();
            finish = true;
        }
        if (ica_expr.contains("0 0")) {
            stable(ica);
            finish = true;
        }
        if (ica_expr.contains("- +") || ica_expr.contains("+ -")) {
            mchange(ica);
            finish = true;
        }
        if (ica_expr.contains("+ 0") || ica_expr.contains("- 0")) {
            schange(ica);
            finish = true;
        }
        if (ica_expr.contains("0 +") || ica_expr.contains("0 -")) {
            echange(ica);
            finish = true;
        }
        if (ica_expr.contains("+ +") || ica_expr.contains("- -")) {
            pchange(ica);
            finish = true;
        }
        if (finish) {
            finishDescription();
        }

        return text;
    }

    private void improving() {
        change_label = IMPROVE;
    }

    private void change() {
        change_label = CHANGE;
    }

    private void stable(String ica) {
        text = MessageFormat.format(ica_template.getLabels().get("case_stable").getData(), ica_template.getLabels().get(ica));
        nuance = weatherNuance(0, ica, change_label);
    }

    private void mchange(String ica) {
        text = MessageFormat.format(ica_template.getLabels().get("case_mchange").getData(), ica_template.getLabels().get(change_label), ica_template.getLabels().get(ica));
        nuance = weatherNuance(2, ica, change_label);
    }

    private void schange(String ica) {
        text = MessageFormat.format(ica_template.getLabels().get("case_echange").getData(), ica_template.getLabels().get(change_label), ica_template.getLabels().get(ica));
        nuance = weatherNuance(1, ica, change_label);
    }

    private void echange(String ica) {
        text = MessageFormat.format(ica_template.getLabels().get("case_schange").getData(), ica_template.getLabels().get(change_label), ica_template.getLabels().get(ica));
        nuance = weatherNuance(2, ica, change_label);
    }

    private void pchange(String ica) {
        text = MessageFormat.format(ica_template.getLabels().get("case_pchange").getData(), ica_template.getLabels().get(change_label), ica_template.getLabels().get(ica));
        nuance = weatherNuance(2, ica, change_label);
    }

    private void finishDescription() {
        text = ica_template.getLabels().get("start").getData()+ " " + text + ica_template.getLabels().get(nuance).getData();
    }

    private String weatherNuance(int w_index, String ica_label, String trend) {
        if (weather_info != null) {
            if (ica_label.equals("G")) {
                if (weather_info.get(w_index).equals("R")) {
                    return "nuance_rain";
                } else if (weather_info.get(w_index).equals("W")) {
                    return "nuance_wind";
                } else if (weather_info.get(w_index).equals("RW")) {
                    return "nuance_rainwind";
                }
            } else if ((ica_label.equals("I") || ica_label.equals("B") || ica_label.equals("H")) && !trend.equals(IMPROVE)) {
                if (weather_info.get(w_index).equals("S")) {
                    return "nuance_sunnowind";
                } else if (weather_info.get(w_index).equals("D")) {
                    return "nuance_dry";
                }
            }
        }
        return NO_NUANCE;
    }
}
