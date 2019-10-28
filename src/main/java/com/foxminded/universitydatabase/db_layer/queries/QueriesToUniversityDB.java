package com.foxminded.universitydatabase.db_layer.queries;

import com.foxminded.universitydatabase.db_layer.connections.PostgreSQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class QueriesToUniversityDB {
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;

    public QueriesToUniversityDB() throws SQLException {
        dropAllTablesIfExists();
        createTables();
    }

    private void dropAllTablesIfExists() throws SQLException {
        connection = new PostgreSQLConnection().getConnection();
        statement = connection.createStatement();
        statement.executeUpdate("DROP TABLE if exists students, groups, faculties");
        connection.close();
    }

    private void createTables() throws SQLException {
        createTableStudents();
        createTableGroups();
        createTableFaculties();
    }

    private void createTableStudents() throws SQLException {
        String QUERY_CREATE_TABLE_STUDENTS = "CREATE TABLE students " +
                "(id SERIAL, " +
                " group_id INTEGER not NULL, " +
                " first_name VARCHAR(100), " +
                " last_name VARCHAR(100), " +
                " PRIMARY KEY ( id ))";

        connection = new PostgreSQLConnection().getConnection();
        statement = connection.createStatement();
        statement.executeUpdate(QUERY_CREATE_TABLE_STUDENTS);
        connection.close();
    }

    private void createTableGroups() throws SQLException {
        String QUERY_CREATE_TABLE_GROUPS = "CREATE TABLE groups " +
                "(id SERIAL, " +
                " name VARCHAR(100), " +
                " PRIMARY KEY ( id ))";

        connection = new PostgreSQLConnection().getConnection();
        statement = connection.createStatement();
        statement.executeUpdate(QUERY_CREATE_TABLE_GROUPS);
        connection.close();
    }

    private void createTableFaculties() throws SQLException {
        String QUERY_CREATE_TABLE_FACULTIES = "CREATE TABLE faculties " +
                "(id SERIAL, " +
                " name VARCHAR(100), " +
                " description VARCHAR(1000), " +
                " PRIMARY KEY ( id ))";

        connection = new PostgreSQLConnection().getConnection();
        statement = connection.createStatement();
        statement.executeUpdate(QUERY_CREATE_TABLE_FACULTIES);
        connection.close();
    }

}
