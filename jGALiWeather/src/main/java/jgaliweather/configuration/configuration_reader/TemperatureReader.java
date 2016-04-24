package jgaliweather.configuration.configuration_reader;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import jgaliweather.data.data_structures.HistoricalTemperature;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TemperatureReader {

    private Document xmlData;

    public TemperatureReader() {
    }

    public void parseFile(String file_name) throws Exception {

        File inputFile = new File(file_name);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        xmlData = dBuilder.parse(inputFile);
        xmlData.getDocumentElement().normalize();

    }

    public HistoricalTemperature retrieveClimaticDataForLocation(int lid, int month) throws Exception {

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression max = xpath.compile("//row[@lnConcello='" + lid + "' and @Mes='" + month + "' and @lnParametro='2']");
        XPathExpression min = xpath.compile("//row[@lnConcello='" + lid + "' and @Mes='" + month + "' and @lnParametro='3']");

        NodeList nl = (NodeList) max.evaluate(xmlData, XPathConstants.NODESET);
        Element path = (Element) nl.item(0);
        double avgMax = Double.parseDouble(path.getAttribute("Media"));
        double dtMax = Double.parseDouble(path.getAttribute("DTipica"));

        nl = (NodeList) min.evaluate(xmlData, XPathConstants.NODESET);
        path = (Element) nl.item(0);
        double avgMin = Double.parseDouble(path.getAttribute("Media"));
        double dtMin = Double.parseDouble(path.getAttribute("DTipica"));

        return new HistoricalTemperature(avgMax, dtMax, avgMin, dtMin);

    }

}
