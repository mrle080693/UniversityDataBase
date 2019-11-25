package com.foxminded.universitydatabase.generators;

import com.foxminded.universitydatabase.db_layer.managers.UniversityDBManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {
    private Random random = new Random();
    private static List<String> names = new ArrayList<>();
    private static List<String> surnames = new ArrayList<>();
    private static List<String> facultyNames = new ArrayList<>();
    private UniversityDBManager universityDBManager = null;

    static {
        names.add("StudentsName1");
        names.add("StudentsName2");
        names.add("StudentsName3");
        names.add("StudentsName4");
        names.add("StudentsName5");
        names.add("StudentsName6");
        names.add("StudentsName7");
        names.add("StudentsName8");
        names.add("StudentsName9");
        names.add("StudentsName10");

        surnames.add("StudentsSurname1");
        surnames.add("StudentsSurname2");
        surnames.add("StudentsSurname3");
        surnames.add("StudentsSurname4");
        surnames.add("StudentsSurname6");
        surnames.add("StudentsSurname7");
        surnames.add("StudentsSurname8");
        surnames.add("StudentsSurname9");
        surnames.add("StudentsSurname10");

        facultyNames.add("Faculty1");
        facultyNames.add("Faculty2");
        facultyNames.add("Faculty3");
        facultyNames.add("Faculty4");
        facultyNames.add("Faculty5");
        facultyNames.add("Faculty6");
        facultyNames.add("Faculty7");
        facultyNames.add("Faculty8");
        facultyNames.add("Faculty9");
        facultyNames.add("Faculty10");

    }

    public DataGenerator() throws SQLException {
    }

    public void generate() throws SQLException {
        universityDBManager = new UniversityDBManager();
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
            universityDBManager.createFaculty(facultyNames.get(i), "Without description");
        }
    }

    private void generateStudents() throws SQLException {
        for (int i = 0; i < 200; i++) {
            String name = getRandomName();
            String surname = getRandomSurname();

            universityDBManager.createStudent(name, surname);
        }
    }

    private void randomPutStudentsToGroups() throws SQLException {
        List<Integer> studentsId = universityDBManager.getStudentsId();

        for (Integer id : studentsId) {
            Integer groupId = universityDBManager.getNotFullGroupId().get(getRandomIntListElementFromInput(studentsId));
            if (groupId != null) {
                universityDBManager.addStudentToGroup(id, groupId);
            } else {
                break;
            }
        }

        universityDBManager.disbandGroupsByCount(10);
    }

    private String generateRandomGroupName() {
        return String.valueOf(getRandomCharacterFromInput("ABCDEFGHIJ")) +
                getRandomCharacterFromInput("ABCDEFGHIJ") + "-" +
                getRandomCharacterFromInput("1234567890") +
                getRandomCharacterFromInput("1234567890");
    }

    private String getRandomName() {
        return getRandomListElementFromInput(names);
    }

    private String getRandomSurname() {
        return getRandomListElementFromInput(surnames);
    }

    private Character getRandomCharacterFromInput(String input) {
        return input.charAt(random.nextInt(input.length()));
    }

    private String getRandomListElementFromInput(List<String> input) {
        return input.get(random.nextInt(input.size() - 1));
    }

    private Integer getRandomIntListElementFromInput(List<Integer> input) {
        return input.get(random.nextInt(input.size() - 1));
    }
}
