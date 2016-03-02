package configuration;

import java.io.File;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/* Implements a class that reads variable configuration files. */
public class VariableReader {

    private Document xmlData;
    private HashMap<String, XMLVariable> variables;

    public VariableReader() {
        xmlData = null;
        variables = new HashMap();
    }

    /* Parses a variable configuration file

       :param file_name: The path string to the variable configuration file
     */
    public void parseFile(String file_name) {
        try {
            String name, valid_lengths;
            int start, end;
            Element v;

            File inputFile = new File(file_name);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            xmlData = dBuilder.parse(file_name);
            xmlData.getDocumentElement().normalize();

            NodeList vs = xmlData.getElementsByTagName("variable");
            for (int i = 0; i < vs.getLength(); i++) {
                v = (Element) vs.item(i);

                name = v.getElementsByTagName("name").item(0).getFirstChild().getTextContent();
                start = Integer.parseInt(v.getElementsByTagName("start").item(0).getFirstChild().getTextContent());
                end = Integer.parseInt(v.getElementsByTagName("end").item(0).getFirstChild().getTextContent());
                valid_lengths = v.getElementsByTagName("valid_lengths").item(0).getFirstChild().getTextContent();

                variables.put(name, new XMLVariable(name, start, end, valid_lengths));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getDataAsString() {
        return "Number of languages: " + variables.size();
    }
}
