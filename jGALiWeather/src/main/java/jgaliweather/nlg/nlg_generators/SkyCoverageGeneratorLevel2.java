package jgaliweather.nlg.nlg_generators;

import java.util.Arrays;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.template_reader.LabelSet;
import jgaliweather.configuration.template_reader.Template;
import jgaliweather.configuration.template_reader.template_components.Option;

/*
    Implements a natural language text generator
    for the quantifier cloud coverage linguistic
    description approach.
 */
public class SkyCoverageGeneratorLevel2 {

    private Template template;
    private LabelSet coverage;
    private Partition cov_partition;
    private double[][] l_description;

    /*
        Initializes a SkyCoverageGeneratorLevel2 object

        :param template: A cloud coverage template
        :param coverage: A cloud coverage labelset
        :param cov_partition: A cloud coverage quantifier partition set
        :param l_description: A cloud coverage linguistic description

        :return: A new SkyCoverageGeneratorLevel2 object
     */
    public SkyCoverageGeneratorLevel2(Template template, LabelSet coverage, Partition cov_partition, double[][] l_description) {
        this.template = template;
        this.coverage = coverage;
        this.cov_partition = cov_partition;
        this.l_description = l_description;
    }

    /*
        Returns a single natural language text
        description

        :return: A natural language description of the
        cloud coverage variable forecast
     */
    public String generate() {

        double[] aux1 = {2.0, 0.0, 0.0};
        double[] aux2 = {1.0, 1.0, 0.0};
        double[] aux3 = {1.0, 0.0, 0.0};

        if (Arrays.equals(l_description[0], aux1)) {
            template.setSelectedCase(template.getCases().get(0));
            template.getVariables().get("pred1").setValue(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][0]).getName()).getData());

            if (l_description[2][1] > 0.1 || l_description[2][2] > 0.1) {
                template.getOptions().get(1).setUsed(true);

                int optional;
                if (l_description[2][1] >= l_description[2][2]) {
                    optional = 1;
                } else {
                    optional = 2;
                }
                template.getVariables().get("pred2").setValue(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][optional]).getName()).getData());
            }
        } else if (Arrays.equals(l_description[0], aux2)) {
            template.setSelectedCase(template.getCases().get(1));
            template.getVariables().get("rel1").setValue(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][0]).getName()).getData());
            template.getVariables().get("rel2").setValue(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][1]).getName()).getData());

            if (l_description[2][2] > 0.1) {
                template.getOptions().get(2).setUsed(true);
                template.getVariables().get("rel3").setValue(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][2]).getName()).getData());
            }
        } else if (Arrays.equals(l_description[0], aux3)) {
            template.setSelectedCase(template.getCases().get(2));
            template.getVariables().get("rel4").setValue(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][0]).getName()).getData());

            int optional;
            if (l_description[2][1] >= l_description[2][2]) {
                optional = 1;
            } else {
                optional = 2;
            }
            template.getVariables().get("rel5").setValue(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][optional]).getName()).getData());

            int optional2;
            if (optional == 1) {
                optional2 = 2;
            } else {
                optional2 = 1;
            }
            if (l_description[2][optional2] > 0.1) {
                template.getOptions().get(3).setUsed(true);
                template.getVariables().get("rel6").setValue(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][optional2]).getName()).getData());
            }
        } else {
            template.setSelectedCase(template.getCases().get(3));
        }

        String text = template.toString();

        for (Option o : template.getOptions().values()) {
            o.setUsed(false);
        }

        return text;
    }
}
