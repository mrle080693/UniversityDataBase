package com.foxminded.universitydatabase.generators;

import java.util.Random;

class RandomsGenerator {
    private Random random = new Random();

    String generateRandomGroupName() {
        return String.valueOf(getRandomCharacterFromInput("ABCDEFGHIJ")) +
                getRandomCharacterFromInput("ABCDEFGHIJ") + "-" +
                getRandomCharacterFromInput("1234567890") +
                getRandomCharacterFromInput("1234567890");
    }

    String generateRandomName() {
        return getRandomArrayElementFromInput(getNames());
    }

    String generateRandomSurname() {
        return getRandomArrayElementFromInput(getSurnames());
    }

    private Character getRandomCharacterFromInput(String input) {
        return input.charAt(random.nextInt(input.length()));
    }

    private String getRandomArrayElementFromInput(String[] input) {
        return input[random.nextInt(input.length - 1)];
    }

    private String[] getNames() {
        String[] names = new String[10];

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

        return names;
    }

    private String[] getSurnames() {
        String[] surnames = new String[10];

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

        return surnames;
    }
}
