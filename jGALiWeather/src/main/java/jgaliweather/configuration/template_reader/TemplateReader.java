package jgaliweather.configuration.template_reader;

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
    private HashMap<String, LabelSet> labelsets;

    public TemplateReader() {
        this.xmlData = null;
        this.labelsets = new HashMap();
    }

    public Document getXmlData() {
        return xmlData;
    }

    public void setXmlData(Document xmlData) {
        this.xmlData = xmlData;
    }

    public HashMap<String, LabelSet> getLabelsets() {
        return labelsets;
    }

    public void setLabelsets(HashMap<String, LabelSet> labelsets) {
        this.labelsets = labelsets;
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
            xmlData = dBuilder.parse(inputFile);
            xmlData.getDocumentElement().normalize();

            this.parseLabelSets();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
