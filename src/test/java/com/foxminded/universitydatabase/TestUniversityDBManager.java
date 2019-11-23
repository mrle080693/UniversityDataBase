package com.foxminded.universitydatabase;

import com.foxminded.universitydatabase.db_layer.managers.UniversityDBManager;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static junit.framework.Assert.assertEquals;

class TestUniversityDBManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/university_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "org.postgresql.Driver";

    private static Class driverClass = null;
    private static Connection jdbcConnection = null;
    private static IDatabaseConnection connection = null;
    private static IDataSet databaseDataSet = null;
    private static ITable tableStudents = null;
    private static ITable tableGroups = null;
    private static ITable tableFaculties = null;
    private static ITable tableFacultiesStudents = null;

    private static UniversityDBManager universityDBManager = new UniversityDBManager();

    @BeforeAll
    static void initializer() throws SQLException, ClassNotFoundException, DatabaseUnitException {
        driverClass = Class.forName(DRIVER);
        jdbcConnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        universityDBManager.init();
        connection = new DatabaseConnection(jdbcConnection);
        databaseDataSet = connection.createDataSet();
    }

    @Test
    void createStudentHaveToCreateNewStudent() throws SQLException, DataSetException {
        tableStudents = databaseDataSet.getTable("students");
        int studentsAmount = tableStudents.getRowCount();

        universityDBManager.createStudent("Alexander", "Lyebyedyev");
        tableStudents = databaseDataSet.getTable("students");
        assertEquals(studentsAmount + 1, tableStudents.getRowCount());
    }

    @Test
    void dropStudentByIdHaveToDropStudent() throws SQLException, DataSetException {
        universityDBManager.createStudent("Alexander", "Lyebyedyev");

        tableStudents = databaseDataSet.getTable("students");
        int studentsAmount = tableStudents.getRowCount();

        universityDBManager.dropStudentById("1");
        tableStudents = databaseDataSet.getTable("students");
        assertEquals(studentsAmount - 1, tableStudents.getRowCount());
    }

    @Test
    void createGroupHaveToCreateNewGroup() throws SQLException, DataSetException {
        tableGroups = databaseDataSet.getTable("groups");
        int groupsAmount = tableGroups.getRowCount();

        universityDBManager.createGroup("KT-12");
        tableGroups = databaseDataSet.getTable("groups");

        assertEquals(groupsAmount + 1, tableGroups.getRowCount());
    }

    @Test
    void createFacultyHaveToCreateNewFaculty() throws SQLException, DataSetException {
        tableFaculties = databaseDataSet.getTable("faculties");
        int facultiesAmount = tableFaculties.getRowCount();

        universityDBManager.createFaculty("Math", "Math is useful subject");
        tableFaculties = databaseDataSet.getTable("faculties");

        assertEquals(facultiesAmount + 1, tableFaculties.getRowCount());
    }

    @Test
    void addStudentToFacultyHaveToAddStudentToFaculty() throws SQLException, DataSetException {
        tableFacultiesStudents = databaseDataSet.getTable("faculties_students");
        int facultiesStudentsAmount = tableFaculties.getRowCount();

        universityDBManager.createStudent("Mr", "Le");
        universityDBManager.createFaculty("Faculty", "No description");
        universityDBManager.addStudentToFaculty(1, 1);

        tableFacultiesStudents = databaseDataSet.getTable("faculties_students");
        assertEquals(facultiesStudentsAmount + 1, tableFacultiesStudents.getRowCount());


    }
}