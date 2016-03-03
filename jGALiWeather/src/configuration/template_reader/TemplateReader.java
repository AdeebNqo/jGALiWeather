package configuration.template_reader;

import configuration.template_reader.template_components.Case;
import configuration.template_reader.template_components.Option;
import configuration.template_reader.template_components.Period;
import configuration.template_reader.template_components.Space;
import configuration.template_reader.template_components.Static;
import configuration.template_reader.template_components.Time;
import configuration.template_reader.template_components.Variable;
import java.io.File;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/* Implements a language template file reader
   using XML. The XML template is loaded into
   a tree of template objects, which is used
   in the natural language generation process.
 */
public class TemplateReader {

    private Document xmlData;
    private HashMap<String, Template> templates;
    private HashMap<String, LabelSet> labelsets;

    public TemplateReader() {
        this.xmlData = null;
        this.templates = new HashMap();
        this.labelsets = new HashMap();
    }

    /* Parses a XML file into a dictionary of templates and
       a dictionary of label sets

       :param file_name: The string path to the template file
     */
    public void parseFile(String file_name) {
        try {
            File inputFile = new File(file_name);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            xmlData = dBuilder.parse(file_name);
            xmlData.getDocumentElement().normalize();

            this.parseTemplates();
            this.parseLabelSets();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /* Extracts the templates contained in a language template
       file, creating a tree structure of objects. 
     */
    public void parseTemplates() {

        Element t, c, p, vals;
        NodeList ts, cases, parts, part_childs;
        String tname;
        int tid;
        Template new_template;
        Case new_case;
        Static new_static;
        Part new_part;
        Option new_option;
        Variable new_variable;
        Time new_time;
        Period new_period;

        ts = xmlData.getElementsByTagName("template");
        for (int i = 0; i < ts.getLength(); i++) {
            t = (Element) ts.item(i);

            tname = t.getAttribute("name");
            tid = Integer.parseInt(t.getAttribute("id"));

            new_template = new Template(tid, tname);

            cases = t.getElementsByTagName("case");
            for (int j = 0; j < cases.getLength(); j++) {
                c = (Element) cases.item(j);

                new_case = new Case(Integer.parseInt(c.getAttribute("id")));

                parts = c.getElementsByTagName("part");
                for (int k = 0; k < cases.getLength(); k++) {
                    p = (Element) parts.item(k);

                    new_part = new Part();

                    part_childs = p.getChildNodes();
                    for (int l = 0; l < part_childs.getLength(); l++) {
                        vals = (Element) part_childs.item(l);

                        switch (vals.getNodeName()) {
                            case "option":
                                new_option = parseOptions(vals, new_template);
                                new_part.getComponents().add(new_option);
                                new_template.getOptions().put(new_option.getId(), new_option);
                                break;

                            case "static":
                                new_static = new Static(vals.getFirstChild().getTextContent());
                                new_part.getComponents().add(new_static);
                                break;

                            case "variable":
                                new_variable = new Variable(vals.getAttribute("id"));
                                new_part.getComponents().add(new_variable);
                                new_template.getVariables().put(new_variable.getName(), new_variable);
                                break;

                            case "space":
                                new_part.getComponents().add(new Space());
                                break;

                            case "time":
                                new_time = new Time(vals.getAttribute("id"));
                                new_part.getComponents().add(new_time);
                                new_template.getTime_labels().put(new_time.getName(), new_time);
                                break;

                            case "period":
                                new_period = new Period(vals.getAttribute("id"));
                                new_part.getComponents().add(new_period);
                                new_template.getTime_labels().put(new_period.getName(), new_period);
                                break;
                        }
                    }
                    new_case.getParts().add(new_part);
                }
                new_template.getCases().add(new_case);
            }
            templates.put(new_template.getName(), new_template);
        }
    }

    /*
        Extracts the optional elements of a language template

        :param option: The option element from the parsed XML
        tree
        :param template: The template storing the option element

        :return: An Option object
     */
    public Option parseOptions(Element option, Template template) {

        Option new_option = new Option(Integer.parseInt(option.getAttribute("id")));

        NodeList part_childs = option.getChildNodes();
        for (int i = 0; i < part_childs.getLength(); i++) {
            Element c = (Element) part_childs.item(i);

            switch (c.getNodeName()) {
                case "static":
                    Static new_static = new Static(c.getFirstChild().getTextContent());
                    new_option.getComponents().add(new_static);
                    break;

                case "variable":
                    Variable new_variable = new Variable(c.getAttribute("id"));
                    new_option.getComponents().add(new_variable);
                    template.getVariables().put(new_variable.getName(), new_variable);
                    break;

                case "space":
                    new_option.getComponents().add(new Space());
                    break;

                case "time":
                    Time new_time = new Time(c.getAttribute("id"));
                    new_option.getComponents().add(new_time);
                    template.getTime_labels().put(new_time.getName(), new_time);
                    break;

                case "period":
                    Period new_period = new Period(c.getAttribute("id"));
                    new_option.getComponents().add(new_period);
                    template.getTime_labels().put(new_period.getName(), new_period);
                    break;
            }
        }

        return new_option;
    }

    /* Extracts the label sets contained in a language template
        file, returning them organized in a dictionary */
    public void parseLabelSets() {

        NodeList vs, labels;
        Element v, l;
        String name, labelname, labeltext;
        LabelSet labelset;
        Label label;

        vs = xmlData.getElementsByTagName("labelset");
        for (int i = 0; i < vs.getLength(); i++) {
            v = (Element) vs.item(i);

            name = v.getAttribute("name");
            labelset = new LabelSet(name);

            labels = v.getElementsByTagName("label");
            for (int j = 0; j < labels.getLength(); j++) {
                l = (Element) labels.item(j);

                labelname = l.getAttribute("name");
                labeltext = l.getFirstChild().getTextContent();
                label = new Label(labelname, labeltext);
                labelset.getLabels().put(label.getName(), label);
            }
            labelsets.put(labelset.getName(), labelset);
        }
    }
}
