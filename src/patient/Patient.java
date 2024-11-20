package patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import doctor_admin.Admin; 
import doctor_admin.Doctor;
import display.DisplayUtils;
import user.User;

public class Patient extends User {
    private String patientId;
    private String placeOfBirth;
    private String address;
    private String province;
    private String city;
    private String barangay;
    private LocalDate bday;
    private String contactNo;
    private String email;
    private String emergencyNo;
    private Gender gender;
    private String nationality;
    private String maritalStatus;
    private String allergy;
    private String past; // past surgeries or treatments
    private String bloodtype;
    private boolean hasDisability;
    private String disabilityDetails;
    private boolean isVaccinated;
    private List<PatientHistory> historyRecords = new ArrayList<>();
    private List<PatientHistory> appointments;
    private List<PatientHistory> cancelledAppointments;
    private Admin admin; 
    private DisplayUtils display;

    enum Gender {
        Female, Male
    }

    public Patient(String patientId, String name, String password, String address, String province, String barangay, String placeOfBirth, LocalDate bday, String contactNo, String email,
                   String emergencyNo, Gender gender, String nationality, String maritalStatus, String allergy, String past, String bloodtype, boolean hasDisability, boolean isVaccinated, Admin admin, DisplayUtils display) {
        super(name, password); 
        this.address = address;
        this.province =  province;
        this.barangay = barangay;
        this.bday = bday;
        this.placeOfBirth = placeOfBirth;
        this.contactNo = contactNo;
        this.email = email;
        this.emergencyNo = emergencyNo;
        this.gender = gender;
        this.nationality = nationality;
        this.maritalStatus =  maritalStatus;
        this.allergy = allergy;
        this.past = past;
        this.bloodtype = bloodtype;
        this.admin = admin;
        this.patientId = patientId;
        this.hasDisability = hasDisability;
        this.isVaccinated = isVaccinated;
        this.display = display;
        this.appointments = new ArrayList<>();
        this.cancelledAppointments = new ArrayList<>();
        this.historyRecords = new ArrayList<>();
    }

    @Override
    public String toString() { 
        return getName(); 
    } 

    public Patient(String name, String password) { 
        super(name, password);  
    }

    public String getUniqueId() {
        return patientId;
    }

    public String getName() {
        return super.getName(); 
    }

    public Admin getAdmin() { 
        return admin; 
    }

    public DisplayUtils getDisplay(){
        return display;
    }

    public Gender getGender() { 
        return gender; 
    }

    public LocalDate getBday() {
        return bday;
    }

    public void setBday(LocalDate bday) {
        this.bday = bday;
    }

    public List<PatientHistory> getAppointments() { 
        return appointments; 
    }

    public List<PatientHistory> getHistoryRecords() { 
        return historyRecords; 
    }
    
    public void addAppointment(PatientHistory appointment) {
        this.appointments.add(appointment);  
    }

    public void removeAppointment(PatientHistory appointment) {
        this.appointments.remove(appointment);  
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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
    
    private String generateUniqueId(PatientDatabase patientDatabase) {
        String date = java.time.LocalDate.now().toString().replace("-", "");
        int patientNumber = patientDatabase.getPatientCount() + 1; 
        return "P" + date + String.format("%05d", patientNumber);
    }

    public boolean signup(Scanner scanner, PatientDatabase patientDatabase, Admin admin, DisplayUtils display) {
        display.printHeader("PATIENT SIGN UP");
        System.out.println("Please fill in the following details to sign up. Note: Some fields are case-sensitive.\n");
    
        System.out.print("Enter your name: ");
        this.name = scanner.nextLine();
    
        System.out.print("Enter your password: ");
        this.password = scanner.nextLine();
    
        LocalDate dateOfBirth = null;

        while (dateOfBirth == null) {
            try {
                System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
                String input = scanner.nextLine();
                dateOfBirth = LocalDate.parse(input);

                if (dateOfBirth.isAfter(LocalDate.now())) {
                    System.out.println("Date of birth cannot be in the future. Please enter a valid date.");
                    dateOfBirth = null; 
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        System.out.print("Enter your place of birth: ");
        this.placeOfBirth = scanner.nextLine();

        String genderInput = null;
        while (genderInput == null){
            try{
                System.out.print("Enter your gender (Female/Male): ");
                genderInput = scanner.nextLine().trim();

                if (genderInput.equalsIgnoreCase("Female")) {
                    this.gender = Gender.Female;
                } else if (genderInput.equalsIgnoreCase("Male")) {
                    this.gender = Gender.Male;
                } else {
                    System.out.println("Invalid gender. Please enter 'Female' or 'Male'.");
                    genderInput = null;
                }
            }catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        System.out.print("Enter your email: ");
        this.email = scanner.nextLine();

        System.out.print("Enter your contact number: ");
        this.contactNo = scanner.nextLine();

        System.out.print("Enter your emergency contact number: ");
        this.emergencyNo = scanner.nextLine();
    
        System.out.print("Enter your address: ");
        this.address = scanner.nextLine();
    
        System.out.print("Enter your province: ");
        this.province = scanner.nextLine();
    
        System.out.print("Enter your city: ");
        this.city = scanner.nextLine();

        System.out.print("Enter your barangay: ");
        this.barangay = scanner.nextLine();
    
        System.out.print("Enter your nationality: ");
        this.nationality = scanner.nextLine();
    
        System.out.print("Enter your marital status: ");
        this.maritalStatus = scanner.nextLine();
    
        System.out.print("Enter any allergies (N/A if none): ");
        this.allergy = scanner.nextLine();
    
        System.out.print("Enter past surgeries or treatments (N/A if none): ");
        this.past = scanner.nextLine();
    
        System.out.print("Enter your blood type: ");
        this.bloodtype = scanner.nextLine();
    
        System.out.print("Are you vaccinated? (yes/no): ");
        String vaccinatedInput = scanner.nextLine().trim();
        if (vaccinatedInput.equalsIgnoreCase("yes")) {
            isVaccinated = true;
        } else if (vaccinatedInput.equalsIgnoreCase("no")) {
            isVaccinated = false;
        } else {
            System.out.println("Invalid input for vaccination. Please answer 'yes' or 'no'.");
            return false;
        }

        System.out.print("Do you have a disability? (Yes/No): ");
        String disabilityResponse = scanner.nextLine();

        if (disabilityResponse.equalsIgnoreCase("Yes")) {
            hasDisability = true;
            System.out.print("Please enter your disability details: ");
            disabilityDetails = scanner.nextLine();
        } else {
            hasDisability = false;
            disabilityDetails = "None"; 
        }

        if (name.isEmpty() || password.isEmpty() || address.isEmpty() ||  contactNo.isEmpty() ||
            email.isEmpty() || emergencyNo.isEmpty() || allergy.isEmpty() || bloodtype.isEmpty() || past.isEmpty() ||
            province.isEmpty() || barangay.isEmpty() || placeOfBirth.isEmpty() || nationality.isEmpty() ||
            maritalStatus.isEmpty()) {
            System.out.println("Required fields are missing.");
            return false;
        }
    

        this.patientId = generateUniqueId(patientDatabase);
        patientDatabase.addPatient(this);
        System.out.println("Sign-up successful! Your Patient ID is: " + getUniqueId());
        return true;
        }

    


    public static void signIn(Scanner scanner, PatientDatabase patientDatabase, Admin admin, Doctor doctor, DisplayUtils display) {
        display.printHeader("PATIENT SIGN IN");
        System.out.print("Enter your full name (Note: this is case sensitive): ");
        String inputName = scanner.nextLine();

        System.out.print("Enter your password: ");
        String inputPassword = scanner.nextLine();

        Patient foundPatient = patientDatabase.findPatient(inputName, inputPassword);

        if (foundPatient != null && foundPatient.getPassword().equals(inputPassword)) {
            System.out.println("Sign-in successful. Welcome, " + foundPatient.getName() + "!");
            foundPatient.showUserOptions(scanner, admin, doctor, display);
        } else {
            System.out.println("Sign-in failed. Patient not found or invalid password.");
        }
    }

    public void showUserOptions(Scanner scanner, Admin admin, Doctor doctor, DisplayUtils display) {
       boolean exit = false;

        while (!exit){
            display.printHeader("PATIENT MENU");
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
                    bookAppointment(admin, scanner, doctor, display);
                    break;
                case 2:
                    viewMedicalHistory(display);
                    break;
                case 3:
                    displayPatientInfo(display);
                    break;
                case 4: 
                    viewPatientAppointments(display);
                    break;
                case 5:
                    viewCancelledAppointments(display);
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

    public void bookAppointment(Admin admin, Scanner scanner, Doctor doctor, DisplayUtils display) {
        display.printHeader("SCHEDULE AN APPOINTMENT");
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

    public void viewMedicalHistory(DisplayUtils display) {
        display.printHeader("MEDICAL HISTORY RECORDS");
        if (historyRecords != null && !historyRecords.isEmpty()) {
            for (PatientHistory record : historyRecords) {
                System.out.println(record.toString());
                System.out.println("---------------------------------------------------------------------------------------------");  
            }                         
        } else {
            System.out.println("No medical history records found.");
        }
    }
    
    
    public void displayPatientInfo(DisplayUtils display) {
        display.printHeader("PATIENT INFORMATION");
        System.out.printf("%-30s %-30s\n", "Id: ", patientId);
        System.out.printf("%-30s %-30s\n", "Name:", name);
        System.out.printf("%-30s %-30s\n", "Birthday:", bday);
        System.out.printf("%-30s %-30s\n", "Place of Birth:", placeOfBirth);
        System.out.printf("%-30s %-30s\n", "Gender:", gender);
        System.out.printf("%-30s %-30s\n", "Email:", email);
        System.out.printf("%-30s %-30s\n", "Contact Number:", contactNo);
        System.out.printf("%-30s %-30s\n", "Emergency Contact:", emergencyNo);
        System.out.printf("%-30s %-30s\n", "Address:", address);
        System.out.printf("%-30s %-30s\n", "Province:", province);
        System.out.printf("%-30s %-30s\n", "City:", city);
        System.out.printf("%-30s %-30s\n", "Barangay:", barangay);
        System.out.printf("%-30s %-30s\n", "Nationality:", nationality);
        System.out.printf("%-30s %-30s\n", "Marital Status:", maritalStatus);
        System.out.printf("%-30s %-30s\n", "Allergies:", allergy);
        System.out.printf("%-30s %-30s\n", "Past Surgeries/Treatments:", past);
        System.out.printf("%-30s %-30s\n", "Blood Type:", bloodtype);
        System.out.printf("%-30s %-30s\n", "Already vaccinated:", isVaccinated ? "Yes" : "No");
        if (hasDisability) {
            System.out.printf("%-30s %-30s\n", "Disability/disabilities:", "Yes");
            System.out.printf("%-30s %-30s\n", "Disability Details:", disabilityDetails);
        } else {
            System.out.printf("%-30s %-30s\n", "Disability/disabilities:", "None");
        }
    }
    
    
    
    public void viewPatientAppointments(DisplayUtils display) {
        display.printHeader("SCHEDULED APPOINTMENTS");
        List<PatientHistory> appointments = getAppointments(); 
    
        if (appointments == null || appointments.isEmpty()) {
            System.out.println("You have no scheduled appointments.");
            return;
        }

        System.out.println("Scheduled appointments for " + getName() + ":");
        for (PatientHistory appointment : appointments) {
            if (!appointment.isCancelled()) { 
                System.out.println("-------------------------------------------------");
                System.out.println(appointment.toStringForAppointment(true));
                System.out.println("-------------------------------------------------");
            }
        }
    }
    
    public void viewCancelledAppointments(DisplayUtils display) {
        display.printHeader("CANCELLED APPOINTMENTS");
        if (cancelledAppointments == null || cancelledAppointments.isEmpty()) {
            System.out.println("No cancelled appointments.");
            return;
        }
        
        for (PatientHistory history : cancelledAppointments) {
            System.out.println("Date: " + history.getVisitDate() +
                               "\nDoctor: Dr. " + history.getDoctorName() +
                               "\nReason: " + history.getCancellationReason());
            System.out.println("------------------------------------------------------------------------------------------");
        }
    }

    public static void patientMenu(Scanner scanner, PatientDatabase patientDatabase, Admin admin, Doctor doctor, DisplayUtils display) {
        boolean exit = false;
    
        while (!exit) {
            display.printHeader("WELCOME TO PATIENT PORTAL");
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
                        boolean success = newPatient.signup(scanner, patientDatabase, admin, display);
                        if (success) {
                            System.out.println("Patient signed up successfully.");
                        } else {
                            System.out.println("Signup failed. Please try again.");
                        }
                        break;
                    case 2:
                        Patient.signIn(scanner, patientDatabase, admin, doctor, display);
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



