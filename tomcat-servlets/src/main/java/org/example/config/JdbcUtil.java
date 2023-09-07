package org.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

public class JdbcUtil {
    private static String databaseURL;

    private static String username;


    public static Connection getConnection() {
        initDB();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(databaseURL, username, "1237d7321");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(connection).orElseThrow(IllegalArgumentException::new);
    }

    private static void initDB() {
        String configFile = "db-config.properties";
        ClassLoader classLoader = JdbcUtil.class.getClassLoader();
        try (final InputStream resourceAsStream = classLoader.getResourceAsStream(configFile)) {
            final Properties properties = new Properties();
            if (resourceAsStream == null) {
                System.out.println("Config file '" + configFile + "' not found in resources.");
                return;
            }
            properties.load(resourceAsStream);
            databaseURL = properties.getProperty("db_url");
            username = properties.getProperty("db_user");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            String jdbcDriver = "org.postgresql.Driver";
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}