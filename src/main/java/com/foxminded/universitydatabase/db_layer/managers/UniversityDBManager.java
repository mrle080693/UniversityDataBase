package com.foxminded.universitydatabase.db_layer.managers;

import com.foxminded.universitydatabase.db_layer.queries.UniversityDBQueries;

import java.sql.*;
import java.util.*;

public class UniversityDBManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/university_db";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "root";
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public void init() throws SQLException {
        dropTables();
        createTables();
    }

    public void createStudent(String name, String surName) throws SQLException {
        executeQuery(UniversityDBQueries.QUERY_CREATE_STUDENT, name, surName);
    }


    public void dropStudentById(String id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            preparedStatement = connection.prepareStatement(UniversityDBQueries.QUERY_DROP_STUDENT_BY_ID);
            preparedStatement.setInt(1, Integer.valueOf(id));
            preparedStatement.execute();
        }
    }

    public void createGroup(String name) throws SQLException {
        if (doesGroupExists(name)) {
            throw new SQLException("Sorry ;( Such group is already isExists");
        }

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            preparedStatement = connection.prepareStatement(UniversityDBQueries.QUERY_CREATE_GROUP);
            preparedStatement.setString(1, name);
            preparedStatement.execute();
        }
    }

    public void createFaculty(String name, String description) throws SQLException {
        if (doesFacultyExists(name)) {
            throw new SQLException("Sorry ;( Such faculty is already isExists");
        }

        executeQuery(UniversityDBQueries.QUERY_CREATE_FACULTY, name, description);
    }

    public void addStudentToFaculty(int studentsId, int facultiesId) throws SQLException {
        executeQuery(UniversityDBQueries.QUERY_ADD_STUDENT_TO_THE_FACULTY, studentsId, facultiesId);
    }

    public void dropStudentFromFaculty(int studentsId, int facultiesId) throws SQLException {
        executeQuery(UniversityDBQueries.QUERY_DROP_STUDENT_FROM_FACULTY, studentsId, facultiesId);
    }

    public void addStudentToGroup(int studentsId, int groupId) throws SQLException {
        executeQuery(UniversityDBQueries.QUERY_ADD_STUDENT_TO_THE_GROUP, studentsId, groupId);
    }

    public List<String> getStudentsByFacultyId(int facultyId) throws SQLException {
        List<String> result = new LinkedList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            preparedStatement = connection.prepareStatement(UniversityDBQueries.QUERY_SELECT_STUDENTS_NAMES_FROM_FACULTY_BY_FACULTY_ID);
            preparedStatement.setInt(1, facultyId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString("name"));
            }
        }

        return result;
    }

    public List<String> getGroupsByStudentsAmount(int x) throws SQLException {
        List<String> result = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            preparedStatement = connection.prepareStatement(UniversityDBQueries.QUERY_SELECT_GROUPS_WITH_STUDENTS_QUANTITY_IS_NOT_MORE_THAN_X);
            preparedStatement.setInt(1, x);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString("name"));
            }
        }

        return result;
    }

    public List<Integer> getStudentsId() throws SQLException {
        List<Integer> studentsId = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(UniversityDBQueries.QUERY_SELECT_ID_FROM_STUDENTS);

            while (resultSet.next()) {
                studentsId.add(resultSet.getInt("id"));
            }
        }

        return studentsId;
    }

    public List<Integer> getNotFullGroupId() throws SQLException {
        List<Integer> groupsId = getGroupsId();

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            preparedStatement = connection.prepareStatement(UniversityDBQueries.QUERY_SELECT_GROUPS_WITH_STUDENTS_QUANTITY_LESS_THAN_30);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                groupsId.add(resultSet.getInt("group_id"));
            }
        }

        return groupsId;
    }

    public void disbandGroupsByCount(int x) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            preparedStatement = connection.prepareStatement(UniversityDBQueries.QUERY_DELETE_STUDENTS_FROM_GROUP_BY_GROUP_AMOUNT);
            preparedStatement.setInt(1, x);
            preparedStatement.executeUpdate();
        }
    }

    private void dropTables() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            statement = connection.createStatement();
            statement.executeUpdate(UniversityDBQueries.QUERY_DROP_TABLES_IF_EXISTS);
        }
    }

    private void createTables() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            statement = connection.createStatement();
            statement.executeUpdate(UniversityDBQueries.QUERY_CREATE_TABLE_STUDENTS);

            statement = connection.createStatement();
            statement.executeUpdate(UniversityDBQueries.QUERY_CREATE_TABLE_GROUPS);

            statement = connection.createStatement();
            statement.executeUpdate(UniversityDBQueries.QUERY_CREATE_TABLE_FACULTIES);

            statement = connection.createStatement();
            statement.executeUpdate(UniversityDBQueries.QUERY_CREATE_TABLE_FACULTIES_STUDENTS);

            statement = connection.createStatement();
            statement.executeUpdate(UniversityDBQueries.QUERY_CREATE_TABLE_GROUPS_STUDENTS);
        }
    }

    private boolean doesGroupExists(String name) throws SQLException {
        return isExists(UniversityDBQueries.QUERY_FIND_GROUP_BY_NAME, name);
    }

    private boolean doesFacultyExists(String name) throws SQLException {
        return isExists(UniversityDBQueries.QUERY_FIND_NAME_BY_NAME, name);
    }

    private boolean isExists(String query, String name) throws SQLException {
        boolean result = false;

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = true;
            }

        }

        return result;
    }

    private List<Integer> getGroupsId() throws SQLException {
        ArrayList<Integer> groupsId = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(UniversityDBQueries.QUERY_SELECT_ID_FROM_GROUPS);

            while (resultSet.next()) {
                groupsId.add(resultSet.getInt("id"));
            }
        }

        return groupsId;
    }

    private void executeQuery(String query, String firstParameter, String secondParameter) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstParameter);
            preparedStatement.setString(2, secondParameter);
            preparedStatement.execute();
        }
    }

    private void executeQuery(String query, int studentsId, int facultiesId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD)) {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentsId);
            preparedStatement.setInt(2, facultiesId);
            preparedStatement.execute();
        }
    }
}
