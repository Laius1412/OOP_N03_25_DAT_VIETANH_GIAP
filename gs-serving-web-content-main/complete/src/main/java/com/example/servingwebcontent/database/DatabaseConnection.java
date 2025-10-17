package com.example.servingwebcontent.database;

import org.springframework.stereotype.Controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.lang.Class;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Value;

@Controller
public class DatabaseConnection {

    public DatabaseConnection() {
    };

    // @Value("${my.database.url}")
    // protected String myDatabaseURL;

    String myDatabaseURL = "mysql://avnadmin:AVNS_oANjQlXdl8KLqmPO1rs@quanlygiapha-vanhlion.b.aivencloud.com:23978/defaultdb?ssl-mode=REQUIRED";

    // @Value("${my.database.driver}")
    // protected String myDatabaseDriver;

    String myDatabaseDriver = "com.mysql.cj.jdbc.Driver";

    public Connection conn = null;
    
    public Statement getMyConn() {

        try {

            Class.forName(myDatabaseDriver);
            conn = DriverManager.getConnection(myDatabaseURL);
            Statement sta = conn.createStatement();
            return sta;

        } catch (Exception e) {
            System.out.println("DatabaseConnection at getMyConn: " + e);
        }

        return null;

    }
    
    public Connection getOnlyConn() {
        try {
            Class.forName(myDatabaseDriver);

            conn = DriverManager.getConnection(myDatabaseURL);
            return conn;

        } catch (Exception e) {
            System.out.println("Database connection error: " + e);
        }

        return conn;

    }

}
