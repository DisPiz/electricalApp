package org.lukashchuk.appll.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static final String DB = "appliancedb";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB + "?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root";
    private static Connection connection;
    public static void conectionDB(){
        try {
            if(connection == null){
                connection = DriverManager.getConnection(URL, USER, PASS);
                connection.close();
            }
            if(connection.isClosed()){
                connection = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("Connected to: " + DB);
            } else if(!connection.isClosed()){
                connection.close();
                System.out.println("Disconnected from: " + DB);
            }
        } catch (SQLException e) {
            System.err.println("Connection error!");
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
