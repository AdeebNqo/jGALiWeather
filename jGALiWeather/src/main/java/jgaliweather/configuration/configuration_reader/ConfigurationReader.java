package jgaliweather.configuration.configuration_reader;

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
    private HashMap<String, DatabaseConfiguration> db_data;

    public ConfigurationReader() {
        this.xmlData = null;
        this.inpaths = new HashMap();
        this.db_data = new HashMap();
    }

    public ConfigurationReader(Document xmlData, HashMap<String, String> inpaths, HashMap<String, DatabaseConfiguration> db_data) {
        this.xmlData = xmlData;
        this.inpaths = inpaths;
        this.db_data = db_data;
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

    public HashMap<String, DatabaseConfiguration> getDb_data() {
        return db_data;
    }

    public void setDb_data(HashMap<String, DatabaseConfiguration> db_data) {
        this.db_data = db_data;
    }

    public Document getXmlData() {
        return xmlData;
    }

    /* Parses a XML configuration file 
    
       :param file_name: The path string for the configuration file
     */
    public void parseFile(String file_name) throws Exception {

        File inputFile = new File(file_name);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        xmlData = dBuilder.parse(inputFile);
        xmlData.getDocumentElement().normalize();

        this.setPaths();
        this.setDatabaseData();

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
}
