package com.foxminded.universitydatabase;

import java.util.Scanner;

public class UserManager {
    public static void printStartingMessage() {
        System.out.println("__________ WELCOME _________");
        System.out.println("--  University Data Base  --");
        System.out.println("1 - Find groups where quantity of students <= x");
        System.out.println("2 - Find students which learn x subject");
        System.out.println("3 - Add new student");
        System.out.println("4 - Delete student by id");
        System.out.println("5 - Ad course for a student");
        System.out.println("6 - Delete course from student");
    }

    public static String getStringFromUser() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
