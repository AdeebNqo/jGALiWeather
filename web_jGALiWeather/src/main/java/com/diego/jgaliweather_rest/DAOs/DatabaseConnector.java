package com.diego.jgaliweather_rest.DAOs;

import com.diego.jgaliweather_rest.VOs.MeteorologicalDataDay;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;

/* Implements a database connection manager. */
public class DatabaseConnector {

    private String db = "jdbc:sqlite:D:/OneDrive/Clase/TFG/jGALiWeather/jGALiWeather/estudio6-6.db";
    private static DatabaseConnector instance = new DatabaseConnector();

    /*
        Initializes a DatabaseConnector object

        :return: A new DatabaseConnector object
     */
    private DatabaseConnector() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            Logger.getLogger("Logger").log(Level.SEVERE, "Error conectando a BBDD");
        }
    }

    public static DatabaseConnector getInstance() {
        return instance;
    }

    /*
        Retrieves the forecast values of any location.
    
        :return: A list of tuples containing the variable
        forecast data
     */
    public MeteorologicalDataDay retrieveVariableDataForLocation(int location_id, Date actual_date) throws Exception {

        Connection conn = null;
        Statement stmt = null;
        StringTokenizer st = null;
        MeteorologicalDataDay data = null;
        ArrayList<Integer> sky = new ArrayList();
        ArrayList<Integer> wind = new ArrayList();
        ArrayList<Integer> temp = new ArrayList();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateTime dtOrg = new DateTime(actual_date);

        String query = "SELECT CIELO_MG, VIENTO_MG, TEMPERATURA_MG FROM METEO WHERE ID_MG='" + location_id + "' AND (";

        query = query.concat("FECHA LIKE '" + sdf.format(actual_date) + "%'");
        for (int i = 0; i < 3; i++) {
            dtOrg = dtOrg.plusDays(1);
            query = query.concat(" OR FECHA LIKE '" + sdf.format(dtOrg.toDate()) + "%'");
        }
        query = query.concat(")");

        try {

            conn = DriverManager.getConnection(db);
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {            
                st = new StringTokenizer(rs.getString("CIELO_MG"), ",");
                while (st.hasMoreTokens()) {
                    sky.add(Integer.parseInt(st.nextToken()));
                }

                st = new StringTokenizer(rs.getString("VIENTO_MG"), ",");
                while (st.hasMoreTokens()) {
                    wind.add(Integer.parseInt(st.nextToken()));
                }

                st = new StringTokenizer(rs.getString("TEMPERATURA_MG"), ",");
                while (st.hasMoreTokens()) {
                    temp.add(Integer.parseInt(st.nextToken()));
                }
            }

            data = new MeteorologicalDataDay(sky, wind, temp);

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger("Logger").log(Level.SEVERE, "Error consultando los datos en la BBDD: {0}", ex.getMessage());
                    throw new Exception(ex1);
                }
            }
            Logger.getLogger("Logger").log(Level.SEVERE, "Error consultando los datos en la BBDD: {0}", ex.getMessage());
            throw new Exception(ex);
        }

        return data;
    }
    
    public String getLocationName(int location_id) throws Exception {

        Connection conn = null;
        Statement stmt = null;
        String data = "";

        String query = "SELECT name FROM Location WHERE id='" + location_id + "'";

        try {
            conn = DriverManager.getConnection(db);
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                data = rs.getString(1);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger("Logger").log(Level.SEVERE, "Error consultando los datos en la BBDD: {0}", ex.getMessage());
                    throw new Exception(ex1);
                }
            }
            Logger.getLogger("Logger").log(Level.SEVERE, "Error consultando los datos en la BBDD: {0}", ex.getMessage());
            throw new Exception(ex);
        }

        return data;
    }
    
    public String getComment(int location_id, Date actual_date) throws Exception {

        Connection conn = null;
        Statement stmt = null;
        String data = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String query = "SELECT comentario FROM Comentarios WHERE id='" + location_id + "' AND fecha = '"+ sdf.format(actual_date) +"'";

        try {
            conn = DriverManager.getConnection(db);
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                data = rs.getString(1);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex1) {
                    Logger.getLogger("Logger").log(Level.SEVERE, "Error consultando los datos en la BBDD: {0}", ex.getMessage());
                    throw new Exception(ex1);
                }
            }
            Logger.getLogger("Logger").log(Level.SEVERE, "Error consultando los datos en la BBDD: {0}", ex.getMessage());
            throw new Exception(ex);
        }

        return data;
    }

}
