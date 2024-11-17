package doctor_admin;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import user.User;

public class Admin extends User{
    private static List<Doctor> doctors = new ArrayList<>();
    private static List<String> departments = new ArrayList<>();

    public Admin(String name, String password) {
        super("admin", "adminpass");  // Calling the constructor of the User class
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

    public static void addDepartment(Scanner scanner) {
        System.out.print("Enter the name of the department to add: ");
        String department = scanner.nextLine();

        if (!departments.contains(department)) {
            departments.add(department);
            System.out.println("Department '" + department + "' has been added successfully.");
        } else {
            System.out.println("Department '" + department + "' already exists.");
        }
    }

    public boolean signIn(Scanner scanner) {
        System.out.print("Enter admin username: ");
        String inputUsername = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String inputPassword = scanner.nextLine();

        return this.getName().equals(inputUsername) && this.signIn(inputPassword);  // Using the signIn method from User class
    }

    public void initDepartments() {
        if (departments == null) {
            departments = new ArrayList<>();
        }
        departments.add("Neurology");
        departments.add("X-ray");
        departments.add("Dentistry");
    }
   
    public void addDoctor(String doctorName, String departmentName) {
        if (departments.contains(departmentName)) {
            for (Doctor doctor : doctors) {
                if (doctor.getName().equalsIgnoreCase(doctorName)) {
                    System.out.println("Dr. " + doctorName + " already exists in the system.");
                    return;
                }
            }
            
            Doctor newDoctor = new Doctor(doctorName, departmentName);
            doctors.add(newDoctor);
            System.out.println("Dr. " + doctorName + " added to " + departmentName + " department.");
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

    public static void viewDoctors() {
        System.out.println("List of Doctors:");
        System.out.println("-----------------");
    
        if (doctors.isEmpty()) {
            System.out.println("No doctors available.");
        } else {
            // Add headers for better readability
            System.out.printf("%-5s %-30s %-20s\n", "No.", "Doctor Name", "Department");
    
            // Display doctors with formatted output
            for (int i = 0; i < doctors.size(); i++) {
                Doctor doctor = doctors.get(i);
                System.out.printf("%-5d %-30s %-20s\n", (i + 1), "Dr. " + doctor.getName(), doctor.getDepartment());
            }
        }
    }
    
    
    public static void showDepartments() {
        System.out.println("List of Departments:");
        System.out.println("---------------------");

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

    public void adminMenu(Scanner scanner) {
    boolean exit = false;

        while (!exit) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Department");
            System.out.println("2. Add Doctor");
            System.out.println("3. Remove Doctor");
            System.out.println("4. View Doctors");
            System.out.println("5. View Departments");
            System.out.println("6. Exit");
            System.out.print("Please select an option by entering the corresponding number: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        addDepartment(scanner);
                        break;
                    case 2:
                        System.out.print("Enter doctor's name: ");
                        String doctorName = scanner.nextLine();
                        System.out.print("Enter department for the doctor (Note: this is case sensitive): ");
                        String departmentName = scanner.nextLine();
                        addDoctor(doctorName, departmentName);
                        break;
                    case 3:
                        System.out.print("Enter doctor's name to remove: ");
                        String nameToRemove = scanner.nextLine();
                        removeDoctor(nameToRemove);
                        break;
                    case 4:
                        viewDoctors();
                        break;
                    case 5:
                        showDepartments();
                        break;
                    case 6:
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
}
