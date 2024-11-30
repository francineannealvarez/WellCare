package healthtips;

import java.util.InputMismatchException;
import java.util.Scanner;
import display.DisplayUtils;

public abstract class HealthTips {
    protected String title; 

    public HealthTips(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public abstract void displayTips(DisplayUtils display);

    public static void showCategories(Scanner scanner, DisplayUtils display) {
        LowBloodTips lowBlood = new LowBloodTips();
        HighBloodTips highBlood = new HighBloodTips();
        DietingTips dieting = new DietingTips();
        BoostImmunityTips boost = new BoostImmunityTips();
        boolean exit = false;

        while (!exit) {
            display.printHeader("Healthy Food Tips Categories");
            System.out.println("1. For Low Blood");
            System.out.println("2. For High Blood");
            System.out.println("3. For Weight Loss");
            System.out.println("4. For Boosting Immune System");
            System.out.println("5. Exit");

            System.out.print("Please select a category (1-3): ");
            
            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("\nCategory: " + lowBlood.getTitle());
                        lowBlood.displayTips(display);
                        break;
                    case 2:
                        System.out.println("\nCategory: " + highBlood.getTitle());
                        highBlood.displayTips(display);
                        break;
                    case 3:
                        System.out.println("\nCategory: " + dieting.getTitle());
                        dieting.displayTips(display);
                        break;
                    case 4:
                        System.out.println("\nCategory: " +boost.getTitle());
                        boost.displayTips(display);
                        break;
                    case 5:
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

