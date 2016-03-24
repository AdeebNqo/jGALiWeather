package jgaliweather.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import jgaliweather.configuration.configuration_reader.DatabaseConfiguration;
import jgaliweather.configuration.logger.GALiLogger;

/* Implements a database connection manager. */
public class DatabaseConnector {

    private DatabaseConfiguration dbinfo;
    private Connection conn;

    /*
        Initializes a DatabaseConnector object

        :param dbinfo: A DatabaseConfiguration object, containing
        connection info.

        :return: A new DatabaseConnector object
     */
    public DatabaseConnector(DatabaseConfiguration dbinfo) {
        this.dbinfo = dbinfo;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbinfo.getDbname());
        } catch (ClassNotFoundException | SQLException e) {
            GALiLogger.getLogger().log(Level.SEVERE, "Error conectando a BBDD");
        }
    }

    /*
        Retrieves the location list from a
        database

        :return: A list containing location identifiers.
     */
    public ArrayList<Integer> retrieveLocations() {

        Statement stmt = null;
        ArrayList<Integer> locations = new ArrayList();

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT ID_MG FROM METEO");
            while (rs.next()) {
                locations.add(Integer.parseInt(rs.getString(1)));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            GALiLogger.getLogger().log(Level.SEVERE, "Error consultando las localizaciones en la BBDD: {0}", e.getMessage());
        }

        return locations;
    }

    /*
        Retrieves the forecast values of any location.
    
        :return: A list of tuples containing the variable
        forecast data
     */
    public ArrayList<Integer> retrieveVariableDataForLocation(int location_id, ArrayList<String> dates) {

        Statement stmt = null;
        ArrayList<Integer> data = new ArrayList();

        if (!dates.isEmpty()) {

            String query = "SELECT CIELO_MG, VIENTO_MG, TEMPERATURA_MG FROM METEO WHERE ID_MG='" + location_id + "' AND (";

            query = query.concat("FECHA LIKE '" + dates.get(0) + "%'");
            for (int i = 1; i < dates.size(); i++) {
                query = query.concat(" OR FECHA LIKE '" + dates.get(i) + "%'");
            }
            query = query.concat(")");

            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    StringTokenizer st = new StringTokenizer(rs.getString("CIELO_MG") + "," + rs.getString("VIENTO_MG") + "," + rs.getString("TEMPERATURA_MG"), ",");
                    while (st.hasMoreTokens()) {
                        data.add(Integer.parseInt(st.nextToken()));
                    }
                }
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                GALiLogger.getLogger().log(Level.SEVERE, "Error obteniendo los valores de predicción");
            }
        } else {
            GALiLogger.getLogger().log(Level.SEVERE, "Fechas vacias");
        }

        return data;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException ex) {
            GALiLogger.getLogger().log(Level.SEVERE, "Error cerrando conexión a BBDD");
        }
    }
}
