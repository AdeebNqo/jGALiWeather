package jgaliweather.configuration.partition_reader;

import fuzzy4j.sets.TrapezoidalFunction;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/* 
    Implements a reader which loads a XML file defining crisp, fuzzy
    and object set partitions used by the linguistic description
    operators.
 */
public class PartitionReader {

    private Document xmlData;
    private HashMap<String, Partition> partitions;

    public PartitionReader() {
        this.xmlData = null;
        this.partitions = new HashMap();
    }

    public Document getXmlData() {
        return xmlData;
    }

    public void setXmlData(Document xmlData) {
        this.xmlData = xmlData;
    }

    public HashMap<String, Partition> getPartitions() {
        return partitions;
    }

    public void setPartitions(HashMap<String, Partition> partitions) {
        this.partitions = partitions;
    }

    /*  
        Parses a partition definition XML file

        :param file_name: The path string for the partition
        definition XML file
     */
    public void parseFile(String file_name) throws Exception {
        File inputFile = new File(file_name);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        xmlData = dBuilder.parse(inputFile);
        xmlData.getDocumentElement().normalize();

        String name;
        Partition new_partition;
        Set nset;
        Element v, c;
        NodeList vs = xmlData.getElementsByTagName("partition");

        for (int i = 0; i < vs.getLength(); i++) {
            v = (Element) vs.item(i);

            name = v.getAttribute("name");
            new_partition = new Partition(name);

            NodeList childs = v.getElementsByTagName("*");
            for (int j = 0; j < childs.getLength(); j++) {
                c = (Element) childs.item(j);

                switch (c.getNodeName()) {
                    case "CrispInterval":
                        nset = parseCrispInterval(c);
                        new_partition.getSets().add(nset);
                        break;

                    case "ObjectSet":
                        nset = parseObjectSet(c);
                        new_partition.getSets().add(nset);
                        break;

                    case "FuzzySet":
                        nset = parseFuzzySet(c);
                        new_partition.getSets().add(nset);
                        break;
                }
            }
            partitions.put(new_partition.getName(), new_partition);
        }

    }

    public CrispInterval parseCrispInterval(Element c) {

        float a = Float.parseFloat(c.getAttribute("a"));
        float b = Float.parseFloat(c.getAttribute("b"));
        String name = c.getAttribute("name");
        String mode = c.getAttribute("mode");

        return new CrispInterval(a, b, name, mode);
    }

    public ObjectSet parseObjectSet(Element c) {

        ArrayList<Integer> objects = new ArrayList();
        String name = c.getAttribute("name");

        StringTokenizer st = new StringTokenizer(c.getFirstChild().getTextContent());
        while (st.hasMoreTokens()) {
            objects.add(Integer.parseInt(st.nextToken()));
        }

        return new ObjectSet(name, objects);
    }

    public FuzzySet parseFuzzySet(Element c) {

        double m1 = Double.parseDouble(c.getAttribute("m1"));
        double m2 = Double.parseDouble(c.getAttribute("m2"));
        double alpha = Double.parseDouble(c.getAttribute("alpha"));
        double beta = Double.parseDouble(c.getAttribute("beta"));
        String name = c.getAttribute("name");

        return new FuzzySet(name, new TrapezoidalFunction(m1 - alpha, m1, m2, m2 + beta));
    }
}
