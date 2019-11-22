package com.foxminded.universitydatabase;

import com.foxminded.universitydatabase.db_layer.managers.UniversityDBManager;
import config.DBUnitConfig;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TestUniversityDBManager extends DBUnitConfig {

    private UniversityDBManager universityDBManager = new UniversityDBManager();

    // переопределяем метод для того, чтобы передать в него наш набор данных для конкретного теста.
    @Before
    public void setUp() throws Exception {
        super.setUp();

        // создает набор данных с переданного ему в потоке файла xml который содержит в себе описание данных,
        // которые должны будут записаны в соответствующие таблички.
        // Состоит с тега верхнего уровня dataset внутри которого находятся теги с атрибутами.
        // Название тега –  имя таблички, атрибуты внутри – атрибуты таблички в БД.
        beforeData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("C:\\Java\\Projects\\Foxminded\\Mentoring\\UniversityDataBase\\src\\test\\resources\\person-data.xml"));

        tester.setDataSet(beforeData);
        tester.onSetup();
    }

    public TestUniversityDBManager(String name) {
        super(name);
    }

    @Test
    public void testGetAll() throws Exception {
        // создаем набор ожидаемых данных
        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("com/devcolibri/entity/person/person-data.xml"));

        // Создаем набор данных с существующих данных в БД:
        IDataSet actualData = tester.getConnection().createDataSet();

        // В случаях, когда сравниваются таблички или наборы данных в которых присутствует поле
        // типа auto increment могут быть ошибки при прямом сравнивании так как,
        // при удалении одного поля и добавления нового с БД,  индекс будет расти,
        // что не будет соответствовать  индексу с набора данных в xml файле.
        // Поэтому, одним решением есть игнорирование поля, как мы делали выше.
        String[] ignore = {"id"};
        Assertion.assertEqualsIgnoreCols(expectedData, actualData, "students", ignore);
    }

    //others tests
}
