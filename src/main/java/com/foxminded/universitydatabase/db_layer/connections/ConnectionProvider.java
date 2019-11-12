package com.foxminded.universitydatabase.db_layer.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
    private static Connection connection = null;

    private ConnectionProvider() {
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(String url, String userName, String password) throws SQLException {
        connection = DriverManager.getConnection(url, userName, password);
    }
}
