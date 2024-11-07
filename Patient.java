import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Patient extends User {
    private String address;
    private String bday;
    private String contactNo;
    private String email;
    private String emergencyNo;
    private String gender;
    private String allergy;
    private String past; // past surgeries or treatments
    private String bloodtype;
    private List<PatientHistory> historyRecords;
   // private PatientDatabase patientDatabase; // Assume you have a PatientDatabase class
    private Admin admin; // Reference to the Admin instance for booking appointments

    public Patient(String name, String password, String address, String bday, String contactNo, String email,
                   String emergencyNo, String gender, String allergy, String past, String bloodtype, Admin admin) {
        super(name, password);  
        this.address = address;
        this.bday = bday;
        this.contactNo = contactNo;
        this.email = email;
        this.emergencyNo = emergencyNo;
        this.gender = gender;
        this.allergy = allergy;
        this.past = past;
        this.bloodtype = bloodtype;
        this.historyRecords = new ArrayList<>();
        this.admin = admin;
    }
   

    public Patient(String name, String password) {
        super(name, password);  
    }

    
public boolean signup(Scanner scanner, PatientDatabase patientDatabase) {
    System.out.print("Enter your name: ");
    this.name = scanner.nextLine();

    System.out.print("Enter your password: ");
    this.password = scanner.nextLine();

    System.out.print("Enter your address: ");
    this.address = scanner.nextLine();

    System.out.print("Enter your birth date (YYYY-MM-DD): ");
    this.bday = scanner.nextLine();

    System.out.print("Enter your contact number: ");
    this.contactNo = scanner.nextLine();

    System.out.print("Enter your email: ");
    this.email = scanner.nextLine();

    System.out.print("Enter your emergency contact number: ");
    this.emergencyNo = scanner.nextLine();

    System.out.print("Enter your gender: ");
    this.gender = scanner.nextLine();

    System.out.print("Enter any allergies: ");
    this.allergy = scanner.nextLine();

    System.out.print("Enter past surgeries or treatments: ");
    this.past = scanner.nextLine();

    System.out.print("Enter your blood type: ");
    this.bloodtype = scanner.nextLine();

    // Perform validation (check for empty fields)
    if (name.isEmpty() || password.isEmpty() || contactNo.isEmpty() || email.isEmpty() ||
        address.isEmpty() || bday.isEmpty() || emergencyNo.isEmpty() || gender.isEmpty() ||
        allergy.isEmpty() || bloodtype.isEmpty() || past.isEmpty()) {
        System.out.println("Required fields are missing.");
        return false;
    }

    // Add the patient to the database
    patientDatabase.addPatient(this);
    System.out.println("Sign-up completed successfully!\n");
    return true;
}

public static void signIn(Scanner scanner, PatientDatabase patientDatabase) {
    System.out.println("Sign-In:");
    System.out.print("Enter your full name: ");
    String inputName = scanner.nextLine();

    System.out.print("Enter your password: ");
    String inputPassword = scanner.nextLine();

    // Find the patient in the database
    Patient foundPatient = patientDatabase.findPatient(inputName, inputPassword);

    // Check if the patient exists and verify the password
    if (foundPatient != null && foundPatient.getPassword().equals(inputPassword)) {
        System.out.println("Sign-in successful. Welcome, " + foundPatient.getName() + "!");
        foundPatient.showUserOptions(scanner);
    } else {
        System.out.println("Sign-in failed. Patient not found or invalid password. Please sign up first.");
    }
}

    
    /* Method to sign in a patient
    public static void signIn(PatientDatabase patientDatabase) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Sign-In:");
        System.out.print("Enter your full name: ");
        String inputName = scanner.nextLine();

        System.out.print("Enter your password: ");
        String inputPassword = scanner.nextLine();

        System.out.print("Enter your birth date (YYYY-MM-DD): ");
        String inputBday = scanner.nextLine();

        // Check if the patient exists in the database
        Patient foundPatient = patientDatabase.findPatient(inputName, inputPassword, inputBday);

        if (foundPatient != null) {
            System.out.println("Sign-in successful. Welcome, " + foundPatient.getName() + "!");
            foundPatient.showUserOptions(); // Show options for booking appointments and viewing medical history
        } else {
            System.out.println("Sign-in failed. Patient not found. Please sign up first.");
        }
        scanner.close();
    }*/

    
    public void showUserOptions(Scanner scanner) {
       // Scanner scanner = new Scanner(System.in);
       int choice;
        do{
            System.out.println("\nSelect an option:");
            System.out.println("1. Book an appointment");
            System.out.println("2. View medical history");
            System.out.println("3. Log out");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            

            switch (choice) {
                case 1:
                    bookAppointment(admin, scanner);
                    break;
                case 2:
                    viewMedicalHistory();
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return; 
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 3);
    }

    public void bookAppointment(Admin admin, Scanner scanner) {
        if (admin == null) {
            System.out.println("Admin data not available.");
            return;
        }

        List<String> departments = admin.getDepartments();
        
        // Display available departments
        System.out.println("Available departments:");
        for (int i = 0; i < departments.size(); i++) {
            System.out.println((i + 1) + ". " + departments.get(i));
        }
        
        // Prompt the patient to select a department
        System.out.print("Please select a department by entering the corresponding number: ");
        int departmentChoice = scanner.nextInt();
        scanner.nextLine(); 
        
        if (departmentChoice < 1 || departmentChoice > departments.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }
        
        String selectedDepartment = departments.get(departmentChoice - 1);
        
        // Get the list of available doctors in the selected department from the admin
        List<Doctor> availableDoctors = admin.getDoctors(); 

        // Filter doctors based on the selected department
        availableDoctors = getDoctorsByDepartment(availableDoctors, selectedDepartment);
        
        // Display available doctors
        System.out.println("Available doctors in the " + selectedDepartment + " department:");
        if (availableDoctors.isEmpty()) {
            System.out.println("No doctors available in this department.");
            return;
        }
        for (int i = 0; i < availableDoctors.size(); i++) {
            System.out.println((i + 1) + ". " + availableDoctors.get(i).getName());
        }
    
        // Prompt the patient to select a doctor
        System.out.print("Please select a doctor by entering the corresponding number: ");
        int doctorChoice = scanner.nextInt();
        scanner.nextLine(); 
        
        if (doctorChoice < 1 || doctorChoice > availableDoctors.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }
        
        Doctor selectedDoctor = availableDoctors.get(doctorChoice - 1);
        
        // Get the available appointment times from the selected doctor
        List<String> availableTimes = selectedDoctor.getAvailableTimes();
        
        // Check if there are available times
        if (availableTimes.isEmpty()) {
            System.out.println("Sorry, there are no available times for Dr. " + selectedDoctor.getName() + ".");
            return;
        }
        
        // Display available times
        System.out.println("Available times for Dr. " + selectedDoctor.getName() + ":");
        for (int i = 0; i < availableTimes.size(); i++) {
            System.out.println((i + 1) + ". " + availableTimes.get(i));
        }
    
        // Prompt the patient to select a time
        System.out.print("Please select a time by entering the corresponding number: ");
        int timeChoice = scanner.nextInt();
        scanner.nextLine();
        
        // Validate the choice
        if (timeChoice < 1 || timeChoice > availableTimes.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }
    
        // Get the selected time
        String selectedTime = availableTimes.get(timeChoice - 1);
    
        // Create a new PatientHistory record for this appointment
        PatientHistory newRecord = new PatientHistory(this.name, selectedTime, selectedDepartment, selectedDoctor.getName(), ""); // Issue description can be added here if needed
        this.historyRecords.add(newRecord);
    
        // Inform the doctor and add the appointment
        selectedDoctor.addAppointment(newRecord);
        selectedDoctor.removeAvailableTime(selectedTime);  // Remove the booked time from availability
    
    
        System.out.println("Appointment booked with Dr. " + selectedDoctor.getName() + " in the " + selectedDepartment + " department on " + selectedTime);
    }
    
    // Method to get available doctors by department
    private List<Doctor> getDoctorsByDepartment(List<Doctor> doctors, String department) {
        List<Doctor> availableDoctors = new ArrayList<>();
        for (Doctor doctor : doctors) {
            if (doctor.getDepartment().equalsIgnoreCase(department) && !doctor.getAvailableTimes().isEmpty()) {
                availableDoctors.add(doctor);
            }
        }
        return availableDoctors; 
    }
    

/* 
    public void bookAppointment(Admin admin) {
        Scanner scanner = new Scanner(System.in);
        
        // Get the list of available departments from the admin
        List<String> departments = admin.getDepartments();
        
        // Display available departments
        System.out.println("Available departments:");
        for (int i = 0; i < departments.size(); i++) {
            System.out.println((i + 1) + ". " + departments.get(i));
        }
        
        // Prompt the patient to select a department
        System.out.print("Please select a department by entering the corresponding number: ");
        int departmentChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (departmentChoice < 1 || departmentChoice > departments.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }
        
        String selectedDepartment = departments.get(departmentChoice - 1);
        
        // Get the list of available doctors in the selected department from the admin
        List<Doctor> availableDoctors = admin.getDoctors(); // Or filter doctors based on department

        // Filter doctors based on the selected department
        availableDoctors = getDoctorsByDepartment(availableDoctors, selectedDepartment);
        
        // Display available doctors
        System.out.println("Available doctors in the " + selectedDepartment + " department:");
        if (availableDoctors.isEmpty()) {
            System.out.println("No doctors available in this department.");
            return;
        }
        for (int i = 0; i < availableDoctors.size(); i++) {
            System.out.println((i + 1) + ". " + availableDoctors.get(i).getName());
        }

        // Prompt the patient to select a doctor
        System.out.print("Please select a doctor by entering the corresponding number: ");
        int doctorChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (doctorChoice < 1 || doctorChoice > availableDoctors.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }
        
        Doctor selectedDoctor = availableDoctors.get(doctorChoice - 1);
        
        // Get the available appointment times from the selected doctor
        List<String> availableTimes = selectedDoctor.getAvailableTimes();
        
        // Check if there are available times
        if (availableTimes.isEmpty()) {
            System.out.println("Sorry, there are no available times for Dr. " + selectedDoctor.getName() + ".");
            return;
        }
        
        // Display available times
        System.out.println("Available times for Dr. " + selectedDoctor.getName() + ":");
        for (int i = 0; i < availableTimes.size(); i++) {
            System.out.println((i + 1) + ". " + availableTimes.get(i));
        }

        // Prompt the patient to select a time
        System.out.print("Please select a time by entering the corresponding number: ");
        int timeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        // Validate the choice
        if (timeChoice < 1 || timeChoice > availableTimes.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        // Get the selected time
        String selectedTime = availableTimes.get(timeChoice - 1);

        // Create a new PatientHistory record for this appointment
        PatientHistory newRecord = new PatientHistory(this.name, selectedTime, selectedDepartment, selectedDoctor.getName(), ""); // Issue description can be added here if needed
        this.historyRecords.add(newRecord);

        // Inform the doctor and add the appointment
        selectedDoctor.addAppointment(newRecord);
        selectedDoctor.removeAvailableTime(selectedTime);  // Remove the booked time from availability

        System.out.println("Appointment booked with Dr. " + selectedDoctor.getName() + " in the " + selectedDepartment + " department on " + selectedTime);
    }

    // Method to get available doctors by department
    private List<Doctor> getDoctorsByDepartment(List<Doctor> doctors, String department) {
        List<Doctor> availableDoctors = new ArrayList<>();
        for (Doctor doctor : doctors) {
            if (doctor.getDepartment().equalsIgnoreCase(department) && !doctor.getAvailableTimes().isEmpty()) {
                availableDoctors.add(doctor);
            }
        }
        scanner.close();
        return availableDoctors;
    }
*/

    // Optional method to display patient information (for testing lang)
    public void displayPatientInfo() {
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Contact Number: " + contactNo);
        System.out.println("Address: " + address);
        System.out.println("Birthday: " + bday);
        System.out.println("Emergency Contact: " + emergencyNo);
        System.out.println("Gender: " + gender);
        System.out.println("Allergies: " + allergy);
        System.out.println("Past Surgeries/Treatments: " + past);
        System.out.println("Blood Type: " + bloodtype);
    }
    

    /*public void bookAppointment(Doctor doctor, String department, String issueDescription) {
        Scanner scanner = new Scanner(System.in);
        
        // Get the available times from the doctor
        List<String> availableTimes = doctor.getAvailableTimes();

        // Check if there are available times
        if (availableTimes.isEmpty()) {
            System.out.println("Sorry, there are no available times for Dr. " + doctor.getName() + ".");
            return;
        }

        // Display available times
        System.out.println("Available times for Dr. " + doctor.getName() + " in the " + department + " department:");
        for (int i = 0; i < availableTimes.size(); i++) {
            System.out.println((i + 1) + ". " + availableTimes.get(i));
        }

        // Prompt the patient to select a time
        System.out.print("Please select a time by entering the corresponding number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        scanner.close();
        // Validate the choice
        if (choice < 1 || choice > availableTimes.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        // Get the selected time
        String selectedTime = availableTimes.get(choice - 1);

        // Create a new PatientHistory record for this appointment
        PatientHistory newRecord = new PatientHistory(this.name, selectedTime, department, doctor.getName(), issueDescription);
        historyRecords.add(newRecord);
        
        // Inform the doctor and add the appointment
        doctor.addAppointment(newRecord);
        doctor.removeAvailableTime(selectedTime);  // Remove the booked time from availability

        System.out.println("Appointment booked with Dr. " + doctor.getName() + " in the " + department + " department on " + selectedTime);
        
    }

    public void chooseDepartmentAndShowDoctors(List<Doctor> doctors) {
        Scanner scanner = new Scanner(System.in);
        
        // Create a list of departments and their corresponding doctors
        List<String> departments = new ArrayList<>();
        for (Doctor doc : doctors) {
            if (!departments.contains(doc.getDepartment())) {
                departments.add(doc.getDepartment());
            }
        }

        // Display available departments
        System.out.println("Available Departments:");
        for (int i = 0; i < departments.size(); i++) {
            System.out.println((i + 1) + ". " + departments.get(i));
        }

        // Prompt the patient to select a department
        System.out.print("Please select a department by entering the corresponding number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        // Validate the choice
        if (choice < 1 || choice > departments.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        // Get the selected department
        String selectedDepartment = departments.get(choice - 1);
        
        // Show available doctors in the selected department
        System.out.println("Available doctors in " + selectedDepartment + ":");
        boolean found = false;
        for (Doctor doc : doctors) {
            if (doc.getDepartment().equals(selectedDepartment)) {
                System.out.println(" - " + doc.getName());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No available doctors in this department.");
        }
        scanner.close();
    }
*/
    /*public List<PatientHistory> getHistoryRecords() {
        return historyRecords; // Return the list of patient history records
    }*/

    public void viewMedicalHistory() {
        if (historyRecords.isEmpty()) {
            System.out.println("You have no medical history records.");
            return;
        }
    
        System.out.println("Your Medical History:");
        for (PatientHistory record : historyRecords) {
            System.out.println("Patient Name: " + record.getPatientName());
            System.out.println("Visit Date: " + record.getVisitDate());
            System.out.println("Department: " + record.getDepartment());
            System.out.println("Doctor: " + record.getDoctorName());
            System.out.println("Diagnosis: " + record.getDiagnosis());
            System.out.println("----------------------------------");
        }
    }
    
    public static void patientMenu(Scanner scanner, PatientDatabase patientDatabase) {
        System.out.println("1. Signup");
        System.out.println("2. Sign in");
        System.out.println("3. Exit");
        System.out.print("Enter the corresponding number of your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); 
    
        switch (choice) {
            case 1:
                Patient newPatient = new Patient("", ""); // Create a new Patient with empty name and password
                boolean success = newPatient.signup(scanner, patientDatabase);
                if (success) {
                    System.out.println("Patient signed up successfully.");
                } else {
                    System.out.println("Signup failed. Please try again.");
                }
                break;
            case 2:
                Patient.signIn(scanner, patientDatabase);
                break;
            case 3:
                System.out.println("Exiting...");
                return; // Exit the method
            default:
                System.out.println("Invalid option. Please try again.");
        }
        
    }
    
    
    }
    


