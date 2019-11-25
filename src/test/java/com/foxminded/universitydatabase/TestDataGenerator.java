package com.foxminded.universitydatabase;

import com.foxminded.universitydatabase.db_layer.managers.UniversityDBManager;
import com.foxminded.universitydatabase.generators.DataGenerator;
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

class TestDataGenerator {
    private static final String URL = "hardcoded";
    private static final String USERNAME = "hardcoded";
    private static final String PASSWORD = "hardcoded";

    private static IDataSet databaseDataSet = null;
    private static UniversityDBManager universityDBManager = new UniversityDBManager();

    @BeforeAll
    static void initializer() throws SQLException, DatabaseUnitException {
        universityDBManager.init();
        DataGenerator dataGenerator = new DataGenerator();
        dataGenerator.generate();

        Connection jdbcConnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
        databaseDataSet = connection.createDataSet();
    }

    @Test
    void generateHaveToGenerateGroups() throws DataSetException {
        ITable tableGroups = databaseDataSet.getTable("groups");
        int groupsAmount = tableGroups.getRowCount();

        assertEquals(10, groupsAmount);
    }

    @Test
    void generateHaveToGenerateFaculties() throws DataSetException {
        ITable tableFaculties = databaseDataSet.getTable("faculties");
        int facultiesAmount = tableFaculties.getRowCount();

        assertEquals(10, facultiesAmount);
    }

    @Test
    void generateHaveToGenerateStudents() throws DataSetException {
        ITable tableStudents = databaseDataSet.getTable("students");
        int studentsAmount = tableStudents.getRowCount();

        assertEquals(200, studentsAmount);
    }

    @Test
    void generateHaveToPutStudentsToGroups() throws DataSetException {
        ITable tableGroupsStudents = databaseDataSet.getTable("groups_students");
        int studentsInGroupsAmount = tableGroupsStudents.getRowCount();

        assertEquals(200, studentsInGroupsAmount);
    }
}
