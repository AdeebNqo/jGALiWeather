package jgaliweather.nlg_simpleNLG.nlg_generators;

import java.util.Arrays;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.template_reader.LabelSet;
import simplenlg.features.Feature;
import simplenlg.features.NumberAgreement;
import simplenlg.features.Tense;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.DocumentElement;
import simplenlg.framework.NLGFactory;
import simplenlg.phrasespec.AdjPhraseSpec;
import simplenlg.phrasespec.AdvPhraseSpec;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;

/*
    Implements a natural language text generator
    for the quantifier cloud coverage linguistic
    description approach.
 */
public class SkyCoverageGeneratorLevel2 {

    private LabelSet template;
    private LabelSet coverage;
    private Partition cov_partition;
    private double[][] l_description;
    private NLGFactory nlgFactory;

    /*
        Initializes a SkyCoverageGeneratorLevel2 object
        :param template: A cloud coverage template
        :param coverage: A cloud coverage labelset
        :param cov_partition: A cloud coverage quantifier partition set
        :param l_description: A cloud coverage linguistic description
        :return: A new SkyCoverageGeneratorLevel2 object
     */
    public SkyCoverageGeneratorLevel2(LabelSet template, LabelSet coverage, Partition cov_partition, double[][] l_description, NLGFactory nlgFactory) {
        this.template = template;
        this.coverage = coverage;
        this.cov_partition = cov_partition;
        this.l_description = l_description;
        
        this.nlgFactory = nlgFactory;
    }

    /*
        Returns a single natural language text
        description
        :return: A natural language description of the
        cloud coverage variable forecast
     */
    public DocumentElement generate() {

        double[] aux1 = {2.0, 0.0, 0.0};
        double[] aux2 = {1.0, 1.0, 0.0};
        double[] aux3 = {1.0, 0.0, 0.0};

        if (Arrays.equals(l_description[0], aux1)) {

            NPPhraseSpec text = nlgFactory.createNounPhrase(template.getLabels().get("noun").getData());
            text.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
            text.addModifier(nlgFactory.createAdjectivePhrase(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][0]).getName()).getData()));
            text.addModifier(nlgFactory.createPrepositionPhrase(template.getLabels().get("in").getData(), template.getLabels().get("general").getData()));
            text.addModifier(nlgFactory.createPrepositionPhrase(template.getLabels().get("for").getData(), template.getLabels().get("period").getData()));

            if (l_description[2][1] > 0.1 || l_description[2][2] > 0.1) {

                int optional;
                if (l_description[2][1] >= l_description[2][2]) {
                    optional = 1;
                } else {
                    optional = 2;
                }

                AdvPhraseSpec conn = nlgFactory.createAdverbPhrase(template.getLabels().get("although").getData());
                conn.setFeature(Feature.APPOSITIVE, true);

                SPhraseSpec second_part = nlgFactory.createClause(template.getLabels().get("subject2").getData(),
                        template.getLabels().get("verb2").getData());
                second_part.setFeature(Feature.TENSE, Tense.FUTURE);

                second_part.addComplement(nlgFactory.createAdjectivePhrase(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][optional]).getName()).getData()));

                second_part.addPostModifier(nlgFactory.createAdverbPhrase(template.getLabels().get("occasionally").getData()));

                conn.addPostModifier(second_part);

                text.addPostModifier(conn);

            }
            
            DocumentElement salida = nlgFactory.createSentence(text);
            return salida;

        } else if (Arrays.equals(l_description[0], aux2)) {

            SPhraseSpec text = nlgFactory.createClause(template.getLabels().get("subject2").getData(),
                    template.getLabels().get("verb2").getData());
            text.setObject(nlgFactory.createNounPhrase(template.getLabels().get("article").getData(), template.getLabels().get("alternance").getData()));

            NPPhraseSpec state1 = nlgFactory.createNounPhrase(template.getLabels().get("noun").getData());
            state1.setFeature(Feature.NUMBER, NumberAgreement.PLURAL); 
            state1.addModifier(nlgFactory.createAdjectivePhrase(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][0]).getName()).getData()));
            NPPhraseSpec dep = nlgFactory.createNounPhrase(template.getLabels().get("period").getData());
            dep.setFeature(Feature.NUMBER, NumberAgreement.PLURAL); 
            state1.addModifier(dep);

            text.addComplement(nlgFactory.createPrepositionPhrase(template.getLabels().get("of").getData(), state1));

            NPPhraseSpec state2 = nlgFactory.createNounPhrase(template.getLabels().get("other").getData(), template.getLabels().get("period").getData());
            state2.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
            state2.addModifier(nlgFactory.createAdjectivePhrase(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][1]).getName()).getData()));

            text.addComplement(nlgFactory.createPrepositionPhrase(template.getLabels().get("with").getData(), state2));

            if (l_description[2][2] > 0.1) {
                AdvPhraseSpec conn = nlgFactory.createAdverbPhrase(template.getLabels().get("although").getData());
                conn.setFeature(Feature.APPOSITIVE, true);

                SPhraseSpec second_part = nlgFactory.createClause(template.getLabels().get("subject4").getData(),
                        template.getLabels().get("verb_opt").getData());
                second_part.setFeature(Feature.TENSE, Tense.FUTURE);

                second_part.addComplement(nlgFactory.createAdjectivePhrase(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][2]).getName()).getData()));

                second_part.addModifier(nlgFactory.createAdverbPhrase(template.getLabels().get("occasionally").getData()));

                conn.addPostModifier(second_part);

                text.addPostModifier(conn);
            }

            DocumentElement salida = nlgFactory.createSentence(text);
            return salida;

        } else if (Arrays.equals(l_description[0], aux3)) {

            SPhraseSpec text = nlgFactory.createClause(template.getLabels().get("subject3").getData(),
                    template.getLabels().get("verb_opt").getData());
            text.setFeature(Feature.TENSE, Tense.FUTURE);

            NPPhraseSpec state = nlgFactory.createNounPhrase(template.getLabels().get("noun").getData());
            state.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
            state.addModifier(nlgFactory.createAdjectivePhrase(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][0]).getName()).getData()));

            CoordinatedPhraseElement state_list = nlgFactory.createCoordinatedPhrase();
            state_list.setConjunction("or");

            int optional;
            if (l_description[2][1] >= l_description[2][2]) {
                optional = 1;
            } else {
                optional = 2;
            }
            state_list.addCoordinate(nlgFactory.createAdjectivePhrase(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][optional]).getName()).getData()));

            int optional2;
            if (optional == 1) {
                optional2 = 2;
            } else {
                optional2 = 1;
            }
            if (l_description[2][optional2] > 0.1) {
                state_list.addCoordinate(nlgFactory.createAdjectivePhrase(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][optional2]).getName()).getData()));
            }

            NPPhraseSpec moments = nlgFactory.createNounPhrase(template.getLabels().get("moment").getData());
            moments.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);

            state_list.addPostModifier(moments);

            state.addPostModifier(nlgFactory.createPrepositionPhrase(template.getLabels().get("with").getData(), state_list));

            text.setObject(state);

            DocumentElement salida = nlgFactory.createSentence(text);
            return salida;

        } else {

            NPPhraseSpec subject = nlgFactory.createNounPhrase(template.getLabels().get("the").getData(), template.getLabels().get("subject3").getData());
            subject.addPreModifier(nlgFactory.createNounPhrase(template.getLabels().get("noun").getData()));

            SPhraseSpec text = nlgFactory.createClause(subject,
                    template.getLabels().get("verb_opt").getData());
            text.setFeature(Feature.TENSE, Tense.FUTURE);

            AdjPhraseSpec variable = nlgFactory.createAdjectivePhrase(template.getLabels().get("variable").getData());
            variable.addPostModifier(nlgFactory.createAdverbPhrase(template.getLabels().get("very").getData()));

            text.addPostModifier(variable);

            NPPhraseSpec term = nlgFactory.createNounPhrase(template.getLabels().get("the").getData(), template.getLabels().get("term").getData());
            subject.addPreModifier(nlgFactory.createAdjectivePhrase(template.getLabels().get("whole").getData()));

            text.addPostModifier(nlgFactory.createPrepositionPhrase(template.getLabels().get("for").getData(), term));

            DocumentElement salida = nlgFactory.createSentence(text);
            return salida;

        }
    }
}
