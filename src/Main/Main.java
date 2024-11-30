import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;
import panel.PatientPanel;
import admin.Admin; 
import panel.DoctorPanel;
import healthtips.HealthTips;
import display.DisplayUtils;
import connection.AdminDao;
import connection.AdminDaoJdbc;
import connection.DatabaseConnection;
import model.Patient;
import model.Doctor;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DisplayUtils display = new DisplayUtils();
        Patient patient = new Patient(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, false, false, null);
        Doctor doctor = new Doctor(null, null, null);
        
        try (Connection connection = DatabaseConnection.getConnection()) {
            AdminDao adminDao = new AdminDaoJdbc(connection);
            DoctorPanel doctorPanel = new DoctorPanel(doctor, connection);
            PatientPanel patientPanel = new PatientPanel(patient, connection);

            Admin admin = new Admin("admin", "adminpass", adminDao, display);
            
            boolean exit = false;
            try {
                while (!exit) {
                    display.printHeader("Welcome to the Hospital Booking System!");
                    System.out.println("1. Patient Menu");
                    System.out.println("2. Doctor");
                    System.out.println("3. Admin");
                    System.out.println("4. Health Tips");
                    System.out.println("5. Exit");
                    System.out.print("Choose an option: ");
                    int choice = scanner.nextInt(); 
                    scanner.nextLine(); 
                
                    switch (choice) {
                        case 1 -> patientPanel.patientMenu(scanner); 
                        case 2 -> doctorPanel.signIn(scanner, admin); 
                        case 3 -> {
                            if (admin.signIn(scanner)) {
                                admin.adminMenu(scanner);
                            } else {
                                System.out.println("Sign-in failed. Please check your username and password.");
                            }
                        }
                        case 4 -> HealthTips.showCategories(scanner, display); 
                        case 5 -> {
                            exit = true;
                            System.out.println("Exiting...");
                        }
                        default -> System.out.println("Invalid option. Please try again.");
                    }
                }
             } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); 
            }   
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while connecting to the database.");
        } finally {
            scanner.close(); 
        }
    }
}
