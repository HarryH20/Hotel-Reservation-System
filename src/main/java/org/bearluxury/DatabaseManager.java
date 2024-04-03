package org.bearluxury;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseManager {
    public Connection connect_to_db(String dbName, String user, String password) {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbName, user, password);
            if (conn != null) {
                System.out.println("Connection Established");
            } else {
                System.out.println("Connection Failed.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }
}
