package com.foxminded.universitydatabase;

import com.foxminded.universitydatabase.db_layer.queries.QueriesToUniversityDB;
import com.foxminded.universitydatabase.user_layer.UserManager;

import java.sql.SQLException;

public class UniversityDataBase {
    private static UserManager userManager = new UserManager();
    private static QueriesToUniversityDB queriesToUniversityDB = null;

    public static void main(String[] args) {
        boolean exit = false;

        try {
            queriesToUniversityDB = new QueriesToUniversityDB();

            for (; !exit; ) {
                process();
                exit = userManager.getExitOrRestartChoice();
            }
        } catch (SQLException e) {
            System.out.println("Sorry :( DB Connection troubles");
            e.printStackTrace();
        }
    }

    private static void process() throws SQLException {
        userManager.printStartingMessage();
        String usersChoice = userManager.getStringFromUser().trim();

        if (usersChoice.equals("1")) {
            findGroupsWhereQuantityOfStudentsNotMoreThanX();
        }
        if (usersChoice.equals("2")) {
            findStudentsOnFaculty();
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

    private static void findGroupsWhereQuantityOfStudentsNotMoreThanX() {
        System.out.println("Enter the x value please");
        String input = userManager.getStringFromUser();
        // Work with db
    }

    private static void findStudentsOnFaculty() {
        String faculty = userManager.getFacultyChoiceFromUser();
        // Work with db
    }

    private static void newStudent() throws SQLException {
        System.out.println("Enter the name of new student please");
        String newStudentsName = userManager.getStringFromUser();
        System.out.println("Enter the surname of new student please");
        String newStudentsSurname = userManager.getStringFromUser();

        queriesToUniversityDB.createStudent(newStudentsName, newStudentsSurname);
        System.out.println(newStudentsName + " " + newStudentsSurname + " was added ;)");
    }

    private static void deleteStudent() throws SQLException{
        System.out.println("Write please id of the student who will be deleted");
        String id = userManager.getStringFromUser();

        System.out.println("Are you sure?");
        System.out.println("Yes - 1");
        System.out.println("No  - Something else");
        String sureOrNot = userManager.getStringFromUser();

        if (sureOrNot.equals("1")) {
            queriesToUniversityDB.dropStudentById(id);
            System.out.println("Student with id " + id + " was deleted");
        }
    }

    private static void addStudentToFaculty() throws SQLException{
        System.out.println("Enter students id please");
        int studentsId = Integer.valueOf(userManager.getStringFromUser());
        System.out.println("Enter faculties id please");
        int facultiesId = Integer.valueOf(userManager.getStringFromUser());

        queriesToUniversityDB.addStudentToTheFaculty(studentsId, facultiesId);
        System.out.println("Student was added to the faculty)");
    }

    private static void deleteStudentFromFaculty() throws SQLException{
        System.out.println("Enter students id please");
        int studentsId = Integer.valueOf(userManager.getStringFromUser());
        System.out.println("Enter faculties id please");
        int facultiesId = Integer.valueOf(userManager.getStringFromUser());

        queriesToUniversityDB.dropStudentFromTheFaculty(studentsId, facultiesId);
        System.out.println("Student was added to the faculty)");
    }
}
