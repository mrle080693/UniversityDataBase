package com.foxminded.universitydatabase;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.foxminded.universitydatabase.db_layer.managers.UniversityDBManager;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.File;
import java.sql.ResultSet;

public class TestUniversityDBManager {
    private static final String JDBC_DRIVER = org.postgresql.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/university_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";
    private UniversityDBManager universityDBManager = new UniversityDBManager();

    @BeforeClass
    public static void createSchema() throws Exception {
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, JDBC_DRIVER);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, JDBC_URL);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, USER);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, PASSWORD);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "chema.sql");
    }

    @Before
    public void importDataSet() throws Exception {
        IDataSet dataSet = readDataSet();
        cleanlyInsert(dataSet);
    }

    private IDataSet readDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("dataset.xml"));
    }

    private void cleanlyInsert(IDataSet dataSet) throws Exception {
        IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    @Test
    public void findsAndReadsExistingPersonByFirstName() throws Exception {
        ResultSet actual = universityDBManager.getAllStudents();

        FlatXmlDataSet expected = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("C:\\Java\\Projects\\Foxminded\\Mentoring\\UniversityDataBase\\src\\test\\resources\\dataset.xml"));

        assertEquals(expected, actual);
    }
}