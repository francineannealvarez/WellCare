package patient;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import doctor_admin.Admin; // For accessing Admin
import doctor_admin.Doctor;
import user.User;

public class Patient extends User {
    private String address;
    private String bday;
    private String contactNo;
    private String email;
    private String emergencyNo;
    private Gender gender;
    private String allergy;
    private String past; // past surgeries or treatments
    private String bloodtype;
    private List<PatientHistory> historyRecords = new ArrayList<>();
    private List<PatientHistory> appointments;
    private List<PatientHistory> cancelledAppointments;
    
    private Admin admin; 

    enum Gender {
        Female, Male
    }
    public Patient(String name, String password, String address, String bday, String contactNo, String email,
                   String emergencyNo, Gender gender, String allergy, String past, String bloodtype, Admin admin) {
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
        this.appointments = new ArrayList<>();
        this.cancelledAppointments = new ArrayList<>();
    }

    public Patient(String name, String password) {
        super(name, password);  
    }

    public String getName() {
        return super.getName();  
    }

    @Override
    public String toString() {
        return getName();  
    } 
    
    public Admin getAdmin(){
        return admin;
    }
    
    public void addAppointment(PatientHistory appointment) {
        this.appointments.add(appointment);  
    }

    public void removeAppointment(PatientHistory appointment) {
        this.appointments.remove(appointment);  
    }
    
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<PatientHistory> getAppointments() {
        return appointments;
    }

    public List<PatientHistory> getHistoryRecords() {
        return historyRecords;
    }
    
    public List<PatientHistory> getCancelledAppointments() {
        if (cancelledAppointments == null) {
            cancelledAppointments = new ArrayList<>(); 
        }
        return cancelledAppointments;
    }

    public void addCancelledAppointment(PatientHistory history) {
        if (cancelledAppointments == null) {
            cancelledAppointments = new ArrayList<>(); 
        }
        if (history != null) {
            cancelledAppointments.add(history); 
        } else {
            System.out.println("Cannot add a null appointment.");
        }
    }
    

    public boolean signup(Scanner scanner, PatientDatabase patientDatabase) {
        System.out.println("Please fill in the following details to sign up. Note: Some fields are case-sensitive.");
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

        System.out.print("Enter your gender (Female/Male): ");
        String genderInput = scanner.nextLine().trim();
        if (genderInput.equalsIgnoreCase("Female")) {
            this.gender = Gender.Female;
        } else if (genderInput.equalsIgnoreCase("Male")) {
            this.gender = Gender.Male;
        } else {
            System.out.println("Invalid gender. Please enter 'Female' or 'Male'.");
            return false;
        }

        System.out.print("Enter any allergies (N/A if none): ");
        this.allergy = scanner.nextLine();

        System.out.print("Enter past surgeries or treatments(N/A if none): ");
        this.past = scanner.nextLine();

        System.out.print("Enter your blood type: ");
        this.bloodtype = scanner.nextLine();

        if (name.isEmpty() || password.isEmpty() || contactNo.isEmpty() || email.isEmpty() ||
        address.isEmpty() || bday.isEmpty() || emergencyNo.isEmpty() || allergy.isEmpty() ||
        bloodtype.isEmpty() || past.isEmpty()) {
        System.out.println("Required fields are missing.");
        return false;
        }

        patientDatabase.addPatient(this);
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
       boolean exit = false;

        while (!exit){
            System.out.println("\nPatient Menu");
            System.out.println("1. Book an appointment");
            System.out.println("2. View medical history");
            System.out.println("3. Entered Patient Info");
            System.out.println("4. View Scheduled Appointment");
            System.out.println("5. View Cancelled Appointment");
            System.out.println("6. Log out");
            System.out.print("Choose an option: ");
           
        try{
            int choice = scanner.nextInt();
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
                    viewPatientAppointments();
                    break;
                case 5:
                    viewCancelledAppointments();
                    break;
                case 6:
                    exit = true;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input from the scanner
        }
    }
}

    public void bookAppointment(Admin admin, Scanner scanner, Doctor doctor) {
        List<String> departments = admin.getDepartments();
        if (appointments == null) {
            appointments = new ArrayList<>(); 
        }
    
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

        String selectedTime = availableTimes.get(timeChoice - 1);
        selectedDoctor.removeAvailableTime(selectedTime);

        System.out.print("Please describe your medical condition or reason for the visit: ");
        String medicalCondition = scanner.nextLine();

        //PatientHistory newRecord = new PatientHistory(this.name, selectedTime, selectedDepartment, selectedDoctor.getName(), medicalCondition, "Pending");
        PatientHistory newRecord = new PatientHistory(
            this,  // Pass the current Patient object (refers to the current Patient)
            selectedTime,  
            selectedDepartment,  
            selectedDoctor.getName(),  
            medicalCondition,  
            "Pending"  
        ); 

        for (PatientHistory record : historyRecords) {
            if (record.equals(newRecord)) {
                System.out.println("This appointment has already been booked.");
                return; 
            }
        }
    
        //this.historyRecords.add(newRecord);
        this.appointments.add(newRecord);
    
        selectedDoctor.addAppointment(newRecord, selectedTime, selectedDoctor.getName());
        System.out.println("Appointment booked with Dr. " + selectedDoctor.getName() + " in the " + selectedDepartment + " department on " + selectedTime);
    }
    
    public List<Doctor> getDoctorsByDepartment(List<Doctor> doctors, String department) {
        List<Doctor> availableDoctors = new ArrayList<>();
        for (Doctor doctor : doctors) {
            if (doctor.getDepartment().equalsIgnoreCase(department) && !doctor.getAvailableTimes().isEmpty()) {
                availableDoctors.add(doctor);
            }
        }
        return availableDoctors; 
    }

    public void viewMedicalHistory() {
        if (historyRecords != null && !historyRecords.isEmpty()) {
            System.out.println("Your Medical History:");
            for (PatientHistory record : historyRecords) {
                System.out.println(record.toString());   // Alternatively, just System.out.println(record);
            }
        } else {
            System.out.println("No medical history records found.");
        }
    }
    
    
    public void displayPatientInfo() {
        System.out.println("Patient Information:");
        System.out.println("----------------------");
        System.out.printf("%-30s %-30s\n", "Name:", name);
        System.out.printf("%-30s %-30s\n", "Email:", email);
        System.out.printf("%-30s %-30s\n", "Contact Number:", contactNo);
        System.out.printf("%-30s %-30s\n", "Address:", address);
        System.out.printf("%-30s %-30s\n", "Birthday:", bday);
        System.out.printf("%-30s %-30s\n", "Emergency Contact:", emergencyNo);
        System.out.printf("%-30s %-30s\n", "Gender:", gender);
        System.out.printf("%-30s %-30s\n", "Allergies:", allergy);
        System.out.printf("%-30s %-30s\n", "Past Surgeries/Treatments:", past);
        System.out.printf("%-30s %-30s\n", "Blood Type:", bloodtype);
    }
    
    
    public void viewPatientAppointments() {
        List<PatientHistory> appointments = getAppointments(); // Retrieve the patient's appointments
    
        if (appointments == null || appointments.isEmpty()) {
            System.out.println("You have no scheduled appointments.");
            return;
        }
        
        System.out.println("Scheduled appointments for " + getName() + ":");
        for (PatientHistory appointment : appointments) {
            if (!appointment.isCancelled()) { // Skip canceled appointments
                System.out.println("-------------------------------------------------");
                System.out.println(appointment.toStringForAppointment(true));
                System.out.println("-------------------------------------------------");
            }
        }
    }
    
    public void viewCancelledAppointments() {
        if (cancelledAppointments == null || cancelledAppointments.isEmpty()) {
            System.out.println("No cancelled appointments.");
            return;
        }
    
        System.out.println("Cancelled Appointments:");
        for (PatientHistory history : cancelledAppointments) {
            System.out.println("Date: " + history.getVisitDate() +
                               ", Reason: " + history.getCancellationReason());
        }
    }

    public static void patientMenu(Scanner scanner, PatientDatabase patientDatabase, Admin admin, Doctor doctor) {
        boolean exit = false;
    
        while (!exit) {
            System.out.println("\nPatient Menu");
            System.out.println("1. Signup");
            System.out.println("2. Sign in");
            System.out.println("3. Exit");
            System.out.print("Please select an option by entering the corresponding number: ");
            
            try {
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
    


