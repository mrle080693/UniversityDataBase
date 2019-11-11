package com.foxminded.universitydatabase;

import com.foxminded.universitydatabase.db_layer.managers.UniversityDBManager;
import com.foxminded.universitydatabase.generators.TestDataGenerator;
import com.foxminded.universitydatabase.user_layer.UserInputManager;

import java.sql.SQLException;
import java.util.List;

public class UniversityDataBase {
    private static UserInputManager userInputManager = new UserInputManager();
    private static UniversityDBManager universityDBManager = null;

    public static void main(String[] args) {
        try {
            universityDBManager = new UniversityDBManager();
            TestDataGenerator testDataGenerator = new TestDataGenerator();
            testDataGenerator.generate(universityDBManager);

            boolean exit = false;
            for (; !exit; ) {
                process();
                exit = userInputManager.getExitOrRestartChoice();
            }

        } catch (SQLException e) {
            System.out.println("Sorry :( DB Connection troubles");
            System.out.println(e.getMessage());

        } finally {
            userInputManager.closeScanner();
        }
    }

    private static void process() throws SQLException {
        userInputManager.printMenu();
        String usersChoice = userInputManager.getStringFromUser("").trim();

        if (usersChoice.equals("1")) {
            printNotMoreXGroups();
        }
        if (usersChoice.equals("2")) {
            printStudentsFromFaculty();
        }
        if (usersChoice.equals("3")) {
            newStudent();
        }
        if (usersChoice.equals("4")) {
            deleteStudent();
        }
        if (usersChoice.equals("5")) {
            addStudentToFaculty();
        }
        if (usersChoice.equals("6")) {
            deleteStudentFromFaculty();
        }
    }

    private static void printNotMoreXGroups() throws SQLException {
        int x = Integer.parseInt(userInputManager.getStringFromUser("Enter the x value please"));
        List<String> groups = universityDBManager.getNotMoreXGroups(x);

        for (String group : groups) {
            System.out.println(group);
        }
    }

    private static void printStudentsFromFaculty() throws SQLException {
        int facultyId = Integer.parseInt(userInputManager.getStringFromUser("Write please faculty id"));
        List<String> students = universityDBManager.getStudentsFromFaculty(facultyId);

        for (String student : students) {
            System.out.println(student);
        }
    }

    private static void newStudent() throws SQLException {
        String newStudentsName = userInputManager.getStringFromUser("Enter the name of new student please");
        String newStudentsSurname = userInputManager.getStringFromUser("Enter the surname of new student please");

        universityDBManager.createStudent(newStudentsName, newStudentsSurname);
    }

    private static void deleteStudent() throws SQLException {
        String id = userInputManager.getStringFromUser("Write please id of the student who will be deleted");
        String sureOrNot = userInputManager.getStringFromUser(
                "Are you sure?" + "\n" +
                        "Yes - 1" + "\n" +
                        "No  - Something else");

        if (sureOrNot.equals("1")) {
            universityDBManager.dropStudentById(id);
        }
    }

    private static void addStudentToFaculty() throws SQLException {
        int studentsId = Integer.valueOf(userInputManager.getStringFromUser("Enter students id please"));
        int facultiesId = Integer.valueOf(userInputManager.getStringFromUser("Enter faculties id please"));

        universityDBManager.addStudentToTheFaculty(studentsId, facultiesId);
    }

    private static void deleteStudentFromFaculty() throws SQLException {
        int studentsId = Integer.valueOf(userInputManager.getStringFromUser("Enter students id please"));
        int facultiesId = Integer.valueOf(userInputManager.getStringFromUser("Enter faculties id please"));

        universityDBManager.dropStudentFromTheFaculty(studentsId, facultiesId);
    }
}
