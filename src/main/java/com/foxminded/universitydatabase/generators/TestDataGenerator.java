package com.foxminded.universitydatabase.generators;

import com.foxminded.universitydatabase.db_layer.managers.UniversityDBManager;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class TestDataGenerator {
    private Random random = new Random();
    private static String[] names = new String[10];
    private static String[] surnames = new String[10];
    private static String[] facultyNames = new String[10];
    private UniversityDBManager universityDBManager = null;

    static {
        names[0] = "StudentsName1";
        names[1] = "StudentsName2";
        names[2] = "StudentsName3";
        names[3] = "StudentsName4";
        names[4] = "StudentsName5";
        names[5] = "StudentsName6";
        names[6] = "StudentsName7";
        names[7] = "StudentsName8";
        names[8] = "StudentsName9";
        names[9] = "StudentsName10";

        surnames[0] = "StudentsSurname1";
        surnames[1] = "StudentsSurname2";
        surnames[2] = "StudentsSurname3";
        surnames[3] = "StudentsSurname4";
        surnames[4] = "StudentsSurname5";
        surnames[5] = "StudentsSurname6";
        surnames[6] = "StudentsSurname7";
        surnames[7] = "StudentsSurname8";
        surnames[8] = "StudentsSurname9";
        surnames[9] = "StudentsSurname10";

        facultyNames[0] = "Faculty1";
        facultyNames[1] = "Faculty2";
        facultyNames[2] = "Faculty3";
        facultyNames[3] = "Faculty4";
        facultyNames[4] = "Faculty5";
        facultyNames[5] = "Faculty6";
        facultyNames[6] = "Faculty7";
        facultyNames[7] = "Faculty8";
        facultyNames[8] = "Faculty9";
        facultyNames[9] = "Faculty10";
    }

    public TestDataGenerator() throws SQLException {
    }

    public void generate(UniversityDBManager universityDBManager) throws SQLException {
        this.universityDBManager = universityDBManager;
        generateGroups();
        generateFaculties();
        generateStudents();
        randomPutStudentsToGroups();
    }

    private void generateGroups() throws SQLException {
        for (int i = 0; i < 10; i++) {
            String groupName = generateRandomGroupName();
            universityDBManager.createGroup(groupName);
        }
    }

    private void generateFaculties() throws SQLException {
        for (int i = 0; i < 10; i++) {
            universityDBManager.createFaculty(facultyNames[i], "Without description");
        }
    }

    private void generateStudents() throws SQLException {
        for (int i = 0; i < 200; i++) {
            String name = generateRandomName();
            String surname = generateRandomSurname();

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

    private String generateRandomGroupName() {
        return String.valueOf(getRandomCharacterFromInput("ABCDEFGHIJ")) +
                getRandomCharacterFromInput("ABCDEFGHIJ") + "-" +
                getRandomCharacterFromInput("1234567890") +
                getRandomCharacterFromInput("1234567890");
    }

    private String generateRandomName() {
        return getRandomArrayElementFromInput(names);
    }

    private String generateRandomSurname() {
        return getRandomArrayElementFromInput(surnames);
    }


    private Character getRandomCharacterFromInput(String input) {
        return input.charAt(random.nextInt(input.length()));
    }

    private String getRandomArrayElementFromInput(String[] input) {
        return input[random.nextInt(input.length - 1)];
    }
}
