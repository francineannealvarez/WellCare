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
    private List<PatientHistory> historyRecords = new ArrayList<>();
    
    private Admin admin; 

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
    
    public Admin getAdmin(){
        return admin;
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

    public static void signIn(Scanner scanner, PatientDatabase patientDatabase, Admin admin, Doctor doctor) {
        System.out.println("Sign-In:");
        System.out.print("Enter your full name (Note: this is case sensitive): ");
        String inputName = scanner.nextLine();

        System.out.print("Enter your password: ");
        String inputPassword = scanner.nextLine();

        // Find the patient in the database
        Patient foundPatient = patientDatabase.findPatient(inputName, inputPassword);

        if (foundPatient != null && foundPatient.getPassword().equals(inputPassword)) {
            System.out.println("Sign-in successful. Welcome, " + foundPatient.getName() + "!");
            foundPatient.showUserOptions(scanner, admin, doctor);
        } else {
            System.out.println("Sign-in failed. Patient not found or invalid password.");
        }
    }

    public void showUserOptions(Scanner scanner, Admin admin, Doctor doctor) {
       int choice;
       boolean exit = false;

        while (!exit){
            System.out.println("\nPatient Menu");
            System.out.println("1. Book an appointment");
            System.out.println("2. View medical history");
            System.out.println("3. Entered Patient Info");
            System.out.println("4. Log out");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    bookAppointment(admin, scanner, doctor);
                    break;
                case 2:
                    viewMedicalHistory();
                    break;
                case 3:
                    displayPatientInfo();
                    break;
                case 4:
                    exit = true;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } 
    }

    public void bookAppointment(Admin admin, Scanner scanner, Doctor doctor) {
        List<String> departments = admin.getDepartments();
    
        System.out.println("Available departments:");
        for (int i = 0; i < departments.size(); i++) {
            System.out.println((i + 1) + ". " + departments.get(i));
        }
    
        System.out.print("Please select a department by entering the corresponding number: ");
        int departmentChoice = scanner.nextInt();
        scanner.nextLine(); 
        
        if (departmentChoice < 1 || departmentChoice > departments.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }
    
        String selectedDepartment = departments.get(departmentChoice - 1);
        
        // Get the list of available doctors in the selected department from the admin
        List<Doctor> availableDoctors = Admin.getDoctors(); 
    
        // Filter doctors based on the selected department
        availableDoctors = getDoctorsByDepartment(availableDoctors, selectedDepartment);
    
        System.out.println("Available doctors in the " + selectedDepartment + " department:");
        if (availableDoctors.isEmpty()) {
            System.out.println("No doctors available in this department.");
            return;
        }
        for (int i = 0; i < availableDoctors.size(); i++) {
            System.out.println((i + 1) + ". " + availableDoctors.get(i).getName());
        }
    
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
        
        if (availableTimes.isEmpty()) {
            System.out.println("Sorry, there are no available times for Dr. " + selectedDoctor.getName() + ".");
            return;
        }
        
        System.out.println("Available times for Dr. " + selectedDoctor.getName() + ":");
        for (int i = 0; i < availableTimes.size(); i++) {
            System.out.println((i + 1) + ". " + availableTimes.get(i));
        }
    
        System.out.print("Please select a time by entering the corresponding number: ");
        int timeChoice = scanner.nextInt();
        scanner.nextLine(); 
        
        if (timeChoice < 1 || timeChoice > availableTimes.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }
    
        // Get the selected time
        String selectedTime = availableTimes.get(timeChoice - 1);
        selectedDoctor.removeAvailableTime(selectedTime);
    
        // Create a new PatientHistory record for this appointment
        PatientHistory newRecord = new PatientHistory(this.name, selectedTime, selectedDepartment, selectedDoctor.getName(), "Pending");
    
        // Check if this patient history already exists
        for (PatientHistory record : historyRecords) {
            if (record.equals(newRecord)) {
                System.out.println("This appointment has already been booked.");
                return;  // Exit the method to avoid duplicate records
            }
        }
    
        // Add the new PatientHistory record if it doesn't already exist
        this.historyRecords.add(newRecord);
    
        // Inform the doctor and add the appointment
        selectedDoctor.addAppointment(newRecord, selectedTime, selectedDoctor.getName());
        System.out.println("Appointment booked with Dr. " + selectedDoctor.getName() + " in the " + selectedDepartment + " department on " + selectedTime);
    }
    
    
    // Method to get available doctors by department
    public List<Doctor> getDoctorsByDepartment(List<Doctor> doctors, String department) {
        List<Doctor> availableDoctors = new ArrayList<>();
        for (Doctor doctor : doctors) {
            if (doctor.getDepartment().equalsIgnoreCase(department) && !doctor.getAvailableTimes().isEmpty()) {
                availableDoctors.add(doctor);
            }
        }
        return availableDoctors; 
    }
    
    public void displayPatientInfo() {
        System.out.println("Patient Information");
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
    
    public void viewMedicalHistory() {
        if (historyRecords != null && !historyRecords.isEmpty()) {
            System.out.println("Your Medical History:");
            for (PatientHistory record : historyRecords) {
                System.out.println(record.toString());  // Alternatively, just System.out.println(record);
            }
        } else {
            System.out.println("No medical history records found.");
        }
    }
    
    
    public static void patientMenu(Scanner scanner, PatientDatabase patientDatabase, Admin admin, Doctor doctor) {
        System.out.println("1. Signup");
        System.out.println("2. Sign in");
        System.out.println("3. Exit");
        System.out.print("Please select an option by entering the corresponding number:");
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
                Patient.signIn(scanner, patientDatabase, admin, doctor);
                break;
            case 3:
                System.out.println("Exiting...");
                return;
            default:
                System.out.println("Invalid option. Please try again.");
        } 
    }
}
    
    


