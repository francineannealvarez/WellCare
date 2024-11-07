import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PatientDatabase patientDatabase = new PatientDatabase(); // Create an instance of PatientDatabase

        int choice;
        do {
            System.out.println("Welcome to the Hospital Booking System!");
            System.out.println("1. Patient Menu");
            System.out.println("2. Doctor");
            System.out.println("3. Admin");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); 
            
            switch (choice) {
                case 1:
                    Patient.patientMenu(scanner, patientDatabase); 
                    break;
                case 2:
                    Doctor.DoctorMenu();
                    break;
                case 3:
                    if (Admin.signIn(scanner)) {
                        Admin.adminMenu(scanner);
                    } else {
                        System.out.println("Sign-in failed. Please check your username and password.");
                    }
                    break; 
                case 4:
                    System.out.println("Exiting...");
                    break; 
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 4);
    }}
     //
