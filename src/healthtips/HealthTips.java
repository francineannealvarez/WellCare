package healthtips;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class HealthTips {
    protected String title; 

    public HealthTips(String title) {
        this.title = title;
    }

    public abstract void displayTips();


    public String getTitle() {
        return title;
    }

    public static void showCategories(Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\nHealthy Food Tips Categories:");
            System.out.println("1. For Low Blood");
            System.out.println("2. For High Blood");
            System.out.println("3. For Weight Loss");
            System.out.println("4. Exit");

            System.out.print("Please select a category (1-3): ");
            
            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        LowBloodTips lowBlood = new LowBloodTips();
                        System.out.println("\nCategory: " + lowBlood.getTitle());
                        lowBlood.displayTips();
                        break;
                    case 2:
                        HighBloodTips highBlood = new HighBloodTips();
                        System.out.println("\nCategory: " + highBlood.getTitle());
                        highBlood.displayTips();
                        break;
                    case 3:
                        DietingTips dieting = new DietingTips();
                        System.out.println("\nCategory: " + dieting.getTitle());
                        dieting.displayTips();
                        break;
                    case 4:
                        exit = true;
                        System.out.println("Exiting...");
                        break; 
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }
}
