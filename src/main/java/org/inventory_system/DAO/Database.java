package org.inventory_system.DAO;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    protected  Connection connection;
    private static final String CONFIG_FILE="application.properties";
    private static Database database = new Database();
    public static Database getInstance()
    {
        if (database == null)
            database = new Database();
        return database;
    }

    public void connectDB() throws SQLException {
        Properties dbConfig = new Properties();
        try {
            InputStream input = this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE);
            dbConfig.load(input);
            Class.forName(dbConfig.getProperty("javafx.jdbc.driver"));
            connection = DriverManager.getConnection(dbConfig.getProperty(
                            "javafx.datasource.url"),
                    dbConfig.getProperty("javafx.datasource.username"),
                    dbConfig.getProperty("javafx.datasource.password"));

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void closeDB() throws SQLException{
        if (!(connection == null)){
            if (!(connection.isClosed())){
                connection.close();
            }
        }
    }
}
