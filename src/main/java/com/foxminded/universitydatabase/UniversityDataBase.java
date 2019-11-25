package com.foxminded.universitydatabase;

import com.foxminded.universitydatabase.db_layer.managers.UniversityDBManager;
import com.foxminded.universitydatabase.generators.DataGenerator;
import com.foxminded.universitydatabase.user_layer.UserInputManager;

import java.sql.SQLException;
import java.util.List;

public class UniversityDataBase {
    private static UserInputManager userInputManager = new UserInputManager();
    private static UniversityDBManager universityDBManager = null;

    public static void main(String[] args) {
        try {
            universityDBManager = new UniversityDBManager();
            universityDBManager.init();
            DataGenerator dataGenerator = new DataGenerator();
            dataGenerator.generate();

            processUserInput();

        } catch (SQLException e) {
            System.out.println("Sorry :( DB Connection troubles");
            System.out.println(e.getMessage());

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

            try {
                processUserInput();

            } catch (SQLException e1) {
                System.out.println("Sorry :( DB Connection troubles");
            }
        }
    }

    private static void processUserInput() throws SQLException {
        boolean exit = false;

        while (!exit) {
            userInputManager.printMenu();
            String usersChoice = userInputManager.getStringFromUser("").trim();

            if (usersChoice.equals("1")) {
                printGroupsByStudentAmount();
            }
            if (usersChoice.equals("2")) {
                printStudentsByFaculty();
            }
            if (usersChoice.equals("3")) {
                addStudent();
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

            exit = userInputManager.getExitOrRestartChoice();
        }

        userInputManager.closeScanner();
    }

    private static void printGroupsByStudentAmount() throws SQLException {
        int x = userInputManager.getIntFromUser("Enter the x value please");
        List<String> groups = universityDBManager.getGroupsByStudentsAmount(x);

        for (String group : groups) {
            System.out.println(group);
        }
    }

    private static void printStudentsByFaculty() throws SQLException {
        int facultyId = userInputManager.getIntFromUser("Write please faculty id");
        List<String> students = universityDBManager.getStudentsByFacultyId(facultyId);

        for (String student : students) {
            System.out.println(student);
        }
    }

    private static void addStudent() throws SQLException {
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
        int studentsId = userInputManager.getIntFromUser("Enter students id please");
        int facultiesId = userInputManager.getIntFromUser("Enter faculties id please");

        universityDBManager.addStudentToFaculty(studentsId, facultiesId);
    }

    private static void deleteStudentFromFaculty() throws SQLException {
        int studentsId = userInputManager.getIntFromUser("Enter students id please");
        int facultiesId = userInputManager.getIntFromUser("Enter faculties id please");

        universityDBManager.dropStudentFromFaculty(studentsId, facultiesId);
    }
}
