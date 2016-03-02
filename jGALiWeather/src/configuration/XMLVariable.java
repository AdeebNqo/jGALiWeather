package configuration;

import java.util.ArrayList;
import java.util.StringTokenizer;

/* Defines an input variable, loaded from a
    XML variable configuration file.
 */
public class XMLVariable {

    private final String name;
    private final int start;
    private final int end;
    private final ArrayList<String> valid_lengths;

    /*  Initializes a new XMLVariable object

        :param name: Name of the variable
        :param start: Initial numeric code which defines valid values for the variable
        :param end: Ending numeric code which defines valid values for the variable
        :param valid_lengths: List with valid data lengths for the variable

        :return: A new XMLVariable object
     */
    public XMLVariable(String name, int start, int end, String valid_lengths) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.valid_lengths = new ArrayList();
        
        StringTokenizer st = new StringTokenizer(valid_lengths);
        while (st.hasMoreTokens()) {
            this.valid_lengths.add(st.nextToken());
        }
    }

    public String getDataAsString() {
        return "Variable " + name + ": " + start + " to " + end;
    }

}
