import java.util.InputMismatchException;
import java.util.Scanner;
import patient.PatientDatabase;
import patient.Patient;
import doctor_admin.Admin; 
import doctor_admin.Doctor;
import healthtips.HealthTips;
import display.DisplayUtils;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin(null, null);
        Doctor doctor = new Doctor(null,null, null);
        admin.initDepartments();
        PatientDatabase patientDatabase = new PatientDatabase();
        DisplayUtils display = new DisplayUtils();

        boolean exit = false;

        while (!exit) {
            display.printHeader("Welcome to the Hospital Bookung System!");
            System.out.println("1. Patient Menu");
            System.out.println("2. Doctor");
            System.out.println("3. Admin");
            System.out.println("4. Healthy Tips");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        Patient.patientMenu(scanner, patientDatabase, admin, doctor, display); 
                        break;
                    case 2:
                        Doctor.DoctorMenu(scanner, admin, patientDatabase, display);
                        break;
                    case 3:
                        if (admin.signIn(scanner, display)) {
                            admin.adminMenu(scanner, display);
                        } else {
                            System.out.println("Sign-in failed. Please check your username and password.");
                        }
                        break; 
                    case 4:
                        HealthTips.showCategories(scanner, display); 
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
        scanner.close();
    }
}
