package com.foxminded.universitydatabase.db_layer.queries;

import com.foxminded.universitydatabase.db_layer.connections.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class QueriesToUniversityDB {
    private static final String URL = "jdbc:postgresql://localhost:5432/university_db";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "root";
    private ConnectionProvider connectionProvider = new ConnectionProvider(URL, USER_NAME, PASSWORD);
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;

    public QueriesToUniversityDB() throws SQLException {
        dropTablesIfExists();
        createTablesIfNotExists();
    }

    private void dropTablesIfExists() throws SQLException {
        connection = connectionProvider.getConnection();
        statement = connection.createStatement();
        statement.executeUpdate("DROP TABLE if exists students, groups, faculties, faculties_students, groups_students");
        connection.close();
    }

    private void createTablesIfNotExists() throws SQLException {
        String QUERY_CREATE_TABLE_STUDENTS = "CREATE TABLE if not exists students " +
                "(id SERIAL, " +
                " name VARCHAR(100), " +
                " sur_name VARCHAR(100), " +
                " PRIMARY KEY ( id ))";
        String QUERY_CREATE_TABLE_GROUPS = "CREATE TABLE if not exists groups " +
                "(id SERIAL, " +
                " name VARCHAR(100), " +
                " PRIMARY KEY ( id ))";
        String QUERY_CREATE_TABLE_FACULTIES = "CREATE TABLE if not exists faculties " +
                "(id SERIAL, " +
                " name VARCHAR(100), " +
                " description VARCHAR(1000), " +
                " PRIMARY KEY ( id ))";
        String QUERY_CREATE_TABLE_FACULTIES_STUDENTS = "CREATE TABLE if not exists faculties_students " +
                "(faculty_id    INTEGER NOT NULL," +
                "student_id     INTEGER NOT NULL," +
                "FOREIGN KEY (faculty_id) REFERENCES faculties (id)," +
                "FOREIGN KEY (student_id) REFERENCES students (id))";
        String QUERY_CREATE_TABLE_GROUPS_STUDENTS = "CREATE TABLE if not exists groups_students " +
                "(group_id    INTEGER NOT NULL," +
                "student_id     INTEGER NOT NULL," +
                "FOREIGN KEY (group_id) REFERENCES groups (id)," +
                "FOREIGN KEY (student_id) REFERENCES students (id))";

        connection = connectionProvider.getConnection();

        statement = connection.createStatement();
        statement.executeUpdate(QUERY_CREATE_TABLE_STUDENTS);

        statement = connection.createStatement();
        statement.executeUpdate(QUERY_CREATE_TABLE_GROUPS);

        statement = connection.createStatement();
        statement.executeUpdate(QUERY_CREATE_TABLE_FACULTIES);

        statement = connection.createStatement();
        statement.executeUpdate(QUERY_CREATE_TABLE_FACULTIES_STUDENTS);

        statement = connection.createStatement();
        statement.executeUpdate(QUERY_CREATE_TABLE_GROUPS_STUDENTS);

        connection.close();
    }


    //________________________ Maybe it will be in new class_________________________________

    public void createStudent(String name, String surName) throws SQLException {
        String queryCreateStudent = "INSERT INTO students (name, sur_name) VALUES(?, ?)";

        connection = connectionProvider.getConnection();
        preparedStatement = connection.prepareStatement(queryCreateStudent);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surName);
        preparedStatement.execute();

        connection.close();
    }

    public void dropStudentById(String id) throws SQLException {
        String queryDropStudentById = "DELETE FROM students WHERE id = " + id;

        connection = connectionProvider.getConnection();
        statement = connection.createStatement();
        statement.executeUpdate(queryDropStudentById);

        connection.close();
    }

    public void createGroup(String name) throws SQLException {
        String requestCreateStudent = "INSERT INTO groups (name) VALUES(?)";

        connection = connectionProvider.getConnection();
        preparedStatement = connection.prepareStatement(requestCreateStudent);
        preparedStatement.setString(1, name);
        preparedStatement.execute();

        connection.close();
    }

}
