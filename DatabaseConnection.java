package com.example.servingwebcontent.Database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection {

    private String myDatabaseURL;
    private String myDatabaseDriver = "com.mysql.cj.jdbc.Driver";

    public Connection conn = null;

    public DatabaseConnection() {
        // Priority: environment variable MY_DATABASE_URL -> config.properties (project root) -> unset
        String envUrl = System.getenv("MY_DATABASE_URL");
        if (envUrl != null && !envUrl.isEmpty()) {
            myDatabaseURL = envUrl;
        } else {
            Path cfg = Paths.get("config.properties");
            if (cfg.toFile().exists()) {
                Properties props = new Properties();
                try (InputStream in = new FileInputStream(cfg.toFile())) {
                    props.load(in);
                    String propUrl = props.getProperty("my.database.url");
                    if (propUrl != null && !propUrl.isEmpty()) {
                        myDatabaseURL = propUrl;
                    }
                    String propDrv = props.getProperty("my.database.driver");
                    if (propDrv != null && !propDrv.isEmpty()) {
                        myDatabaseDriver = propDrv;
                    }
                } catch (IOException e) {
                    System.out.println("Failed to read config.properties: " + e);
                }
            }
        }

        if (myDatabaseURL == null || myDatabaseURL.isEmpty()) {
            System.out.println("Warning: myDatabaseURL not set. Set the MY_DATABASE_URL env var or create a config.properties file with my.database.url");
        }
    }

    public Statement getMyConn() {
        try {
            Class.forName(myDatabaseDriver);
            conn = DriverManager.getConnection(myDatabaseURL);
            Statement sta = conn.createStatement();
            return sta;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("myDBConnection error: " + e);
        }
        return null;
    }

    public Connection getOnlyConn() {
        try {
            Class.forName(myDatabaseDriver);
            conn = DriverManager.getConnection(myDatabaseURL);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database connection error: " + e);
        }
        return conn;
    }

}
