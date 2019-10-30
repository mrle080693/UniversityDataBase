package com.foxminded.universitydatabase.db_layer.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// It will be redone
public class PostgreSQLConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/university_db";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "root";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    }
}
