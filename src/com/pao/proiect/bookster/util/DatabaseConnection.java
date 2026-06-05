package com.pao.proiect.bookster.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try (InputStream input = new FileInputStream("resources/db.properties")) { 
            Properties prop = new Properties();
            prop.load(input);
            String url = prop.getProperty("db.url");
            
            // MAGIC LINE: Forțăm manual încărcarea driverului înainte să cerem conexiunea
            Class.forName("org.sqlite.JDBC");
            
            this.connection = DriverManager.getConnection(url);
        } catch (Exception e) { // Am pus Exception general ca să prindă absolut orice eroare
            throw new RuntimeException("Eroare la initializarea conexiunii DB: " + e.getMessage(), e);
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new DatabaseConnection();
            }
        } catch (Exception e) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}