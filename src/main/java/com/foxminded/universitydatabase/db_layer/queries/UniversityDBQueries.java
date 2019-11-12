package com.foxminded.universitydatabase.db_layer.queries;

public class UniversityDBQueries {

    private UniversityDBQueries() {
    }

    public static final String QUERY_DROP_TABLES_IF_EXISTS = "DROP TABLE if exists students," +
            " groups, faculties, faculties_students, groups_students";
    public static final String QUERY_CREATE_TABLE_STUDENTS = "CREATE TABLE if not exists students " +
            "(id SERIAL, " +
            " name VARCHAR(100), " +
            " sur_name VARCHAR(100), " +
            " PRIMARY KEY ( id ))";
    public static final String QUERY_CREATE_TABLE_GROUPS = "CREATE TABLE if not exists groups " +
            "(id SERIAL, " +
            " name VARCHAR(100), " +
            " PRIMARY KEY ( id ))";
    public static final String QUERY_CREATE_TABLE_FACULTIES = "CREATE TABLE if not exists faculties " +
            "(id SERIAL, " +
            " name VARCHAR(100), " +
            " description VARCHAR(1000), " +
            " PRIMARY KEY ( id ))";
    public static final String QUERY_CREATE_TABLE_FACULTIES_STUDENTS = "CREATE TABLE if not exists faculties_students " +
            "(faculty_id    INTEGER NOT NULL," +
            "student_id     INTEGER NOT NULL)";
    public static final String QUERY_CREATE_TABLE_GROUPS_STUDENTS = "CREATE TABLE if not exists groups_students " +
            "(group_id    INTEGER NOT NULL," +
            "student_id     INTEGER NOT NULL)";
    public static final String QUERY_CREATE_STUDENT = "INSERT INTO students (name, sur_name) VALUES(?, ?)";
    public static final String QUERY_DROP_STUDENT_BY_ID = "DELETE FROM students WHERE id = ?";
    public static final String QUERY_CREATE_GROUP = "INSERT INTO groups (name) VALUES(?)";
    public static final String QUERY_CREATE_FACULTY = "INSERT INTO faculties (name, description) VALUES(?, ?)";
    public static final String QUERY_FIND_GROUP_BY_NAME = "SELECT name FROM groups WHERE name = ?";
    public static final String QUERY_FIND_NAME_BY_NAME = "SELECT name FROM faculties WHERE name = ?";
    public static final String QUERY_ADD_STUDENT_TO_THE_FACULTY = "INSERT INTO faculties_students (student_id, faculty_id) VALUES(?, ?)";
    public static final String QUERY_DROP_STUDENT_FROM_FACULTY = "DELETE FROM faculties_students WHERE student_id = ? AND faculty_id = ?";
    public static final String QUERY_ADD_STUDENT_TO_THE_GROUP = "INSERT INTO groups_students (student_id, group_id) VALUES(?, ?)";
    public static final String QUERY_GET_STUDENT_ID_FROM_FACULTY = "SELECT student_id FROM faculties_students WHERE faculty_id = ?";
    public static final String QUERY_FOR_FIND_STUDENT = "SELECT * FROM students WHERE id = ?";
    public static final String QUERY_SELECT_ID_FROM_GROUPS = "SELECT id FROM groups";
    public static final String QUERY_SELECT_ID_FROM_STUDENTS = "SELECT id FROM students";
    public static final String QUERY_GET_STUDENT_ID_BY_GROUP_ID = "SELECT student_id FROM groups_students WHERE group_id = ?";
    public static final String QUERY_DELETE_STUDENT_FROM_GROUP_BY_GROUP_ID = "DELETE  FROM groups_students WHERE group_id = ?";
    public static final String QUERY_SELECT_GROUPS_WITH_STUDENTS_QUANTITY_IS_NOT_MORE_THAN_X = "SELECT name from groups join groups_students on id = group_id group by name having count(groups_students.student_id) <= ?";
}