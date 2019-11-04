package com.foxminded.universitydatabase.user_layer;

import java.util.Scanner;

public class UserManager {
    private Scanner scanner = new Scanner(System.in);

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
        return scanner.nextLine();
    }

    public void closeScanner(){
        scanner.close();
    }
}
