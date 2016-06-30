package jgaliweather.nlg.nlg_generators;

import java.util.Arrays;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.template_reader.LabelSet;
import simplenlg.features.Feature;
import simplenlg.features.NumberAgreement;
import simplenlg.features.Tense;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.NLGElement;
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
    public SkyCoverageGeneratorLevel2(LabelSet coverage, Partition cov_partition, double[][] l_description, NLGFactory nlgFactory) {
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
    public NLGElement generate() {

        double[] aux1 = {2.0, 0.0, 0.0};
        double[] aux2 = {1.0, 1.0, 0.0};
        double[] aux3 = {1.0, 0.0, 0.0};

        if (Arrays.equals(l_description[0], aux1)) {

            NPPhraseSpec text = nlgFactory.createNounPhrase("sky");
            text.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
            text.addModifier(nlgFactory.createAdjectivePhrase(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][0]).getName()).getData()));
            text.addModifier(nlgFactory.createPrepositionPhrase("in", "general"));
            text.addModifier(nlgFactory.createPrepositionPhrase("for", "the next few days"));

            if (l_description[2][1] > 0.1 || l_description[2][2] > 0.1) {

                int optional;
                if (l_description[2][1] >= l_description[2][2]) {
                    optional = 1;
                } else {
                    optional = 2;
                }

                AdvPhraseSpec conn = nlgFactory.createAdverbPhrase("although");
                conn.setFeature(Feature.APPOSITIVE, true);

                SPhraseSpec second_part = nlgFactory.createClause("it", "be");
                second_part.setFeature(Feature.TENSE, Tense.FUTURE);

                second_part.addComplement(nlgFactory.createAdjectivePhrase(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][optional]).getName()).getData()));
                
                second_part.addPreModifier(nlgFactory.createAdverbPhrase("occasionally"));

                conn.addPostModifier(second_part);

                text.addPostModifier(conn);

            }

            return text;

        } else if (Arrays.equals(l_description[0], aux2)) {

            SPhraseSpec text = nlgFactory.createClause("it", "be expected");
            text.setObject(nlgFactory.createNounPhrase("a", "alternance"));

            NPPhraseSpec state1 = nlgFactory.createNounPhrase("sky");
            state1.setFeature(Feature.NUMBER, NumberAgreement.PLURAL); 
            state1.addModifier(nlgFactory.createAdjectivePhrase(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][0]).getName()).getData()));
            NPPhraseSpec dep = nlgFactory.createNounPhrase("period");
            dep.setFeature(Feature.NUMBER, NumberAgreement.PLURAL); 
            state1.addModifier(dep);

            text.addComplement(nlgFactory.createPrepositionPhrase("of", state1));

            NPPhraseSpec state2 = nlgFactory.createNounPhrase("other", "period");
            state2.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
            state2.addModifier(nlgFactory.createAdjectivePhrase(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][1]).getName()).getData()));

            text.addComplement(nlgFactory.createPrepositionPhrase("with", state2));

            if (l_description[2][2] > 0.1) {
                AdvPhraseSpec conn = nlgFactory.createAdverbPhrase("although");
                conn.setFeature(Feature.APPOSITIVE, true);

                SPhraseSpec second_part = nlgFactory.createClause("they", "be");
                second_part.setFeature(Feature.TENSE, Tense.FUTURE);
                second_part.addComplement(nlgFactory.createAdjectivePhrase(coverage.getLabels().get(cov_partition.getSets().get((int) l_description[1][2]).getName()).getData()));
                
                second_part.addPreModifier(nlgFactory.createAdverbPhrase("occasionally"));

                conn.addPostModifier(second_part);

                text.addPostModifier(conn);
            }

            return text;

        } else if (Arrays.equals(l_description[0], aux3)) {

            SPhraseSpec text = nlgFactory.createClause("there", "be");
            text.setFeature(Feature.TENSE, Tense.FUTURE);

            NPPhraseSpec state = nlgFactory.createNounPhrase("sky");
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

            NPPhraseSpec moments = nlgFactory.createNounPhrase("moment");
            moments.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);

            state_list.addPostModifier(moments);

            state.addPostModifier(nlgFactory.createPrepositionPhrase("with", state_list));

            text.setObject(state);

            return text;

        } else {

            NPPhraseSpec subject = nlgFactory.createNounPhrase("the", "state");
            subject.addPreModifier(nlgFactory.createNounPhrase("sky"));

            SPhraseSpec text = nlgFactory.createClause(subject, "be");
            text.setFeature(Feature.TENSE, Tense.FUTURE);

            AdjPhraseSpec variable = nlgFactory.createAdjectivePhrase("variable");
            variable.addPreModifier(nlgFactory.createAdverbPhrase("very"));

            text.addPostModifier(variable);

            NPPhraseSpec term = nlgFactory.createNounPhrase("the", "term");
            term.addPreModifier(nlgFactory.createAdjectivePhrase("whole"));

            text.addPostModifier(nlgFactory.createPrepositionPhrase("for", term));

            return text;

        }
    }
}
