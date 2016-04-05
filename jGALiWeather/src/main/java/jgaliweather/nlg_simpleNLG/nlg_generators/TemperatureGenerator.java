package jgaliweather.nlg_simpleNLG.nlg_generators;

import java.util.StringTokenizer;
import jgaliweather.configuration.partition_reader.Partition;
import jgaliweather.configuration.template_reader.LabelSet;
import simplenlg.features.Feature;
import simplenlg.features.Form;
import simplenlg.features.NumberAgreement;
import simplenlg.features.Tense;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.AdjPhraseSpec;
import simplenlg.phrasespec.AdvPhraseSpec;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.english.Realiser;

/*
    Implements a natural language text generator
    for the temperature variable.
 */
public class TemperatureGenerator {

    private LabelSet template;
    private LabelSet cl_var;
    private LabelSet t_var;
    private LabelSet var_var;
    private Partition var_part;
    private String cl_string;
    private String t_string;
    private String var_string;
    private SPhraseSpec text;
    private boolean temp1;
    private boolean temp5;
    private NLGFactory nlgFactory;
    private Realiser realiser;

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
    public TemperatureGenerator(LabelSet template, LabelSet cl_var, LabelSet t_var, LabelSet var_var, Partition var_part, String cl_string, String t_string, String var_string) {
        this.template = template;
        this.cl_var = cl_var;
        this.t_var = t_var;
        this.var_var = var_var;
        this.var_part = var_part;
        this.cl_string = cl_string;
        this.t_string = t_string;
        this.var_string = var_string;
        this.temp1 = false;
        this.temp5 = false;

        Lexicon lexicon = Lexicon.getDefaultLexicon();
        this.nlgFactory = new NLGFactory(lexicon);
        this.realiser = new Realiser(lexicon);
    }

    /*
        Parses the intermediate linguistic descriptions
        and returns a single natural language text
        description
        :return: A natural language description of the
        temperature variable forecast
     */
    public String parseAndGenerate() {

        SPhraseSpec second_part = null;
        AdvPhraseSpec aux = null;

        text = nlgFactory.createClause(template.getLabels().get("noun").getData(), template.getLabels().get("verb").getData());
        text.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
        text.setFeature(Feature.TENSE, Tense.FUTURE);

        StringTokenizer cl_st = new StringTokenizer(cl_string);
        if (cl_st.countTokens() == 1) {
            climateAlternative1(cl_st.nextToken());
        } else if (cl_st.countTokens() == 2) {
            climateAlternative2(cl_st.nextToken(), cl_st.nextToken());
        }

        StringTokenizer t_st = new StringTokenizer(t_string);
        if (t_st.countTokens() == 1) {
            second_part = temperatureAlternative1(t_st.nextToken());
        } else if (t_st.countTokens() == 2) {
            second_part = temperatureAlternative2(t_st.nextToken(), t_st.nextToken());
        }

        StringTokenizer var_st = new StringTokenizer(var_string);
        if (var_st.countTokens() == 1) {
            aux = variabilityAlternative1(var_st.nextToken());
        } else if (var_st.countTokens() == 2) {
            aux = variabilityAlternative2(var_st.nextToken(), var_st.nextToken());
        }
        if(second_part != null){
            if(aux != null) {
                second_part.addPostModifier(second_part);
            }
            second_part.setFeature(Feature.APPOSITIVE, true);
            text.addPostModifier(second_part);
        }

        return realiser.realiseSentence(text);
    }

    private void climateAlternative1(String token) {

        text.addPostModifier(nlgFactory.createAdjectivePhrase(cl_var.getLabels().get(token).getData()));

        NPPhraseSpec period = nlgFactory.createNounPhrase(template.getLabels().get("this").getData(), template.getLabels().get("period").getData());
        period.addModifier(nlgFactory.createPrepositionPhrase(template.getLabels().get("of").getData(),
                nlgFactory.createNounPhrase(template.getLabels().get("the").getData(), template.getLabels().get("year").getData())));

        text.addPostModifier(nlgFactory.createPrepositionPhrase(template.getLabels().get("for").getData(), period));

    }

    private void climateAlternative2(String token1, String token2) {

        CoordinatedPhraseElement climate_list = nlgFactory.createCoordinatedPhrase();

        AdjPhraseSpec min = nlgFactory.createAdjectivePhrase(cl_var.getLabels().get(token2).getData());
        AdjPhraseSpec max = nlgFactory.createAdjectivePhrase(cl_var.getLabels().get(token1).getData());

        NPPhraseSpec minimums = nlgFactory.createNounPhrase(template.getLabels().get("minimum").getData());
        minimums.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
        NPPhraseSpec maximums = nlgFactory.createNounPhrase(template.getLabels().get("maximum").getData());
        maximums.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);

        min.addPostModifier(nlgFactory.createPrepositionPhrase(template.getLabels().get("for").getData(), minimums));
        max.addPostModifier(nlgFactory.createPrepositionPhrase(template.getLabels().get("for").getData(), maximums));

        climate_list.addCoordinate(min);
        climate_list.addCoordinate(max);

        SPhraseSpec option2 = nlgFactory.createClause(climate_list, template.getLabels().get("compare").getData());
        option2.setFeature(Feature.FORM, Form.PAST_PARTICIPLE);

        NPPhraseSpec expected = nlgFactory.createNounPhrase(template.getLabels().get("the").getData(), template.getLabels().get("expected").getData());

        NPPhraseSpec aux = nlgFactory.createNounPhrase(template.getLabels().get("this").getData(), template.getLabels().get("time").getData());
        aux.addModifier(nlgFactory.createPrepositionPhrase(nlgFactory.createPrepositionPhrase(template.getLabels().get("of").getData(),
                nlgFactory.createNounPhrase(template.getLabels().get("the").getData(), template.getLabels().get("year").getData()))));

        expected.addPostModifier(nlgFactory.createPrepositionPhrase(template.getLabels().get("for").getData(), aux));

        option2.addPostModifier(nlgFactory.createPrepositionPhrase(template.getLabels().get("to").getData(), expected));

        text.addPostModifier(option2);

    }

    private SPhraseSpec temperatureAlternative1(String token) {

        SPhraseSpec option1 = nlgFactory.createClause(template.getLabels().get("which").getData(), template.getLabels().get("be").getData());
        option1.setFeature(Feature.TENSE, Tense.FUTURE);
        option1.addPreModifier(nlgFactory.createAdverbPhrase(template.getLabels().get("globally").getData()));
        option1.addPostModifier(nlgFactory.createPrepositionPhrase(t_var.getLabels().get(token).getData()));
        
        temp1 = true;

        return option1;

    }

    private SPhraseSpec temperatureAlternative2(String token1, String token2) {

        CoordinatedPhraseElement temp_list = nlgFactory.createCoordinatedPhrase();

        NPPhraseSpec minimums = nlgFactory.createNounPhrase(template.getLabels().get("minimum").getData());
        minimums.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
        NPPhraseSpec maximums = nlgFactory.createNounPhrase(template.getLabels().get("maximum").getData());
        maximums.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);

        PPPhraseSpec minV = nlgFactory.createPrepositionPhrase(template.getLabels().get("with").getData(), minimums);
        minV.addPostModifier(t_var.getLabels().get(token2).getData());

        NPPhraseSpec maxV = nlgFactory.createNounPhrase(maximums);
        maxV.addPostModifier(t_var.getLabels().get(token1).getData());

        temp_list.addCoordinate(minV);
        temp_list.addCoordinate(maxV);

        SPhraseSpec option2 = nlgFactory.createClause();

        option2.addPostModifier(temp_list);

        temp5 = true;
        
        return option2;

    }

    private AdvPhraseSpec variabilityAlternative1(String token) {

        AdvPhraseSpec conn = nlgFactory.createAdverbPhrase(template.getLabels().get("although").getData());
        
        if (!token.equals("C")) {
            if (temp1) {       
                
                conn.addPostModifier(nlgFactory.createClause(template.getLabels().get("they").getData(), var_var.getLabels().get(token).getData()));
                
            } else {      
                
                SPhraseSpec aux = nlgFactory.createClause(template.getLabels().get("they").getData(), var_var.getLabels().get(token).getData());
                
                PPPhraseSpec aux1 = nlgFactory.createPrepositionPhrase(template.getLabels().get("despite").getData(),
                        nlgFactory.createClause(template.getLabels().get("they").getData(), var_var.getLabels().get(token).getData()));
                aux1.setFeature(Feature.APPOSITIVE, true);
                
                aux.addPostModifier(aux1);
                
                conn.addPostModifier(aux);
                
            }
        }
        
        return conn;
        
    }

    private AdvPhraseSpec variabilityAlternative2(String token1, String token2) {
        
        AdvPhraseSpec conn = nlgFactory.createAdverbPhrase(template.getLabels().get("although").getData());
        
        if (temp1) {
            if (!token1.equals("C")) {
                
                conn.addPostModifier(nlgFactory.createClause(template.getLabels().get("they").getData(), var_var.getLabels().get(token1).getData()));
                
            } else {
                
                conn.addPostModifier(nlgFactory.createClause(template.getLabels().get("they").getData(), var_var.getLabels().get(token2).getData()));
                
            }
        } else {
            if (!token2.equals("C") && temp5) {
                
                SPhraseSpec aux = nlgFactory.createClause(template.getLabels().get("they").getData(), var_var.getLabels().get(token2).getData());
                
            }
            if (!token1.equals("C") && temp5) {
                
                PPPhraseSpec aux1 = nlgFactory.createPrepositionPhrase(template.getLabels().get("despite").getData(),
                        nlgFactory.createClause(template.getLabels().get("they").getData(), var_var.getLabels().get(token1).getData()));
                aux1.setFeature(Feature.APPOSITIVE, true);
                
                conn = nlgFactory.createAdverbPhrase();
                
                conn.addPostModifier(aux1);
            }
        }
        
        return conn;
    }
}
