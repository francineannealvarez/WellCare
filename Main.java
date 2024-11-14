import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin();
        Doctor doctor = new Doctor(null, null);
        admin.initDepartments();
        PatientDatabase patientDatabase = new PatientDatabase(); 

        int choice;
        boolean exit = false;

        while (!exit) {
            System.out.println("\nWelcome to the Hospital Booking System!");
            System.out.println("1. Patient Menu");
            System.out.println("2. Doctor");
            System.out.println("3. Admin");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        Patient.patientMenu(scanner, patientDatabase, admin, doctor); 
                        break;
                    case 2:
                        Doctor.DoctorMenu(scanner, admin);
                        break;
                    case 3:
                        if (Admin.signIn(scanner)) {
                            admin.adminMenu(scanner);
                        } else {
                            System.out.println("Sign-in failed. Please check your username and password.");
                        }
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
        scanner.close();
    }
}
