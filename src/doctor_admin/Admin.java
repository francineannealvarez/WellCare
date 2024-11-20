package doctor_admin;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import user.User;
import display.DisplayUtils;

public class Admin extends User{
    private static List<Doctor> doctors = new ArrayList<>();
    private static List<String> departments = new ArrayList<>();
    private static int doctorCounter = 0;
    private DisplayUtils display;

    public Admin(String name, String password) {
        super("admin", "adminpass");  
    }

    public static List<Doctor> getDoctors() {
        return doctors;
    }

    public static void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }

    public List<String> getDepartments() {
        return departments;
    }

    public DisplayUtils getDisplay(){
        return display;
    }

    public void initDepartments() {
        if (departments == null) {
            departments = new ArrayList<>();
        }
        departments.add("Neurology");
        departments.add("X-ray");
        departments.add("Dentistry");
    }

    public boolean signIn(Scanner scanner, DisplayUtils display) {
        display.printHeader("ADMIN LOGIN");
        System.out.print("Enter admin username: ");
        String inputUsername = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String inputPassword = scanner.nextLine();

        return this.getName().equals(inputUsername) && this.signIn(inputPassword);  // Using the signIn method from User class
    }

    public static void addDepartment(Scanner scanner, DisplayUtils display) {
        display.printHeader("ADD DEPARTMENT TO SYSTEM");
        System.out.print("Enter the name of the department to add: ");
        String department = scanner.nextLine();

        if (!departments.contains(department)) {
            departments.add(department);
            System.out.println("Department '" + department + "' has been added successfully.");
        } else {
            System.out.println("Department '" + department + "' already exists.");
        }
    }

    public static void removeDepartment(Scanner scanner, DisplayUtils display) {
        display.printHeader("REMOVE DEPARTMENT FROM SYSTEM");
        showDepartments(display);
        System.out.print("Enter the name of the department to remove: ");
        String department = scanner.nextLine();
    
        if (departments.contains(department)) {
            departments.remove(department);
            System.out.println("Department '" + department + "' has been removed successfully.");
        } else {
            System.out.println("Department '" + department + "' does not exist.");
        }
    }
   
    public void addDoctor(String doctorName, String departmentName) {
        if (departments.contains(departmentName)) {
            for (Doctor doctor : doctors) {
                if (doctor.getName().equalsIgnoreCase(doctorName)) {
                    System.out.println("Dr. " + doctorName + " already exists in the system.");
                    return;
                }
            }
    
            // Generate the unique ID for the doctor
            String doctorId = generateUniqueId();
    
            // Create a new doctor with the generated ID
            Doctor newDoctor = new Doctor(doctorName, departmentName, doctorId);
            System.out.println("Dr. " + doctorName + " added to " + departmentName + " department with ID: " + doctorId);
            doctors.add(newDoctor);
        } else {
            System.out.println("Department '" + departmentName + "' does not exist.");
        }
    }

    public Doctor getDoctorByName(String name){
        for (Doctor doctor : doctors) {
            if (doctor.getName().equalsIgnoreCase(name)) {
                return doctor;
            }
        }
        return null;
    }

    public static void removeDoctor(String name) {
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getName().equalsIgnoreCase(name)) {
                doctors.remove(i);
                System.out.println("Dr. " + name + " removed successfully.");
                return;
            }
        }
        System.out.println("Dr. " + name + " not found.");
    }

    public static void viewDoctors(DisplayUtils display) {
        display.printHeader("LIST OF DOCTORS");
    
        if (doctors.isEmpty()) {
            System.out.println("No doctors available.");
        } else {
            // Add headers for better readability
            System.out.printf("%-5s %-15s %-30s %-20s\n", "No.", "Doctor ID", "Doctor Name", "Department");
    
            // Display doctors with formatted output
            for (int i = 0; i < doctors.size(); i++) {
                Doctor doctor = doctors.get(i);
                System.out.printf("%-5d %-15s %-30s %-20s\n", 
                    (i + 1), doctor.getDoctorId(), "Dr. " + doctor.getName(), doctor.getDepartment());
            }
        }
    }
    
    
    public static void showDepartments(DisplayUtils display) {
        display.printHeader("LIST OF DEPARTMENTS");

        if (departments.isEmpty()) {
            System.out.println("No departments available.");
        } else {
            // Add a header for better readability
            System.out.printf("%-5s %-30s\n", "No.", "Department Name");

            // Display departments with formatted output
            for (int i = 0; i < departments.size(); i++) {
                System.out.printf("%-5d %-30s\n", (i + 1), departments.get(i));
            }
        }
    }    

    public void adminMenu(Scanner scanner, DisplayUtils display) {
    boolean exit = false;

        while (!exit) {
            display.printHeader("ADMIN MENU");
            System.out.println("1. Add Department");
            System.out.println("2. Remove Department");
            System.out.println("3. Add Doctor");
            System.out.println("4. Remove Doctor");
            System.out.println("5. View Doctors");
            System.out.println("6. View Departments");
            System.out.println("7. Exit");
            System.out.print("Please select an option by entering the corresponding number: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        addDepartment(scanner, display);
                        break;
                    case 2:
                        removeDepartment(scanner, display);
                        break;
                    case 3:
                        display.printHeader("ADD NEW DOCTOR TO SYSTEM");
                        System.out.print("Enter doctor's name: ");
                        String doctorName = scanner.nextLine();
                        System.out.print("Enter department for the doctor (Note: this is case sensitive): ");
                        String departmentName = scanner.nextLine();
                        addDoctor(doctorName, departmentName);
                        break;
                    case 4:
                        display.printHeader("REMOVE DOCTOR FROM SYSTEM");
                        System.out.print("Enter doctor's name to remove: ");
                        String nameToRemove = scanner.nextLine();
                        removeDoctor(nameToRemove);
                        break;
                    case 5:
                        viewDoctors(display);
                        break;
                    case 6:
                        showDepartments(display);
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); 
            }
        }
    }

    
    private static String generateUniqueId() {
        String year = java.time.LocalDate.now().getYear() + "";  // Current year
        doctorCounter++;  // Increment the global counter
        return "D" + year + String.format("%05d", doctorCounter);  // Format as DYYYY##### (e.g., D202400001)
    }

}
