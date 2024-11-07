import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Admin {
    private static List<Doctor> doctors = new ArrayList<>();
    private static List<String> departments = new ArrayList<>();

    
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
    
    public static boolean signIn(Scanner scanner) {
        //Scanner scanner = new Scanner(System.in);
        System.out.print("Enter admin username: ");
        String inputUsername = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String inputPassword = scanner.nextLine();
        
    
        return "admin".equals(inputUsername) && "adminpass".equals(inputPassword);
    }
    
    /*  
    public static void addDepartment() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the department to add: ");
        String department = scanner.nextLine();

        // Check if the department already exists
        if (!departments.contains(department)) {
            departments.add(department);
            System.out.println("Department '" + department + "' has been added successfully.");
        } else {
            System.out.println("Department '" + department + "' already exists.");
        }
        scanner.close();
    }*/

    public List<String> getDepartments() {
        return departments;
    }

    

    static  {
        Admin.doctors = new ArrayList<>();
            departments.add("Neurology");
            departments.add("X-ray");
            departments.add("Dentistry");
        
    }

    

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public static void addDoctor(String name, String department) {
        Doctor newDoctor = new Doctor(name, department);
        doctors.add(newDoctor);
        System.out.println("Doctor " + name + " added to " + department + " department.");
    }

    public static void removeDoctor(String name) {
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getName().equalsIgnoreCase(name)) {
                doctors.remove(i);
                System.out.println("Doctor " + name + " removed successfully.");
                return;
            }
        }
        System.out.println("Doctor " + name + " not found.");
    }

    public static void viewDoctors() {
        System.out.println("List of doctors:");
        if (doctors.isEmpty()) {
            System.out.println("No doctors available.");
        } else {
            for (Doctor doctor : doctors) {
                System.out.println("- " + doctor.getName() + " (Department: " + doctor.getDepartment() + ")");
            }
        }
    }

    public static void showDepartments() {
        System.out.println("List of departments:");
        if (departments.isEmpty()) {
            System.out.println("No departments available.");
        } else {
            for (String dept : departments) {
                System.out.println("- " + dept);
            }
        }
    }

    /* 
    public static boolean signIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter admin username: ");
        String inputUsername = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String inputPassword = scanner.nextLine();
        scanner.close();

        return "admin".equals(inputUsername) && "adminpass".equals(inputPassword);
    }*/

    public static void adminMenu(Scanner scanner) {
        //Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Department");
            System.out.println("2. Add Doctor");
            System.out.println("3. Remove Doctor");
            System.out.println("4. View Doctors");
            System.out.println("5. View Departments");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addDepartment(scanner);
                    break;
                case 2:
                    System.out.print("Enter doctor's name: ");
                    String doctorName = scanner.nextLine();
                    System.out.print("Enter department for the doctor: ");
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
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        
    }

    
}
