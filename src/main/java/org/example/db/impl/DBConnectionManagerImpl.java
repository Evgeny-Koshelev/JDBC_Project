package org.example.db.impl;

import org.example.db.ConnectionManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionManagerImpl implements ConnectionManager {

    private final String url;
    private final String username;
    private final String password;

    FileInputStream fis;
    Properties property = new Properties();
    public DBConnectionManagerImpl() {
        try {
            fis = new FileInputStream("src/main/resources/db.properties");
            property.load(fis);
            url = property.getProperty("flyway.url");
            username = property.getProperty("flyway.user");
            password = property.getProperty("flyway.password");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DBConnectionManagerImpl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
