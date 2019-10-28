package com.foxminded.universitydatabase.user_layer;

import java.util.Scanner;

public class UserManager {
    public void printStartingMessage() {
        System.out.println("__________ WELCOME _________");
        System.out.println("--  University Data Base  --");
        System.out.println("1 - Find groups where quantity of students <= x");
        System.out.println("2 - Find students which study on faculty");
        System.out.println("3 - New student");
        System.out.println("4 - Delete student by id");
        System.out.println("5 - Add student to the faculty");
        System.out.println("6 - Delete student from the faculty");
    }

    public String getFacultyChoiceFromUser() {
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

    public Boolean getExitOrRestartChoice() {
        boolean exit = false;
        System.out.println("Restart - 1");
        System.out.println("Exit    - Something else");

        String input = getStringFromUser();
        if (!input.equals("1")) {
            exit = true;
            System.out.println("Good bye ;)");
        }
        return exit;
    }

    public String getStringFromUser() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
