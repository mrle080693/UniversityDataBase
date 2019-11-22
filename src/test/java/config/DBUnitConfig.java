package config;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;

import java.io.IOException;
import java.util.Properties;

public class DBUnitConfig extends DBTestCase {
    // объект, функционалом которого мы будем проводить сравнивание табличек и бд.
    protected IDatabaseTester tester;
    // здесь мы будем хранить наши данные для БД. Настройки подключения
    private Properties prop;
    // объект, который содержит наши данные для инициализации бд перед выполнением теста.
    protected IDataSet beforeData;

    // в этом методе мы инициализируем данные, необходимые перед выполнением теста.
    // Здесь мы определяем наш тестер
    @Before
    public void setUp() throws Exception {
        tester = new JdbcDatabaseTester(prop.getProperty("org.postgresql.Driver"),
                prop.getProperty("jdbc:postgresql://localhost:5432/university_db"),
                prop.getProperty("postgres"),
                prop.getProperty("root"));
    }

    // конструктор инициализирует нашу БД в системе для дальнейшего получения доступа и возможности
    // в дальнейшем осуществлять взаимодействия.
    public DBUnitConfig(String name) {
        super(name);
        prop = new Properties();
        try {
            prop.load(Thread.currentThread()
                    .getContextClassLoader().getResourceAsStream("db.config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, prop.getProperty("org.postgresql.Driver"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, prop.getProperty("jdbc:postgresql://localhost:5432/university_db"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, prop.getProperty("postgres"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, prop.getProperty("root"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "");
    }

    // возвращает наш набор данных
    @Override
    protected IDataSet getDataSet() throws Exception {
        return beforeData;
    }

    // очищает БД после выполнения тестов
    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }
}
