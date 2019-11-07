package com.foxminded.universitydatabase.generators;

import com.foxminded.universitydatabase.db_layer.managers.UniversityDBManager;

import java.sql.SQLException;
import java.util.List;

public class TestDataGenerator {
    private RandomsGenerator randomsGenerator = new RandomsGenerator();
    private UniversityDBManager universityDBManager = new UniversityDBManager();

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
            universityDBManager.createGroup(groupName);
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
            universityDBManager.createFaculty(faculyNames[i], "Without description");
        }
    }

    private void generateStudents() throws SQLException {
        for (int i = 0; i < 200; i++) {
            String name = randomsGenerator.generateRandomName();
            String surname = randomsGenerator.generateRandomSurname();

            universityDBManager.createStudent(name, surname);
        }
    }

    private void randomPutStudentsToGroups() throws SQLException {
        List<Integer> studentsId = universityDBManager.getStudentsId();

        for (Integer id : studentsId) {
            Integer groupId = universityDBManager.getNotFullGroupId();
            if (groupId != null) {
                universityDBManager.addStudentToTheGroup(id, groupId);
            } else {
                break;
            }
        }

        universityDBManager.disbandNotMoreXGroups(10);
    }
}
