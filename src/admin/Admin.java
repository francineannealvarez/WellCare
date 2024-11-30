package admin;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import connection.AdminDao;
import user.User;
import display.DisplayUtils;

public class Admin extends User{
    private AdminDao adminDao;
    private DisplayUtils display;

    public Admin(String name, String password, AdminDao adminDao, DisplayUtils display) {
        super("admin", "adminpass");  
        this.adminDao = adminDao;
        this.display = display;
    }

    public DisplayUtils getDisplayUtils(){
        return display;
    }

    public AdminDao getAdminDao(){
        return adminDao;
    }

    public boolean signIn(Scanner scanner) {
        display.printHeader("ADMIN LOGIN");
        System.out.print("Enter admin username: ");
        String inputUsername = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String inputPassword = scanner.nextLine();

        return this.getName().equals(inputUsername) && this.signIn(inputPassword);  
    }

    public void addDepartment(Scanner scanner) {
        display.printHeader("ADD DEPARTMENT TO SYSTEM");
        System.out.print("Enter the name of the department to add: ");
        String departmentName = scanner.nextLine();
    
        adminDao.addDepartment(departmentName);
    }
    
    public void removeDepartment(Scanner scanner) {
        display.printHeader("REMOVE DEPARTMENT FROM SYSTEM");
        showDepartments();
    
        System.out.print("Enter the name of the department to remove: ");
        String department = scanner.nextLine();
        
        try {
            adminDao.removeDepartment(department);
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void addDoctor(Scanner scanner) throws SQLException {
        display.printHeader("ADD NEW DOCTOR TO SYSTEM");
    
        System.out.print("Enter doctor's name: ");
        String doctorName = scanner.nextLine();
    
        System.out.print("Enter department for the doctor: ");
        String dept = scanner.nextLine();
    
        adminDao.addDoctor(doctorName, dept); 
    }
    
    public void removeDoctor(Scanner scanner) {
        display.printHeader("REMOVE DOCTOR FROM SYSTEM");
        System.out.println();
    
        try {
            viewDoctors();
    
            int idToRemove = 0;
            boolean validInput = false;
    
            while (!validInput) {
                System.out.print("Enter doctor's ID to remove: ");
                try {
                    idToRemove = scanner.nextInt();
                    validInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer for the doctor ID.");
                    scanner.nextLine(); 
                }
            }

            boolean doctorRemoved = adminDao.removeDoctor(idToRemove);
    
            if (doctorRemoved) {
                System.out.println("Doctor removed successfully.");
            } else {
                System.out.println("Error: Doctor ID " + idToRemove + " not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid doctor ID.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void viewDoctors() {
        display.printHeader("LIST OF DOCTORS");
    
        List<String> doctorsWithDepartments = adminDao.getDoctorsWithDepartments();
    
        if (doctorsWithDepartments.isEmpty()) {
            System.out.println("No doctors available.");
        } else {
            System.out.printf("%-5s %-15s %-30s %-15s %-20s\n", "No.", "Doctor ID", "Doctor Name", "Dept ID", "Dept Name");
    
            for (int i = 0; i < doctorsWithDepartments.size(); i++) {
                String doctorInfo = doctorsWithDepartments.get(i);
                String[] infoParts = doctorInfo.split(", ");
                String doctorId = infoParts[0].split(": ")[1];  
                String doctorName = infoParts[1].split(": ")[1];  
                String departmentId = infoParts[2].split(": ")[1]; 
                String departmentName = infoParts[3].split(": ")[1];  
    
                System.out.printf("%-5d %-15s %-30s %-15s %-20s\n", 
                    (i + 1), doctorId, doctorName, departmentId, departmentName);
            }
        }
    }
    
    public void showDepartments() {
        display.printHeader("LIST OF DEPARTMENTS");
    
        try {
            List<String> departments = adminDao.getAllDepartments(); 
            
            if (departments.isEmpty()) {
                System.out.println("No departments available.");
            } else {
                System.out.printf("%-5s %-15s %-30s\n", "No.", "Dept ID", "Department Name");
    
                for (int i = 0; i < departments.size(); i++) {
                    String[] departmentInfo = departments.get(i).split(", ");
                    String deptId = departmentInfo[0].split(": ")[1]; 
                    String deptName = departmentInfo[1].split(": ")[1]; 
                    
                    System.out.printf("%-5d %-15s %-30s\n", (i + 1), deptId, deptName);
                }
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void adminMenu(Scanner scanner) throws SQLException {
        boolean exit = false;
        try {
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
                int choice = scanner.nextInt();
                scanner.nextLine(); 
                
                switch (choice) {
                    case 1 -> addDepartment(scanner);
                    case 2 -> removeDepartment(scanner);
                    case 3 -> addDoctor(scanner);
                    case 4 -> removeDoctor(scanner);
                    case 5 -> viewDoctors();
                    case 6 -> showDepartments();
                    case 7 -> {
                        System.out.println("Exiting...");
                        exit = true;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }
           }catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); 
        }
    }
}

