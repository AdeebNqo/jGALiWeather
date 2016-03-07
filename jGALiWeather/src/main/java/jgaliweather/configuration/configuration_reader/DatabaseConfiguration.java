package jgaliweather.configuration.configuration_reader;

/* This class holds database configuration data,
   including host info, username and password and
   database name.
 */
public class DatabaseConfiguration {

    private String name;
    private String driver;
    private String host;
    private String dbname;
    private String user;
    private String pwd;

    public DatabaseConfiguration(String name, String driver, String host, String dbname, String user, String pwd) {
        this.name = name;
        this.driver = driver;
        this.host = host;
        this.dbname = dbname;
        this.user = user;
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "DRIVER=" + driver + ";SERVER=" + host + ";DATABASE=" + dbname + ";UID=" + user + ";PWD=" + pwd;
    }
}
