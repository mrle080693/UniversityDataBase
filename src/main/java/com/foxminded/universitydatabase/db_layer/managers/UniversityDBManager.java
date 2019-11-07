package com.foxminded.universitydatabase.db_layer.managers;

import com.foxminded.universitydatabase.db_layer.connections.ConnectionProvider;
import com.foxminded.universitydatabase.db_layer.queries.UniversityDBQueries;

import java.sql.*;
import java.util.*;

public class UniversityDBManager {
    private static final String URL = "";
    private static final String USER_NAME = "";
    private static final String PASSWORD = "";
    private ConnectionProvider connectionProvider = new ConnectionProvider(URL, USER_NAME, PASSWORD);
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public UniversityDBManager() throws SQLException {
        dropTablesIfExists();
        createTablesIfNotExists();
    }

    private void dropTablesIfExists() throws SQLException {
        try {
            connection = connectionProvider.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(UniversityDBQueries.QUERY_DROP_TABLES_IF_EXISTS);
        } finally {
            statement.close();
            connection.close();
        }
    }

    private void createTablesIfNotExists() throws SQLException {
        try {
            connection = connectionProvider.getConnection();

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
        } finally {
            connection.close();
        }
    }

    public void createStudent(String name, String surName) throws SQLException {
        doTwoParametersQuery(name, surName, UniversityDBQueries.QUERY_CREATE_STUDENT);
    }

    public void dropStudentById(String id) throws SQLException {
        try {
            connection = connectionProvider.getConnection();
            preparedStatement = connection.prepareStatement(UniversityDBQueries.QUERY_DROP_STUDENT_BY_ID);
            preparedStatement.setInt(1, Integer.valueOf(id));
            preparedStatement.execute();
        } finally {
            connection.close();
        }
    }

    public void createGroup(String name) throws SQLException {
        if (groupIsAlreadyExists(name)) {
            throw new SQLException("Sorry ;( Such group is already exists");
        }

        try {
            connection = connectionProvider.getConnection();
            preparedStatement = connection.prepareStatement(UniversityDBQueries.QUERY_CREATE_GROUP);
            preparedStatement.setString(1, name);
            preparedStatement.execute();
        } finally {
            connection.close();
        }
    }

    public void createFaculty(String name, String description) throws SQLException {
        if (facultyIsAlreadyExists(name)) {
            throw new SQLException("Sorry ;( Such faculty is already exists");
        }

        doTwoParametersQuery(name, description, UniversityDBQueries.QUERY_CREATE_FACULTY);
    }

    private boolean groupIsAlreadyExists(String name) throws SQLException {
        return exists(UniversityDBQueries.QUERY_FIND_GROUP_BY_NAME, name);
    }

    private boolean facultyIsAlreadyExists(String name) throws SQLException {
        return exists(UniversityDBQueries.QUERY_FIND_NAME_BY_NAME, name);
    }

    private boolean exists(String query, String name) throws SQLException {
        boolean result = false;

        try {
            connection = connectionProvider.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = true;
            }

        } finally {
            connection.close();
        }

        return result;
    }

    public void addStudentToTheFaculty(int studentsId, int facultiesId) throws SQLException {
        doTwoParametersQuery(UniversityDBQueries.QUERY_ADD_STUDENT_TO_THE_FACULTY, studentsId, facultiesId);
    }

    public void dropStudentFromTheFaculty(int studentsId, int facultiesId) throws SQLException {
        doTwoParametersQuery(UniversityDBQueries.QUERY_DROP_STUDENT_FROM_FACULTY, studentsId, facultiesId);
    }

    public void addStudentToTheGroup(int studentsId, int groupId) throws SQLException {
        doTwoParametersQuery(UniversityDBQueries.QUERY_ADD_STUDENT_TO_THE_GROUP, studentsId, groupId);
    }

    private void doTwoParametersQuery(String query, String firstParameter, String secondParameter) throws SQLException {
        try {
            connection = connectionProvider.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstParameter);
            preparedStatement.setString(2, secondParameter);
            preparedStatement.execute();
        } finally {
            connection.close();
        }
    }

    private void doTwoParametersQuery(String query, int studentsId, int facultiesId) throws SQLException {
        try {
            connection = connectionProvider.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentsId);
            preparedStatement.setInt(2, facultiesId);
            preparedStatement.execute();
        } finally {
            connection.close();
        }
    }

    public List<String> getStudentsFromFaculty(int facultyId) throws SQLException {
        List<String> result = new LinkedList<String>();

        try {
            connection = connectionProvider.getConnection();
            preparedStatement = connection.prepareStatement(UniversityDBQueries.QUERY_GET_STUDENT_ID_FROM_FACULTY);
            preparedStatement.setInt(1, facultyId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int studentsId = resultSet.getInt("student_id");
                preparedStatement = connection.prepareStatement(UniversityDBQueries.QUERY_FOR_FIND_STUDENT);
                preparedStatement.setInt(1, studentsId);
                resultSet = preparedStatement.executeQuery();

                resultSet.next();
                String student = resultSet.getString(1) +
                        resultSet.getString(2) +
                        resultSet.getString(1);

                result.add(student);
            }
        } finally {
            connection.close();
        }

        return result;
    }

    public List<String> getNotMoreXGroups(int x) throws SQLException {
        Map<Integer, Integer> studentsQuantities = new HashMap<Integer, Integer>();
        List<String> result = new ArrayList<String>();

        try {
            connection = connectionProvider.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(UniversityDBQueries.QUERY_SELECT_ALL_FROM_GROUPS_STUDENTS_TABLE);

            while (resultSet.next()) {
                int groupId = resultSet.getInt("group_id");

                if (!studentsQuantities.containsKey(groupId)) {
                    studentsQuantities.put(groupId, 1);
                } else {
                    studentsQuantities.put(groupId, studentsQuantities.get(groupId + 1));
                }

                if (studentsQuantities.get(groupId) <= x) {
                    preparedStatement = connection.prepareStatement(UniversityDBQueries.QUERY_GET_NAME_FROM_GROUP_BU_ID);
                    preparedStatement.setInt(1, groupId);
                    ResultSet resultSetWithGroupsName = preparedStatement.executeQuery();

                    resultSetWithGroupsName.next();
                    result.add(resultSetWithGroupsName.getString("name"));
                }
            }
        } finally {
            connection.close();
        }

        return result;
    }

    private List<Integer> getGroupsId() throws SQLException {
        ArrayList<Integer> groupsId = new ArrayList<Integer>();

        try {
            connection = connectionProvider.getConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery(UniversityDBQueries.QUERY_SELECT_ID_FROM_GROUPS);

            while (resultSet.next()) {
                resultSet.next();
                groupsId.add(resultSet.getInt("id"));
            }
        } finally {
            connection.close();
        }
        return groupsId;
    }

    public List<Integer> getStudentsId() throws SQLException {
        ArrayList<Integer> groupsId = new ArrayList<Integer>();

        try {
            connection = connectionProvider.getConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery(UniversityDBQueries.QUERY_SELECT_ID_FROM_STUDENTS);

            while (resultSet.next()) {
                resultSet.next();
                groupsId.add(resultSet.getInt("id"));
            }
        } finally {
            connection.close();
        }
        return groupsId;
    }

    private int getGroupSize(int groupId) throws SQLException {
        int groupSize = 0;

        try {
            connection = connectionProvider.getConnection();

            preparedStatement = connection.prepareStatement(UniversityDBQueries.QUERY_GET_STUDENT_ID_BY_GROUP_ID);
            preparedStatement.setInt(1, groupId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                resultSet.next();
                groupSize++;
            }
        } finally {
            connection.close();
        }

        return groupSize;
    }

    public Integer getNotFullGroupId() throws SQLException {
        Integer notFullGroupId = null;
        List<Integer> groupsId = getGroupsId();

        for (Integer groupId : groupsId) {
            int groupSize = getGroupSize(groupId);
            if (groupSize < 30) {
                notFullGroupId = groupId;
            }
        }

        return notFullGroupId;
    }

    public void disbandNotMoreXGroups(int x) throws SQLException {
        List<Integer> groupsId = getGroupsId();

        try {
            for (Integer groupId : groupsId) {
                int groupSize = getGroupSize(groupId);
                if (groupSize < x) {
                    connection = connectionProvider.getConnection();
                    preparedStatement = connection.prepareStatement(UniversityDBQueries.QUERY_DELETE_STUDENT_FROM_GROUP_BY_GROUP_ID);
                    preparedStatement.setInt(1, groupId);
                    preparedStatement.executeUpdate();
                }
            }
        } finally {
            connection.close();
        }
    }
}
