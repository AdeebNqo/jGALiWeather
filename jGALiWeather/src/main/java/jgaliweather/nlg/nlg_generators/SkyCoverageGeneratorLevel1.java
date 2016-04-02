package jgaliweather.nlg.nlg_generators;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.template_reader.LabelSet;
import jgaliweather.configuration.template_reader.Template;
import jgaliweather.configuration.template_reader.template_components.Option;

/*
    Implements a natural language text generator
    for the temporal cloud coverage linguistic
    description approach.
 */
public class SkyCoverageGeneratorLevel1 {

    private Template template;
    private LabelSet coverage;
    private Partition period_partitions;
    private LabelSet period_labels;
    private String result_string;
    private boolean second_approach;

    /*
        Initializes a SkyCoverageGeneratorLevel1 object

        :param template: A cloud coverage template
        :param cov_labels: A cloud coverage labelset
        :param period_partitions: A cloud coverage temporal partition set
        :param period_labels: A period labelset
        :param result_string: A cloud coverage linguistic description

        :return: A new SkyCoverageGeneratorLevel1 object
     */
    public SkyCoverageGeneratorLevel1(Template template, LabelSet coverage, Partition period_partitions, LabelSet period_labels, String result_string) {
        this.template = template;
        this.coverage = coverage;
        this.period_partitions = period_partitions;
        this.period_labels = period_labels;
        this.result_string = result_string;
        this.second_approach = false;
    }

    /*
        Parses the intermediate linguistic descriptions
        and returns a single natural language text
        description

        :return: A natural language description of the
        cloud coverage variable forecast
     */
    public String parseAndGenerate() {

        StringTokenizer st = new StringTokenizer(result_string);
        if (st.countTokens() == 1) {
            alternative0();
        } else if (st.countTokens() == 3) {
            alternative1(st.nextToken(), st.nextToken(), st.nextToken());
        }

        if (!second_approach) {
            String text = template.toString();

            for (Option o : template.getOptions().values()) {
                o.setUsed(false);
            }

            return text;
        } else {
            return null;
        }
    }

    private void alternative0() {
        second_approach = true;
    }

    private void alternative1(String token1, String token2, String token3) {
        int l_common = 0, aux = 0;
        HashMap<String, Integer> labels = new HashMap();
        labels.merge(token1, 1, Integer::sum);
        labels.merge(token2, 1, Integer::sum);
        labels.merge(token3, 1, Integer::sum);

        if (aux < labels.getOrDefault(token1, 0)) {
            aux = labels.getOrDefault(token1, 0);
            l_common = aux;
        }
        if (aux < labels.getOrDefault(token2, 0)) {
            l_common = aux;
            aux = labels.getOrDefault(token2, 0);
        }
        if (aux < labels.getOrDefault(token3, 0)) {
            l_common = aux;
            aux = labels.getOrDefault(token3, 0);
        }

        if (labels.size() == 1) {
            second_approach = true;
        } else if (labels.size() == 2) {
            template.setSelectedCase(template.getCases().get(1));

            if (l_common == 0) {
                template.getVariables().get("cl4").setValue(coverage.getLabels().get(token1).getData());
                template.getTime_labels().get("p4").setData(period_labels.getLabels().get(period_partitions.getSets().get(0).getName()).getData());
                template.getVariables().get("cl5").setValue(coverage.getLabels().get(token2).getData());
                template.getTime_labels().get("p6").setData(period_labels.getLabels().get(period_partitions.getSets().get(1).getName()).getData());
                template.getTime_labels().get("p7").setData(period_labels.getLabels().get(period_partitions.getSets().get(2).getName()).getData());
                template.getOptions().get(4).setUsed(true);
            } else {
                template.getVariables().get("cl4").setValue(coverage.getLabels().get(token1).getData());
                template.getTime_labels().get("p4").setData(period_labels.getLabels().get(period_partitions.getSets().get(0).getName()).getData());

                int n_index = 1;
                if (l_common == 1) {
                    n_index = 2;
                }
                template.getTime_labels().get("p5").setData(period_labels.getLabels().get(period_partitions.getSets().get(n_index).getName()).getData());
                Iterator it = labels.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    if (l_common == (Integer) pair.getValue()) {
                        template.getVariables().get("c15").setValue(coverage.getLabels().get(pair.getKey()).getData());
                        break;
                    }
                    it.remove();
                }

                template.getTime_labels().get("p6").setData(period_labels.getLabels().get(period_partitions.getSets().get(l_common).getName()).getData());
                template.getOptions().get(3).setUsed(true);
            }
        } else {
            template.setSelectedCase(template.getCases().get(0));
            template.getVariables().get("cl1").setValue(coverage.getLabels().get(token1).getData());
            template.getTime_labels().get("p1").setData(period_labels.getLabels().get(period_partitions.getSets().get(0).getName()).getData());
            template.getVariables().get("cl2").setValue(coverage.getLabels().get(token2).getData());
            template.getTime_labels().get("p2").setData(period_labels.getLabels().get(period_partitions.getSets().get(1).getName()).getData());
            template.getVariables().get("cl3").setValue(coverage.getLabels().get(token3).getData());
            template.getTime_labels().get("p3").setData(period_labels.getLabels().get(period_partitions.getSets().get(2).getName()).getData());
            template.getOptions().get(1).setUsed(true);
        }
    }

}
