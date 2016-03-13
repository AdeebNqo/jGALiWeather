package jgaliweather.configuration.configuration_reader;

import jgaliweather.data.data_structures.Language;
import java.io.File;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/* This class implements a reader class for XML configuration files. */
public class ConfigurationReader {

    private Document xmlData;
    private HashMap<String, String> inpaths;
    private HashMap<String, String> outpaths;
    private HashMap<String, DatabaseConfiguration> db_data;
    private LanguageConfiguration lng_data;

    public ConfigurationReader() {
        this.xmlData = null;
        this.inpaths = new HashMap();
        this.outpaths = new HashMap();
        this.db_data = new HashMap();
        this.lng_data = new LanguageConfiguration();
    }

    public ConfigurationReader(Document xmlData, HashMap<String, String> inpaths, HashMap<String, String> outpaths, HashMap<String, DatabaseConfiguration> db_data, LanguageConfiguration lng_data) {
        this.xmlData = xmlData;
        this.inpaths = inpaths;
        this.outpaths = outpaths;
        this.db_data = db_data;
        this.lng_data = lng_data;
    }

    public void setXmlData(Document xmlData) {
        this.xmlData = xmlData;
    }

    public HashMap<String, String> getInpaths() {
        return inpaths;
    }

    public void setInpaths(HashMap<String, String> inpaths) {
        this.inpaths = inpaths;
    }

    public HashMap<String, String> getOutpaths() {
        return outpaths;
    }

    public void setOutpaths(HashMap<String, String> outpaths) {
        this.outpaths = outpaths;
    }

    public HashMap<String, DatabaseConfiguration> getDb_data() {
        return db_data;
    }

    public void setDb_data(HashMap<String, DatabaseConfiguration> db_data) {
        this.db_data = db_data;
    }

    public LanguageConfiguration getLng_data() {
        return lng_data;
    }

    public void setLng_data(LanguageConfiguration lng_data) {
        this.lng_data = lng_data;
    }

    public Document getXmlData() {
        return xmlData;
    }

    /* Parses a XML configuration file 
    
       :param file_name: The path string for the configuration file
     */
    public void parseFile(String file_name) {
        try {
            File inputFile = new File(file_name);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            xmlData = dBuilder.parse(inputFile);
            xmlData.getDocumentElement().normalize();

            this.setPaths();
            this.setDatabaseData();
            this.setLanguageData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /* Loads input and output directory paths from the configuration parsed tree */
    private void setPaths() {
        String name, string_path;
        Element path;

        NodeList inroutes = xmlData.getElementsByTagName("inpath");
        for (int i = 0; i < inroutes.getLength(); i++) {
            path = (Element) inroutes.item(i);

            name = path.getAttribute("name");
            string_path = path.getFirstChild().getTextContent();
            inpaths.put(name, string_path);
        }

        NodeList outroutes = xmlData.getElementsByTagName("outpath");
        for (int i = 0; i < outroutes.getLength(); i++) {
            path = (Element) outroutes.item(i);

            name = path.getAttribute("name");
            string_path = path.getFirstChild().getTextContent();
            outpaths.put(name, string_path);
        }
    }

    /* Loads database configuration data */
    private void setDatabaseData() {
        String name, driver, host, dbname, user, pwd;
        Element path;

        NodeList dbs = xmlData.getElementsByTagName("database");
        for (int i = 0; i < dbs.getLength(); i++) {
            path = (Element) dbs.item(i);

            name = path.getAttribute("name");
            driver = path.getElementsByTagName("driver").item(0).getFirstChild().getTextContent();
            host = path.getElementsByTagName("host").item(0).getFirstChild().getTextContent();
            dbname = path.getElementsByTagName("dbname").item(0).getFirstChild().getTextContent();
            user = path.getElementsByTagName("user").item(0).getFirstChild().getTextContent();
            pwd = path.getElementsByTagName("pass").item(0).getFirstChild().getTextContent();

            db_data.put(name, new DatabaseConfiguration(name, driver, host, dbname, user, pwd));
        }
    }

    /* Loads language template files configuration data */
    private void setLanguageData() {
        String name, path;
        Element el;

        NodeList lngs = xmlData.getElementsByTagName("language");
        for (int i = 0; i < lngs.getLength(); i++) {
            el = (Element) lngs.item(i);

            name = el.getAttribute("name");

            NodeList alternatives = el.getElementsByTagName("alternative");
            for (int j = 0; j < alternatives.getLength(); j++) {
                path = alternatives.item(j).getFirstChild().getTextContent();

                lng_data.getLanguages().add(new Language(name, path));
            }
        }
    }
}