package com.foxminded.universitydatabase;

// Все таблицы каждый раз удаляются
// 1 - вводит кол - во студентов. Найти группы где столько же или мен
// 2 - выбирает предмет. Показывает всех студентов изуч этот предмет
// НЕ УСЛОЖНЯТЬ !!!
// взаимодействие с пользователем должно быть
// МАКСИМАЛЬНО ПРОСТО !!!!!!!!

import java.util.Scanner;

public class UniversityDataBase {
    private static UserManager userManager = new UserManager();

    public static void main(String[] args) {
        boolean exit = false;
        for (;!exit;) {
            process();
            exit = userManager.getExitOrRestartChoice();
        }
    }

    private static void process() {
        userManager.printStartingMessage();
        String usersChoice = userManager.getStringFromUser().trim();

        if (usersChoice.equals("1")) {
            findGroupsWhereXStudents();
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

    private static void findGroupsWhereXStudents() {
        System.out.println("Enter the x value please");
        String input = userManager.getStringFromUser();
        // Work with db
    }

    private static void findStudentsOnFaculty() {
        String faculty = userManager.getFacultyChoiceFromUser();
        // Work with db
    }

    private static void newStudent() {
        System.out.println("Enter the name of new student please");
        String newStudentsName = userManager.getStringFromUser();
        System.out.println("Enter the surname of new student please");
        String newStudentsSurname = userManager.getStringFromUser();
        // Work with db
    }

    private static void deleteStudent() {
        System.out.println("Write please id of the student who will be deleted");
        String id = userManager.getStringFromUser();

        System.out.println("Are you sure?");
        System.out.println("Yes - 1");
        System.out.println("No  - Something else");
        String sureOrNot = userManager.getStringFromUser();

        if (sureOrNot.equals("1")) {
            // Work with db
            System.out.println("Student with id " + id + " was deleted");
        }
    }

    private static void addStudentToFaculty() {
        System.out.println("Enter students id please");
        int studentsID = Integer.valueOf(userManager.getStringFromUser());
        String faculty = userManager.getFacultyChoiceFromUser();
        // Work with db
    }

    private static void deleteStudentFromFaculty() {
        String faculty = userManager.getFacultyChoiceFromUser();
        System.out.println("Enter students id please");
        int studentsID = Integer.valueOf(userManager.getStringFromUser());
        // Work with db
    }
}
