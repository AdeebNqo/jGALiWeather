package jgaliweather.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import jgaliweather.configuration.configuration_reader.DatabaseConfiguration;
import jgaliweather.configuration.logger.GALiLogger;
import org.javatuples.Pair;

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
    public ArrayList<Pair<Integer, String>> retrieveLocations() {

        Statement stmt = null;
        ArrayList<Pair<Integer, String>> locations = new ArrayList();

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name FROM Location");
            while (rs.next()) {
                locations.add(new Pair(Integer.parseInt(rs.getString(1)), rs.getString(2)));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            GALiLogger.getLogger().log(Level.SEVERE, "Error consultando las localizaciones en la BBDD: {0}", e.getMessage());
        }

        return locations;
    }

    public void saveData(String id, String date, String comment) {

        Statement stmt = null;

        String sql = "INSERT INTO Comentarios VALUES ('"+ id +"', '"+ date +"', '"+ comment +"')";
        
        try {
            stmt = conn.createStatement();
            
            stmt.executeUpdate(sql);
            
            stmt.close();

        } catch (SQLException e) {
            GALiLogger.getLogger().log(Level.SEVERE, "Error guardando los comentarios");
        }

    }

    /*
        Retrieves the forecast values of any location.
    
        :return: A list of tuples containing the variable
        forecast data
     */
    public HashMap<String, ArrayList<Integer>> retrieveVariableDataForLocation(int location_id, ArrayList<String> dates) {

        Statement stmt = null;
        StringTokenizer st = null;
        HashMap<String, ArrayList<Integer>> data = new HashMap();
        ArrayList<Integer> viento = new ArrayList();;
        ArrayList<Integer> cielo = new ArrayList();;
        ArrayList<Integer> temperatura = new ArrayList();;

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
                    st = new StringTokenizer(rs.getString("CIELO_MG"), ",");
                    while (st.hasMoreTokens()) {
                        cielo.add(Integer.parseInt(st.nextToken()));
                    }

                    st = new StringTokenizer(rs.getString("VIENTO_MG"), ",");
                    while (st.hasMoreTokens()) {
                        viento.add(Integer.parseInt(st.nextToken()));
                    }

                    st = new StringTokenizer(rs.getString("TEMPERATURA_MG"), ",");
                    while (st.hasMoreTokens()) {
                        temperatura.add(Integer.parseInt(st.nextToken()));
                    }
                }

                data.put("Viento", viento);
                data.put("Temperatura", temperatura);
                data.put("Meteoro", cielo);
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
