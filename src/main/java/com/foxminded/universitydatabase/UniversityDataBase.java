package com.foxminded.universitydatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UniversityDataBase {
    private static final String URL = "jdbc:postgresql://localhost:5432/university_db";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        try {
            Connection postgreSQLConnection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Sorry (; Connection troubles");
        }
    }
}
