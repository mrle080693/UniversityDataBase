package com.foxminded.universitydatabase.db_layer.queries;

import com.foxminded.universitydatabase.db_layer.connections.ConnectionProvider;

import java.sql.*;
import java.util.*;

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
                "student_id     INTEGER NOT NULL)";
        String QUERY_CREATE_TABLE_GROUPS_STUDENTS = "CREATE TABLE if not exists groups_students " +
                "(group_id    INTEGER NOT NULL," +
                "student_id     INTEGER NOT NULL)";

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
        createStudentOrFaculty(name, surName, queryCreateStudent);
    }

    public void dropStudentById(String id) throws SQLException {
        String queryDropStudentById = "DELETE FROM students WHERE id = " + id;

        connection = connectionProvider.getConnection();
        statement = connection.createStatement();
        statement.executeUpdate(queryDropStudentById);

        connection.close();
    }

    public void createGroup(String name) throws SQLException {
        if (groupIsAlreadyExists(name)) {
            throw new SQLException("Sorry ;( Such group is already exists");
        }

        String requestCreateStudent = "INSERT INTO groups (name) VALUES(?)";
        connection = connectionProvider.getConnection();
        preparedStatement = connection.prepareStatement(requestCreateStudent);
        preparedStatement.setString(1, name);
        preparedStatement.execute();

        connection.close();
    }

    public void createFaculty(String name, String description) throws SQLException {
        if (facultyIsAlreadyExists(name)) {
            throw new SQLException("Sorry ;( Such faculty is already exists");
        }

        String queryCreateFaculty = "INSERT INTO faculties (name, description) VALUES(?, ?)";
        createStudentOrFaculty(name, description, queryCreateFaculty);
    }

    private void createStudentOrFaculty(String firstParameter, String secondParameter, String query) throws SQLException {
        connection = connectionProvider.getConnection();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, firstParameter);
        preparedStatement.setString(2, secondParameter);
        preparedStatement.execute();

        connection.close();
    }

    private boolean groupIsAlreadyExists(String name) throws SQLException {
        String queryIsExists = "SELECT name FROM groups WHERE name = ?";

        return exists(queryIsExists, name);
    }

    private boolean facultyIsAlreadyExists(String name) throws SQLException {
        String queryIsExists = "SELECT name FROM faculties WHERE name = ?";

        return exists(queryIsExists, name);
    }

    private boolean exists(String query, String name) throws SQLException {
        boolean result = false;

        connection = connectionProvider.getConnection();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            result = true;
        }

        connection.close();

        return result;
    }

    public void addStudentToTheFaculty(int studentsId, int facultiesId) throws SQLException {
        String queryAddStudentToTheFaculty = "INSERT INTO faculties_students (student_id, faculty_id) VALUES(?, ?)";
        studentAddOrDropToFacultyOrGroup(studentsId, facultiesId, queryAddStudentToTheFaculty);
    }

    public void dropStudentFromTheFaculty(int studentsId, int facultiesId) throws SQLException {
        String queryDropStudentFromFaculty = "DELETE FROM faculties_students WHERE student_id = ? AND faculty_id = ?";
        studentAddOrDropToFacultyOrGroup(studentsId, facultiesId, queryDropStudentFromFaculty);
    }

    public void addStudentToTheGroup(int studentsId, int groupId) throws SQLException {
        String queryAddStudentToTheGroup = "INSERT INTO groups_students (student_id, group_id) VALUES(?, ?)";
        studentAddOrDropToFacultyOrGroup(studentsId, groupId, queryAddStudentToTheGroup);
    }

    private void studentAddOrDropToFacultyOrGroup(int studentsId, int facultiesId, String query) throws SQLException {
        connection = connectionProvider.getConnection();
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, studentsId);
        preparedStatement.setInt(2, facultiesId);
        preparedStatement.execute();

        connection.close();
    }

    public List<String> getStudentsFromFaculty(int facultyId) throws SQLException {
        List<String> result = new LinkedList<String>();
        String queryForGettingStudentId = "SELECT student_id FROM faculties_students WHERE faculty_id = ?";
        String queryForFindStudent = "SELECT * FROM students WHERE id = ?";

        connection = connectionProvider.getConnection();
        preparedStatement = connection.prepareStatement(queryForGettingStudentId);
        preparedStatement.setInt(1, facultyId);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int studentsId = resultSet.getInt("student_id");
            preparedStatement = connection.prepareStatement(queryForFindStudent);
            preparedStatement.setInt(1, studentsId);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            String student = resultSet.getString(1) +
                    resultSet.getString(2) +
                    resultSet.getString(1);

            result.add(student);
        }

        connection.close();
        return result;
    }

    public List<String> getGroupsWithStudentsQuantityIsNotMoreThanX(int x) throws SQLException {
        Map<Integer, Integer> studentsQuantities = new HashMap<Integer, Integer>();
        List<String> result = new ArrayList<String>();

        connection = connectionProvider.getConnection();
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM groups_students");

        while (resultSet.next()) {
            int groupId = resultSet.getInt("group_id");

            if (!studentsQuantities.containsKey(groupId)) {
                studentsQuantities.put(groupId, 1);
            } else {
                studentsQuantities.put(groupId, studentsQuantities.get(groupId + 1));
            }

            if (studentsQuantities.get(groupId) <= x) {
                preparedStatement = connection.prepareStatement("SELECT name FROM groups WHERE id = ?");
                preparedStatement.setInt(1, groupId);
                ResultSet resultSetWithGroupsName = preparedStatement.executeQuery();

                resultSetWithGroupsName.next();
                result.add(resultSetWithGroupsName.getString("name"));
            }
        }

        connection.close();
        return result;
    }

    public List<Integer> getGroupsId() throws SQLException {
        ArrayList<Integer> groupsId = new ArrayList<Integer>();
        connection = connectionProvider.getConnection();

        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT id FROM groups");

        while (resultSet.next()) {
            resultSet.next();
            groupsId.add(resultSet.getInt("id"));
        }

        connection.close();
        return groupsId;
    }

    public List<Integer> getStudentsId() throws SQLException {
        ArrayList<Integer> groupsId = new ArrayList<Integer>();
        connection = connectionProvider.getConnection();

        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT id FROM students");

        while (resultSet.next()) {
            resultSet.next();
            groupsId.add(resultSet.getInt("id"));
        }

        connection.close();
        return groupsId;
    }
}
