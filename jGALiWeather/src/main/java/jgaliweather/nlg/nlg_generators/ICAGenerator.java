package jgaliweather.nlg.nlg_generators;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import jgaliweather.configuration.template_reader.LabelSet;
import simplenlg.features.Feature;
import simplenlg.features.Form;
import simplenlg.features.Tense;
import simplenlg.framework.NLGFactory;
import simplenlg.framework.PhraseElement;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.AdvPhraseSpec;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;
import simplenlg.realiser.english.Realiser;

/*
    Implements a natural language text generator
    for the air quality state variable.
 */
public class ICAGenerator {

    private final String IMPROVE = "improve";
    private final String CHANGE = "change";

    private LabelSet ica_template;
    private String ica_expr;
    private ArrayList<String> weather_info;
    private SPhraseSpec text;
    private PhraseElement nuance;
    private String change_label;
    private NLGFactory nlgFactory;
    private Realiser realiser;

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
        this.nuance = null;
        this.text = null;
        this.change_label = null;

        Lexicon lexicon = Lexicon.getDefaultLexicon();
        this.nlgFactory = new NLGFactory(lexicon);
        this.realiser = new Realiser(lexicon);
    }

    /*
        Parses the intermediate linguistic descriptions
        and returns a single natural language text
        description

        :return: A natural language description of the
        air quality state forecast
     */
    public String generate() {

        String final_text = null;
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
            final_text = realiser.realiseSentence(finishDescription());
        }

        return final_text;
    }

    private void improving() {
        change_label = IMPROVE;
    }

    private void change() {
        change_label = CHANGE;
    }

    private void stable(String ica) {

        VPPhraseSpec verb = nlgFactory.createVerbPhrase("be");
        VPPhraseSpec verb_aux = nlgFactory.createVerbPhrase("remain");
        verb_aux.setFeature(Feature.FORM, Form.INFINITIVE);
        verb.addComplement(verb_aux);

        text = nlgFactory.createClause("it", verb);
        text.setFeature(Feature.TENSE, Tense.FUTURE);
        text.addPostModifier(nlgFactory.createAdjectivePhrase(ica_template.getLabels().get(ica).getData()));
        text.addPostModifier(nlgFactory.createPrepositionPhrase("in", "general"));

        nuance = weatherNuance(0, ica, change_label);
    }

    private void mchange(String ica) {

        text = nlgFactory.createClause("it", "be");
        text.setFeature(Feature.TENSE, Tense.FUTURE);
        text.addComplement(nlgFactory.createAdjectivePhrase("changeable"));

        AdvPhraseSpec conn = nlgFactory.createAdverbPhrase("although");

        VPPhraseSpec a = nlgFactory.createVerbPhrase("be");
        VPPhraseSpec b = nlgFactory.createVerbPhrase("expected");
        b.setFeature(Feature.FORM, Form.PAST_PARTICIPLE);
        a.addComplement(b);

        SPhraseSpec second_part = nlgFactory.createClause("it", a);

        VPPhraseSpec aux = nlgFactory.createVerbPhrase(ica_template.getLabels().get(change_label).getData());
        aux.setFeature(Feature.FORM, Form.INFINITIVE);
        aux.addPostModifier(nlgFactory.createPrepositionPhrase("to", nlgFactory.createAdjectivePhrase(ica_template.getLabels().get(ica).getData())));

        second_part.addComplement(aux);

        conn.addPostModifier(second_part);

        text.addComplement(conn);

        nuance = weatherNuance(2, ica, change_label);

    }

    private void schange(String ica) {

        VPPhraseSpec verb = nlgFactory.createVerbPhrase("be");
        VPPhraseSpec verb_aux = nlgFactory.createVerbPhrase("expected");
        verb_aux.setFeature(Feature.FORM, Form.PAST_PARTICIPLE);
        verb.addComplement(verb_aux);

        text = nlgFactory.createClause("it", verb);

        VPPhraseSpec aux = nlgFactory.createVerbPhrase(ica_template.getLabels().get(change_label).getData());
        aux.setFeature(Feature.FORM, Form.INFINITIVE);
        aux.addPostModifier(nlgFactory.createPrepositionPhrase("to", nlgFactory.createAdjectivePhrase(ica_template.getLabels().get(ica).getData())));

        text.addComplement(aux);

        nuance = weatherNuance(1, ica, change_label);
    }

    private void echange(String ica) {

        VPPhraseSpec verb = nlgFactory.createVerbPhrase("be");
        VPPhraseSpec verb_aux = nlgFactory.createVerbPhrase("expected");
        verb_aux.setFeature(Feature.FORM, Form.PAST_PARTICIPLE);
        verb.addComplement(verb_aux);

        text = nlgFactory.createClause("it", verb);

        VPPhraseSpec aux = nlgFactory.createVerbPhrase(ica_template.getLabels().get(change_label).getData());
        aux.setFeature(Feature.FORM, Form.INFINITIVE);
        aux.addPostModifier(nlgFactory.createPrepositionPhrase("to", nlgFactory.createAdjectivePhrase(ica_template.getLabels().get(ica).getData())));

        text.addComplement(aux);

        nuance = weatherNuance(2, ica, change_label);
    }

    private void pchange(String ica) {

        VPPhraseSpec verb = nlgFactory.createVerbPhrase("be");
        VPPhraseSpec verb_aux = nlgFactory.createVerbPhrase("expected");
        verb_aux.setFeature(Feature.FORM, Form.PAST_PARTICIPLE);
        verb.addComplement(verb_aux);

        text = nlgFactory.createClause("it", verb);

        VPPhraseSpec aux = nlgFactory.createVerbPhrase(ica_template.getLabels().get(change_label).getData());
        aux.setFeature(Feature.FORM, Form.INFINITIVE);
        aux.addPostModifier(nlgFactory.createAdverbPhrase("progressively"));
        aux.addPostModifier(nlgFactory.createPrepositionPhrase("to", nlgFactory.createAdjectivePhrase(ica_template.getLabels().get(ica).getData())));

        text.addComplement(aux);

        nuance = weatherNuance(2, ica, change_label);
    }

    private NPPhraseSpec finishDescription() {

        text.setFeature(Feature.APPOSITIVE, true);

        NPPhraseSpec final_text = nlgFactory.createNounPhrase("air quality state");
        final_text.addPreModifier(nlgFactory.createPrepositionPhrase("with respect to"));

        final_text.addPostModifier(text);
        if (nuance != null) {
            final_text.addPostModifier(nuance);
        }

        return final_text;
    }

    private PhraseElement weatherNuance(int w_index, String ica_label, String trend) {

        Random randomGenerator = new Random();
        int interval = randomGenerator.nextInt(2);

        if (weather_info != null) {
            if (ica_label.equals("G")) {

                VPPhraseSpec text_nuance = nlgFactory.createVerbPhrase("favor");
                text_nuance.setFeature(Feature.FORM, Form.PAST_PARTICIPLE);

                if (weather_info.get(w_index).equals("R")) {

                    text_nuance.addPostModifier(nlgFactory.createPrepositionPhrase("by", nlgFactory.createNounPhrase(ica_template.getLabels().get("nuance_rain").getData())));
                    text_nuance.addPostModifier(nlgFactory.createPrepositionPhrase("during",
                            nlgFactory.createNounPhrase(ica_template.getLabels().get("interval" + interval).getData())));

                    return text_nuance;

                } else if (weather_info.get(w_index).equals("RW")) {

                    text_nuance.addPostModifier(nlgFactory.createPrepositionPhrase("by", nlgFactory.createNounPhrase(ica_template.getLabels().get("nuance_rainwind").getData())));
                    text_nuance.addPostModifier(nlgFactory.createPrepositionPhrase("for",
                            nlgFactory.createNounPhrase(ica_template.getLabels().get("interval" + interval).getData())));

                    return text_nuance;

                } else if (weather_info.get(w_index).equals("W")) {

                    text_nuance.addPostModifier(nlgFactory.createPrepositionPhrase("by", nlgFactory.createNounPhrase(ica_template.getLabels().get("nuance_wind").getData())));
                    text_nuance.addPostModifier(nlgFactory.createPrepositionPhrase("during",
                            nlgFactory.createNounPhrase(ica_template.getLabels().get("interval" + interval).getData())));

                    return text_nuance;

                }
            } else if ((ica_label.equals("I") || ica_label.equals("B") || ica_label.equals("H")) && (trend == null || !trend.equals(IMPROVE))) {

                PPPhraseSpec text_nuance = nlgFactory.createPrepositionPhrase("because of");
                text_nuance.setFeature(Feature.FORM, Form.INFINITIVE);

                if (weather_info.get(w_index).equals("S")) {

                    text_nuance.addPostModifier(nlgFactory.createNounPhrase(ica_template.getLabels().get("nuance_sunnowind").getData()));
                    text_nuance.addPostModifier(nlgFactory.createPrepositionPhrase("in",
                            nlgFactory.createNounPhrase(ica_template.getLabels().get("interval" + interval).getData())));

                    return text_nuance;

                } else if (weather_info.get(w_index).equals("D")) {

                    text_nuance.addPostModifier(nlgFactory.createNounPhrase(ica_template.getLabels().get("nuance_dry").getData()));
                    text_nuance.addPostModifier(nlgFactory.createPrepositionPhrase("during",
                            nlgFactory.createNounPhrase(ica_template.getLabels().get("interval" + interval).getData())));

                    return text_nuance;
                }
            }
        }
        return null;
    }
}
