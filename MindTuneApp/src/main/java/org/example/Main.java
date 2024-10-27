package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize Firebase and SQLite
        FirebaseInitializer.initializeFirebase();
        SQLiteManager.createDatabase();
        SQLiteManager.createTable();

        while (true) {
            System.out.println("Choose an action: ");
            System.out.println("1. Add Session Data");
            System.out.println("2. Retrieve Data from Firebase");
            System.out.println("3. Retrieve Data from SQLite");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.println("Enter Focus Duration: ");
                    int focusDuration = scanner.nextInt();
                    System.out.println("Enter Number of Distractions: ");
                    int numDistractions = scanner.nextInt();
                    System.out.println("Enter Mindfulness Level (1-5): ");
                    int mindfulnessLevel = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    System.out.println("Enter Condition: ");
                    String condition = scanner.nextLine();
                    System.out.println("Enter Task: ");
                    String task = scanner.nextLine();

                    FirebaseInitializer.addSessionToFirestore(focusDuration, numDistractions, mindfulnessLevel, condition, task);
                    SQLiteManager.addSessionToSQLite(focusDuration, numDistractions, mindfulnessLevel, condition, task);
                    break;

                case 2:
                    FirebaseInitializer.getSessionsFromFirestore();
                    break;

                case 3:
                    SQLiteManager.getSessionsFromSQLite();
                    break;

                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
