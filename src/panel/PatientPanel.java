package panel;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import model.Doctor;
import model.Appointment;
import model.AvailableTime;
import model.CanceledAppointment;
import model.MedicalHistory;
import model.Patient;
import model.Patient.Gender;
import connection.DoctorDao;
import connection.DoctorDaoJdbc;
import connection.AdminDao;
import connection.AdminDaoJdbc;
import connection.PatientDao;
import connection.PatientDaoJdbc;
import display.CanceledAppointmentTableDisplay;
import display.DisplayUtils;
import display.DoctorScheduleTableDisplay;
import display.MedicalHistoryTableDisplay;
import connection.AppointmentDAO;
import connection.AppointmentDaoJdbc;


public class PatientPanel {
    private Patient loggedInPatient; // Patient currently signed in
    private DisplayUtils display;
    private PatientDao patientDao;
    private AdminDao adminDao;
    private DoctorDao doctorDao;
    private AppointmentDAO apd;

    public PatientPanel(Patient patient, Connection connection) {
        this.loggedInPatient = patient;
        this.display = new DisplayUtils();
        this.patientDao = new PatientDaoJdbc(connection);
        this.adminDao = new AdminDaoJdbc(connection);
        this.doctorDao = new DoctorDaoJdbc(connection);
        this.apd = new AppointmentDaoJdbc(connection);
    }
   
    public void patientMenu(Scanner scanner) throws SQLException {
        boolean exit = false;
        try {
            while (!exit) {
                display.printHeader("WELCOME TO PATIENT PORTAL");
                System.out.println("1. Signup");
                System.out.println("2. Sign in");
                System.out.println("3. Exit");
                System.out.print("Please select an option by entering the corresponding number: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 
        
                switch (choice) {
                    case 1 -> signup(scanner);
                    case 2 -> signIn(scanner);
                    case 3 -> {
                        System.out.println("Exiting...");
                        exit = true;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }                
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); 
        }
    }

    public  boolean signup(Scanner scanner) throws SQLException {
        display.printHeader("PATIENT SIGN UP");
        System.out.println("Please fill in the following details to sign up. Note: Some fields are case-sensitive.\n");
    
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
    
        LocalDate dateOfBirth = null;
        while (dateOfBirth == null) {
            try {
                System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
                String input = scanner.nextLine().trim();
    
                if (input.isEmpty()) {
                    System.out.println("Date of birth cannot be empty. Please enter a valid date.");
                    continue;
                }
    
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
        String placeOfBirth = scanner.nextLine();
    
        String genderInput = null;
        Gender gender = null;
        while (gender == null) {
            try {
                System.out.print("Enter your gender (Female/Male): ");
                genderInput = scanner.nextLine().trim();
    
                if (genderInput.equalsIgnoreCase("Female")) {
                    gender = Gender.Female;
                } else if (genderInput.equalsIgnoreCase("Male")) {
                    gender = Gender.Male;
                } else {
                    System.out.println("Invalid gender. Please enter 'Female' or 'Male'.");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("Invalid input for gender.");
            }
        }
    
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
    
        System.out.print("Enter your contact number (format: 9123456789): ");
        String contactNo = scanner.nextLine();
        while (contactNo.length() != 10 || !contactNo.matches("\\d+")) {
            System.out.println("Invalid contact number. Please enter a 10-digit number.");
            System.out.print("Enter your contact number: ");
            contactNo = scanner.nextLine();
        }
    
        System.out.print("Enter your emergency contact number (format: 9123456789): ");
        String emergencyNo = scanner.nextLine();
        while (emergencyNo.length() != 10 || !emergencyNo.matches("\\d+")) {
            System.out.println("Invalid emergency contact number. Please enter a 10-digit number.");
            System.out.print("Enter your emergency contact number: ");
            emergencyNo = scanner.nextLine();
        }
    
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();
    
        System.out.print("Enter your province: ");
        String province = scanner.nextLine();
    
        System.out.print("Enter your city: ");
        String city = scanner.nextLine();
    
        System.out.print("Enter your barangay: ");
        String barangay = scanner.nextLine();
    
        System.out.print("Enter your nationality: ");
        String nationality = scanner.nextLine();
    
        System.out.print("Enter your marital status: ");
        String maritalStatus = scanner.nextLine();
    
        System.out.print("Enter any allergies (N/A if none): ");
        String allergy = scanner.nextLine();
    
        System.out.print("Enter past surgeries or treatments (N/A if none): ");
        String past = scanner.nextLine();
    
        System.out.print("Enter your blood type: ");
        String bloodtype = scanner.nextLine();
    
        System.out.print("Are you vaccinated? (yes/no): ");
        String vaccinatedInput = scanner.nextLine().trim();
        boolean vaccinated = vaccinatedInput.equalsIgnoreCase("yes");
    
        System.out.print("Do you have a disability? (Yes/No): ");
        String disabilityResponse = scanner.nextLine();

        boolean hasDisability = disabilityResponse.equalsIgnoreCase("Yes");
        String disabilityDetails = "None";

        if (hasDisability) {
            System.out.print("Please provide details about your disability: ");
            disabilityDetails = scanner.nextLine();
        }

        // Check if any required fields are missing
        if (name.isEmpty() || password.isEmpty() || address.isEmpty() || contactNo.isEmpty() || email.isEmpty() ||
            emergencyNo.isEmpty() || allergy.isEmpty() || bloodtype.isEmpty() || past.isEmpty() || province.isEmpty() ||
            barangay.isEmpty() || placeOfBirth.isEmpty() || nationality.isEmpty() || maritalStatus.isEmpty()) {
            System.out.println("Required fields are missing.");
            return false;
        }
    
        Patient newPatient = new Patient(
            "", name, password, address, province, city, barangay, placeOfBirth, dateOfBirth, contactNo, email,
            emergencyNo, gender, nationality, maritalStatus, allergy, past, bloodtype, hasDisability, vaccinated, disabilityDetails);
    
        try {
            patientDao.addPatient(newPatient);
            System.out.println("Sign-up successful! Your Patient ID is: " + patientDao.generateUniqueId());
            return true;
        } catch (Exception e) {
            System.out.println("An error occurred while saving your details. Please try again.");
            e.printStackTrace();
            return false;
        }
    }
        
    public void signIn(Scanner scanner) throws SQLException {
        if (patientDao == null) {
            System.out.println("ERROR: patientDao is not initialized.");
            return;
        }
        display.printHeader("PATIENT SIGN IN");
         
        System.out.print("Enter your name: "); 
        String name = scanner.nextLine(); 
        
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        
        loggedInPatient = patientDao.login(name, password);
        
        if (loggedInPatient != null) {
            System.out.println("Sign-in successful! Welcome, " + loggedInPatient.getName() + "!");
            showUserOptions(scanner);
        } else {
            System.out.println("Sign-in failed. Please check your credentials and try again.");
        }
    }
    
    public void showUserOptions(Scanner scanner) throws SQLException {
        boolean exit = false;
        try {

            while (!exit){
                display.printHeader("PATIENT MENU");
                System.out.println("1. Book an appointment");
                System.out.println("2. View Scheduled Appointment");
                System.out.println("3. View Cancelled Appointment");
                System.out.println("4. View medical history");
                System.out.println("5. Entered Patient Info");
                System.out.println("6. Update Patient Info");
                System.out.println("7. Log out");
                System.out.print("Choose an option: ");
                
            
                int choice = scanner.nextInt();
                scanner.nextLine();  
            
                switch (choice) {
                    case 1 -> bookAppointment(scanner);
                    case 2 -> viewPatientAppointments();
                    case 3 -> viewCanceledAppointments();
                    case 4 -> viewMedicalHistory();
                    case 5 -> {
                        if (patientDao != null) {
                            displayPatientInfo();
                        } else {
                            System.out.println("ERROR: patientDao is not initialized.");
                        }
                    }
                    case 6 -> updatePatientInfo(scanner);
                    case 7 -> {
                        exit = true;
                        System.out.println("Logging out...");
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); 
        }   
    }
    
    public void bookAppointment(Scanner scanner) {
        display.printHeader("BOOK AN APPOINTMENT");
    
        try {
            System.out.println("Available Departments:");
            List<String> departments = adminDao.getAllDepartments();
            for (int i = 0; i < departments.size(); i++) {
                System.out.println((i + 1) + ". " + departments.get(i));
            }

            System.out.print("Select a department (1-" + departments.size() + "): ");
            int departmentChoice = scanner.nextInt();
            scanner.nextLine();
            if (departmentChoice < 1 || departmentChoice > departments.size()) {
                System.out.println("Invalid choice. Try again.");
                return;
            }

            String selectedDepartmentString = departments.get(departmentChoice - 1);
            String selectedDepartmentName = selectedDepartmentString.split(",")[1].split(":")[1].trim();

            System.out.println("Selected Department: " + selectedDepartmentName);
            int selectedDepartmentId = adminDao.getDepartmentIdByName(selectedDepartmentName);

            System.out.println("Available Doctors in " + selectedDepartmentName + ":");
            List<Doctor> doctors = doctorDao.getDoctorsByDepartment(selectedDepartmentId);
            if (doctors.isEmpty()) {
                System.out.println("No doctors found for the selected department.");
                return;
            }
    
            for (int i = 0; i < doctors.size(); i++) {
                System.out.println((i + 1) + ". Dr. " + doctors.get(i).getName());
            }
    
            System.out.print("Select a doctor (1-" + doctors.size() + "): ");
            int doctorChoice = scanner.nextInt();
            scanner.nextLine();
            if (doctorChoice < 1 || doctorChoice > doctors.size()) {
                System.out.println("Invalid choice. Try again.");
                return;
            }
            Doctor selectedDoctor = doctors.get(doctorChoice - 1);
    
            System.out.println("Available Appointment Times for Dr. " + selectedDoctor.getName() + ":");
            List<AvailableTime> availableTimes = doctorDao.getAvailableTimes(selectedDoctor.getDoctorId());
    
            if (availableTimes.isEmpty()) {
                System.out.println("No available slots at this time.");
            } else {
                DoctorScheduleTableDisplay scheduleDisplay = new DoctorScheduleTableDisplay(availableTimes);
                scheduleDisplay.printTableHeader();
                scheduleDisplay.printAvailableSlots();
            }
    
            System.out.print("Select a time (1-" + availableTimes.size() + "): ");
            int timeChoice = scanner.nextInt();
            scanner.nextLine();
            if (timeChoice < 1 || timeChoice > availableTimes.size()) {
                System.out.println("Invalid choice. Try again.");
                return;
            }
    
            AvailableTime selectedAvailableTime = availableTimes.get(timeChoice - 1);
            String selectedTime = selectedAvailableTime.getAvailableTime();
            System.out.print("Describe your medical condition: ");
            String medicalCondition = scanner.nextLine();
    
            System.out.println("name" + selectedDepartmentName);
            Appointment appointment = new Appointment(
                0,
                selectedDoctor.getDoctorId(),
                selectedDoctor.getName(),
                loggedInPatient.getUniqueId(),
                loggedInPatient.getName(),
                selectedDepartmentName,
                selectedTime,
                medicalCondition,
                "Scheduled",
                null
            );
    
            apd.createAppointment(appointment, adminDao);
            System.out.println("Appointment successfully booked!");
    
            apd.removeBookedTime(selectedAvailableTime.getAvailableTimeId());
            System.out.println("The booked time has been removed from the available times.");
        } catch (Exception e) {
            System.out.println("Error occurred while booking appointment: " + e.getMessage());
            e.printStackTrace();
        }
    }

            
    public void viewPatientAppointments() {
        display.printHeader("SCHEDULED APPOINTMENTS");
        
        List<Appointment> appointments = apd.getAppointmentsByPatientId(loggedInPatient.getUniqueId());
        
        if (appointments == null || appointments.isEmpty()) {
            System.out.println("You have no scheduled appointments.");
            return;
        }
        
        int count = 1;
        for (Appointment appointment : appointments) {
            if ("scheduled".equalsIgnoreCase(appointment.getStatus())) {
                System.out.println("-------------------------------------------------");
                System.out.println("Appointment " + count++);
                System.out.println("Time: " + appointment.getAppointmentTime());
                System.out.println("Doctor: " + appointment.getDoctorName());
                System.out.println("Department: " + appointment.getDepartment());
                System.out.println("Condition: " + appointment.getMedicalCondition());
                System.out.println("Status: " + appointment.getStatus());
                System.out.println("-------------------------------------------------");
            }
        }
    }
         
    public void viewCanceledAppointments() throws SQLException {
        display.printHeader("CANCELED APPOINTMENTS");

        List<CanceledAppointment> canceledAppointments = patientDao.getCanceledAppointments(loggedInPatient.getUniqueId());
        if (canceledAppointments == null || canceledAppointments.isEmpty()) {
            System.out.println("You have no canceled appointments.");
            return;
        }
        CanceledAppointmentTableDisplay canceledAppointmentTableDisplay = new CanceledAppointmentTableDisplay(canceledAppointments);
        canceledAppointmentTableDisplay.printTableHeader();
        canceledAppointmentTableDisplay.printCanceledAppointments();
    }
        
    public void viewMedicalHistory() {
        display.printHeader("VIEW MEDICAL HISTORY");

        List<MedicalHistory> medicalHistoryList;
        try {
            medicalHistoryList = patientDao.getMedicalHistory(loggedInPatient.getUniqueId());
        } catch (SQLException e) {
            System.out.println("Error fetching medical history: " + e.getMessage());
            return;
        }

        if (medicalHistoryList.isEmpty()) {
            System.out.println("You do not have any medical history.");
            return;
        }

        MedicalHistoryTableDisplay medicalHistoryTableDisplay = new MedicalHistoryTableDisplay(medicalHistoryList);
        medicalHistoryTableDisplay.printTableHeader();
        medicalHistoryTableDisplay.printMedicalHistory();
    }
        
    public void displayPatientInfo() {
        display.printHeader("PATIENT INFORMATION");
    
        Patient patient = patientDao.getPatientDetails(loggedInPatient.getUniqueId());
    
        if (patient != null) {
            printPatientDetail("Id", patient.getUniqueId());
            printPatientDetail("Name", patient.getName());
            printPatientDetail("Birthday", patient.getBday() != null ? patient.getBday().toString() : "N/A");
            printPatientDetail("Place of Birth", patient.getPlaceOfBirth());
            printPatientDetail("Gender", patient.getGender() != null ? patient.getGender().name() : "N/A");  
            printPatientDetail("Email", patient.getEmail());
            printPatientDetail("Contact Number", patient.getContactNo());
            printPatientDetail("Emergency Contact", patient.getEmergencyNo());
            printPatientDetail("Address", patient.getAddress());
            printPatientDetail("Province", patient.getProvince());
            printPatientDetail("City", patient.getCity());
            printPatientDetail("Barangay", patient.getBarangay());
            printPatientDetail("Nationality", patient.getNationality());
            printPatientDetail("Marital Status", patient.getMaritalStatus());
            printPatientDetail("Allergies", patient.getAllergy());
            printPatientDetail("Past Surgeries/Treatments", patient.getPast());
            printPatientDetail("Blood Type", patient.getBloodtype());
            printYesNoDetail("Already vaccinated", patient.isVaccinated());

    
            if (patient.isHasDisability()) {
                System.out.printf("%-30s %-30s\n", "Disability/disabilities:", "Yes");
                printPatientDetail("Disability Details", patient.getDisabilityDetails());
            } else {
                System.out.printf("%-30s %-30s\n", "Disability/disabilities:", "None");
            }
        } else {
            System.out.println("Patient not found!");
        }
    }

    private void printPatientDetail(String label, String value) {
        System.out.printf("%-30s %-30s\n", label + ":", value != null ? value : "N/A");
    }
    
    private void printYesNoDetail(String label, boolean value) {
        System.out.printf("%-30s %-30s\n", label + ":", value ? "Yes" : "No");
    }

    public void updatePatientInfo(Scanner scanner) {
        display.printHeader("UPDATE PATIENT INFORMATION");

        boolean exit = false;
        Patient patient = patientDao.getPatientDetails(loggedInPatient.getUniqueId());
        while (!exit) {
            System.out.println("\nYour Current Information:");
            System.out.println("1. Name: " + patient.getName());
            System.out.println("2. Password: " + patient.getPassword());
            System.out.println("3. Birthdate: " + patient.getBday());
            System.out.println("4. Place of Birth: " + patient.getPlaceOfBirth());
            System.out.println("5. Gender: " + patient.getGender());
            System.out.println("6. Email: " + patient.getEmail());
            System.out.println("7. Contact Number: " + patient.getContactNo());
            System.out.println("8. Emergency Contact: " + patient.getEmergencyNo());
            System.out.println("9. Address: " + patient.getAddress());
            System.out.println("10. Province: " + patient.getProvince());
            System.out.println("11. City: " + patient.getCity());
            System.out.println("12. Barangay: " + patient.getBarangay());
            System.out.println("13. Nationality: " + patient.getNationality());
            System.out.println("14. Marital Status: " + patient.getMaritalStatus());
            System.out.println("15. Allergy: " + patient.getAllergy());
            System.out.println("16. Past Medical History: " + patient.getPast());
            System.out.println("17. Blood Type: " + patient.getBloodtype());
            System.out.println("18. Vaccinated: " + (patient.isVaccinated() ? "Yes" : "No"));
            System.out.println("19. Disability: " + (patient.isHasDisability() ? "Yes" : "No"));
            System.out.println("20. Disability Details: " + patient.getDisabilityDetails());
            System.out.println("21. Exit");
    
            System.out.print("Enter the number corresponding to the field you want to update (or 21 to exit): ");
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter new Name: ");
                    patient.setName(scanner.nextLine());
                }
                case 2 -> {
                    System.out.print("Enter new Password: ");
                    patient.setPassword(scanner.nextLine());
                }
                case 3 -> {
                    System.out.print("Enter new Birthdate (YYYY-MM-DD): ");
                    patient.setBday(LocalDate.parse(scanner.nextLine()));
                }
                case 4 -> {
                    System.out.print("Enter new Place of Birth: ");
                    patient.setPlaceOfBirth(scanner.nextLine());
                }
                case 5 -> {
                    System.out.print("Enter new Gender (Male/Female): ");
                    String genderInput = scanner.nextLine().trim();
                    
                    if (genderInput.equalsIgnoreCase("Female")) {
                        patient.setGender(Gender.Female); 
                    } else if (genderInput.equalsIgnoreCase("Male")) {
                        patient.setGender(Gender.Male); 
                    } else {
                        System.out.println("Invalid gender. Please enter 'Male' or 'Female'.");
                    }
                }
                case 6 -> {
                    System.out.print("Enter new Email: ");
                    patient.setEmail(scanner.nextLine());
                }
                case 7 -> {
                    System.out.print("Enter new Contact Number: ");
                    patient.setContactNo(scanner.nextLine());
                }
                case 8 -> {
                    System.out.print("Enter new Emergency Contact: ");
                    patient.setEmergencyNo(scanner.nextLine());
                }
                case 9 -> {
                    System.out.print("Enter new Address: ");
                    patient.setAddress(scanner.nextLine());
                }
                case 10 -> {
                    System.out.print("Enter new Province: ");
                    patient.setProvince(scanner.nextLine());
                }
                case 11 -> {
                    System.out.print("Enter new City: ");
                    patient.setCity(scanner.nextLine());
                }
                case 12 -> {
                    System.out.print("Enter new Barangay: ");
                    patient.setBarangay(scanner.nextLine());
                }
                case 13 -> {
                    System.out.print("Enter new Nationality: ");
                    patient.setNationality(scanner.nextLine());
                }
                case 14 -> {
                    System.out.print("Enter new Marital Status: ");
                    patient.setMaritalStatus(scanner.nextLine());
                }
                case 15 -> {
                    System.out.print("Enter new Allergy Info: ");
                    patient.setAllergy(scanner.nextLine());
                }
                case 16 -> {
                    System.out.print("Enter new Past Medical History: ");
                    patient.setPast(scanner.nextLine());
                }
                case 17 -> {
                    System.out.print("Enter new Blood Type: ");
                    patient.setBloodtype(scanner.nextLine());
                }
                case 18 -> {
                    System.out.print("Are you Vaccinated? (yes/no): ");
                    patient.setVaccinated(scanner.nextLine().equalsIgnoreCase("yes"));
                }
                case 19 -> {
                    System.out.print("Do you have a Disability? (yes/no): ");
                    patient.setHasDisability(scanner.nextLine().equalsIgnoreCase("yes"));
                }
                case 20 -> {
                    System.out.print("Enter new Disability Details: ");
                    patient.setDisabilityDetails(scanner.nextLine());
                }
                case 21 -> {
                    System.out.println("Exiting update menu...");
                    exit = true;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
    
            patientDao.updatePatient(patient);
            System.out.println("Patient information updated successfully.");
        }
    }

}



