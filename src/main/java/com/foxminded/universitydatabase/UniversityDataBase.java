package com.foxminded.universitydatabase;

// Все таблицы каждый раз удаляются
// 1 - вводит кол - во студентов. Найти группы где столько же или мен
// 2 - выбирает предмет. Показывает всех студентов изуч этот предмет
// НЕ УСЛОЖНЯТЬ !!!
// взаимодействие с пользователем должно быть
// МАКСИМАЛЬНО ПРОСТО !!!!!!!!

import java.util.Scanner;

public class UniversityDataBase {
    public static void main(String[] args) {
        process();
    }

    // Сделать читабельно
    // Много подробностей
    // В этом методе должна быть краткая логика читаемая как обычный английский тест
    private static void process() {
        printStartingMessage();
        String usersChoice = getStringFromUser();
        String dataFromUser = null;

        if (usersChoice.equals("1")) {
            //
            System.out.println("Enter the x value please");
            dataFromUser = getStringFromUser();
            // Work with db
        }
        if (usersChoice.equals("2")) {
            String faculty = getFacultyChoiceFromUser();
            // Work with db
        }
        if (usersChoice.equals("3")) {
            System.out.println("Enter the name of new student please");
            String newStudentsName = getStringFromUser();
            System.out.println("Enter the surname of new student please");
            String newStudentsSurname = getStringFromUser();
            // Work with db
        }
        if (usersChoice.equals("4")) {
            System.out.println("Enter students id please");
            int studentsID = Integer.valueOf(getStringFromUser());
            // Work with db
        }
        if (usersChoice.equals("5")) {
            System.out.println("Enter students id please");
            int studentsID = Integer.valueOf(getStringFromUser());
            String faculty = getFacultyChoiceFromUser();
            // Work with db
        }
        if (usersChoice.equals("6")) {
            String faculty = getFacultyChoiceFromUser();
            System.out.println("Enter students id please");
            int studentsID = Integer.valueOf(getStringFromUser());
            // Work with db
        }
        if (usersChoice.equals("7")) {
            System.out.println("Good bye ;)");
        } else {
            // Exception не нужно по тому что не нужно чтобы процесс выполнения программы прирывался
            printWrongInputMessage();
        }

        if (!usersChoice.equals("7")) {
            getExitOrRestartChoice();
        }
    }

    private static void printStartingMessage() {
        System.out.println("__________ WELCOME _________");
        System.out.println("--  University Data Base  --");
        System.out.println("1 - Find groups where quantity of students <= x");
        System.out.println("2 - Find students which study on faculty");
        System.out.println("3 - New student");
        System.out.println("4 - Delete student by id");
        System.out.println("5 - Add student to the faculty");
        System.out.println("6 - Delete student from the faculty");
        System.out.println("7 - Exit");
    }

    private static void findGroups() {
        System.out.println("Write x please");
        String input = getStringFromUser();
        System.out.println("Will find groups where quantity of students is less than x");
    }

    private static void addNewStudent() {
        System.out.println("Write please the name");
        String name = getStringFromUser();
        System.out.println("Write please the surName");
        String surName = getStringFromUser();
        System.out.println(name + " " + surName + " was added ;)");
    }

    private static void deleteStudent() {
        System.out.println("Write please id of the student who will be deleted");
        String id = getStringFromUser();

        System.out.println("Are you sure?");
        System.out.println("Yes - 1");
        System.out.println("No  - Something else");
        String sureOrNot = getStringFromUser();

        if (sureOrNot.equals("1")) {
            System.out.println("Student with id " + id + " was deleted");
        }
    }


    private static void getExitOrRestartChoice() {
        System.out.println("Restart - 1");
        System.out.println("Exit    - Something else");

        String input = getStringFromUser();
        if (input.equals("1")) {
            process();
        } else {
            System.out.println("Good bye ;)");
        }
    }

    private static void printWrongInputMessage() {

    }

    private static String getFacultyChoiceFromUser() {
        String result = null;
        String[] faculties = new String[10];
        faculties[0] = "Faculty 1";
        faculties[1] = "Faculty 2";
        faculties[2] = "Faculty 3";
        faculties[3] = "Faculty 4";
        faculties[4] = "Faculty 5";
        faculties[5] = "Faculty 6";
        faculties[6] = "Faculty 7";
        faculties[7] = "Faculty 8";
        faculties[8] = "Faculty 9";
        faculties[9] = "Faculty 10";

        System.out.println("Сhoose faculty and press the enter key");

        for (int i = 0; i <= faculties.length - 1; i++) {
            if (i != 9) {
                System.out.println(i + 1 + "  - " + faculties[i]);
            } else {
                System.out.println(i + 1 + " - " + faculties[i]);
            }
        }

        String usersChoice = getStringFromUser().trim();

        for (int i = 0; i <= faculties.length - 1; i++)
            if (usersChoice.equals(String.valueOf(i + 1))) {
                result = faculties[i];
            }
        if (result == null) {
            System.out.println("Sorry ;( Wrong input");
        }

        return result;
    }

    private static String getStringFromUser() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
