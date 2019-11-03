package com.foxminded.universitydatabase.generators;

import com.foxminded.universitydatabase.db_layer.queries.QueriesToUniversityDB;

import java.sql.SQLException;
import java.util.List;

public class TestDataGenerator {
    private RandomsGenerator randomsGenerator = new RandomsGenerator();
    private QueriesToUniversityDB queriesToUniversityDB = new QueriesToUniversityDB();

    public TestDataGenerator() throws SQLException {
    }

    public void generate() throws SQLException {
        generateGroups();
        generateFaculties();
        generateStudents();
        randomPutStudentsToGroups();
    }

    private void generateGroups() throws SQLException {
        for (int i = 0; i < 10; i++) {
            String groupName = randomsGenerator.generateRandomGroupName();
            queriesToUniversityDB.createGroup(groupName);
        }
    }

    private void generateFaculties() throws SQLException {
        String[] faculyNames = new String[10];
        faculyNames[0] = "Faculty1";
        faculyNames[1] = "Faculty2";
        faculyNames[2] = "Faculty3";
        faculyNames[3] = "Faculty4";
        faculyNames[4] = "Faculty5";
        faculyNames[5] = "Faculty6";
        faculyNames[6] = "Faculty7";
        faculyNames[7] = "Faculty8";
        faculyNames[8] = "Faculty9";
        faculyNames[9] = "Faculty10";

        for (int i = 0; i < 10; i++) {
            queriesToUniversityDB.createFaculty(faculyNames[i], "Without description");
        }
    }

    private void generateStudents() throws SQLException {
        for (int i = 0; i < 200; i++) {
            String name = randomsGenerator.generateRandomName();
            String surname = randomsGenerator.generateRandomSurname();

            queriesToUniversityDB.createStudent(name, surname);
        }
    }

    private void randomPutStudentsToGroups() throws SQLException {
        List<Integer> groupsId = queriesToUniversityDB.getGroupsId();
        List<Integer> studentsId = queriesToUniversityDB.getStudentsId();

        for (int i = 0; i < studentsId.size(); i++) {
            int randomGroupId = randomsGenerator.getRandomListElementFromInput(groupsId);
            queriesToUniversityDB.addStudentToTheGroup(studentsId.get(i), randomGroupId);
        }
    }
}
