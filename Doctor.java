import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class Doctor extends User { 
    private String department;
    private List<Patient> patients;
    private List<String> availableTimes;
    private List<PatientHistory> appointments;

    public Doctor(String name, String department) {
        super(name, "WellCare");
        this.password = "WellCare";
        this.department = department;
        this.patients = new ArrayList<>();
        this.availableTimes = new ArrayList<>();
        this.appointments = new ArrayList<>();
        setDefaultTimes();  
    }
    
    public boolean signIn(String inputName, String inputPassword) {
        return this.name.equals(inputName) && this.password.equals(inputPassword);
    }

    private void setDefaultTimes() {
        availableTimes.add("2024-12-01 10:00 AM");
        availableTimes.add("2024-12-01 2:00 PM");
        availableTimes.add("2024-12-02 10:00 AM");
        availableTimes.add("2024-12-02 2:00 PM");
    }

    public String getDepartment() {
        return department;
    }

    public List<String> getAvailableTimes() {
        return availableTimes;
    }

    public String getPassword(){
        return this.password;
    }

    public void addPatient(Patient patient) {
        this.patients.add(patient);
    }

    // Method to get the list of patients
    public List<Patient> getPatients() {
        return patients;  // Return the list of patients
    }


    public static void DoctorMenu(Scanner scanner, Admin admin) {
        System.out.print("Enter Doctor's name: ");
        String doctorName = scanner.nextLine();
        
        // Check if the doctor exists by name
        Doctor loggedInDoctor = admin.getDoctorByName(doctorName);
        
        if (loggedInDoctor != null) {
            System.out.println("Found doctor: Dr. " + loggedInDoctor.getName() + ", Department: " + loggedInDoctor.getDepartment());
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            
            if (password.equals(loggedInDoctor.getPassword())) {  
                loggedInDoctor.showOptions(scanner, loggedInDoctor);
            } 
            else {
                System.out.println("Invalid password. Access denied.");
            }
        } 
        else {
            System.out.println("Doctor not found.");
        }
    }

    public void showOptions(Scanner scanner, Doctor doctor) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Add available time");
            System.out.println("2. Delete available time");
            System.out.println("3. View schedule");
            System.out.println("4. View Appointments");
            System.out.println("5. Add Diagnosis");
            System.out.println("6. Cancel Appointment");
            System.out.println("7. Exit");
            System.out.print("Please select an option by entering the corresponding number: " );

            try{
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        addAvailableTime(scanner);
                        break;
                    case 2:
                        deleteAvailableTime(scanner);
                        break;
                    case 3:
                        viewSchedule();
                        break;
                    case 4:
                        viewDoctorAppointments(doctor);
                        break;
                    case 5:
                        viewPatientsAndAddDiagnosis(scanner);
                        break;
                    case 6: 
                        viewAppointmentsAndCancel(scanner, doctor);
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }  catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); 
                }
            }
        }

    public void viewPatientsAndAddDiagnosis(Scanner scanner) {
        System.out.println("Appointments:");
        
        if (appointments.isEmpty()) {
            System.out.println("No patients available.");
            return;
        }
    
        // Display the list of patients with appointments
        for (int i = 0; i < appointments.size(); i++) {
            PatientHistory history = appointments.get(i);
            System.out.println((i + 1) + ". " + history.getPatientName() + " - " + history.getVisitDate());
        }
    
        // Let the doctor select a patient
        int choice = -1; // Initialize to an invalid choice
        boolean validInput = false;

        // Let the doctor select a patient
        while (!validInput) {
            System.out.print("Select a patient by entering the corresponding number: ");
            
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the newline
                validInput = true; // Mark input as valid if no exception occurs
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        if (choice > 0 && choice <= appointments.size()) {
            PatientHistory selectedPatientHistory = appointments.get(choice - 1);
            
            System.out.print("Enter diagnosis: ");
            String diagnosis = scanner.nextLine();
            
            // Add the diagnosis to the patient's history
            addDiagnosisToAppointment(selectedPatientHistory, diagnosis);
            appointments.remove(choice - 1);
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }
    
    // Method to add the diagnosis to the selected patient's history
    private void addDiagnosisToAppointment(PatientHistory historyRecord, String diagnosis) {
        historyRecord.setDiagnosis(diagnosis);
        System.out.println("Diagnosis added for " + historyRecord.getPatientName() + ": " + diagnosis);
    }
    
    public void addAvailableTime(Scanner scanner) {
        System.out.print("Enter date & time to add (e.g., '2023-11-01 10:00 AM'): ");
        String time = scanner.nextLine();
        availableTimes.add(time);
        System.out.println("Time added successfully: " + time);
    }

    public void deleteAvailableTime(Scanner scanner) {
        System.out.println("Available appointment slots:");
        for (int i = 0; i < availableTimes.size(); i++) {
            System.out.println((i + 1) + ". " + availableTimes.get(i));
        }

        int index = -1;
        boolean validInput = false;
    
        while (!validInput) {
            System.out.print("Enter the number of the slot to delete: ");
            
            try {
                index = scanner.nextInt();
                scanner.nextLine(); 
                validInput = true; 
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); 
            }
        }
    
        if (index > 0 && index <= availableTimes.size()) {
            String removedSlot = availableTimes.remove(index - 1);
            System.out.println("Slot deleted successfully: " + removedSlot);
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    public void viewSchedule() {
        System.out.println("Available appointment slot for Dr. " + this.getName() + " in " + this.department + " department:");
        if (availableTimes.isEmpty()) {
            System.out.println("No available slots.");
        } else {
            for (String time : availableTimes) {
                System.out.println(" - " + time);
            }
        }
    }

    // Adding an appointment (after a patient books it)
    public void addAppointment(PatientHistory appointment, String time, String inputName) {
        if (this.name.equalsIgnoreCase(inputName)) {  
            this.appointments.add(appointment);
        } else {
            System.out.println("Appointment not added. The name doesn't match.");
        }
    }
    
    public List<PatientHistory> getAppointments() {
        return appointments;
    }

    
    public void viewDoctorAppointments(Doctor doctor) {
        if (doctor.getAppointments().isEmpty()) {
            System.out.println("No appointments scheduled for Dr. " + doctor.getName() + ".");
        } else {
            Collections.sort(doctor.getAppointments(), new Comparator<PatientHistory>() {
                @Override
                public int compare(PatientHistory appointment1, PatientHistory appointment2) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
                        Date date1 = sdf.parse(appointment1.getVisitDate());
                        Date date2 = sdf.parse(appointment2.getVisitDate());
                        return date1.compareTo(date2);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
            });
    
            System.out.println("Scheduled appointments for Dr. " + doctor.getName() + ":");
    
            // Display appointments and cancellation status
            for (int i = 0; i < doctor.getAppointments().size(); i++) {
                PatientHistory appointment = doctor.getAppointments().get(i);
                System.out.println((i + 1) + ". Patient: " + appointment.getPatientName());
                System.out.println("   Appointment Date: " + appointment.getVisitDate());
                System.out.println("   Medical Condition: " + appointment.getMedicalCondition());
                System.out.println("   Diagnosis: " + appointment.getDiagnosis());
                System.out.println("   Cancellation Status: " + (appointment.isCancelled() ? "Canceled" : "Scheduled"));
                System.out.println("-------------------------------------------------");
            }
        }
    }
    
    
    public void removeAvailableTime(String time) {
        availableTimes.remove(time);
    }

    public void cancelAppointment(Doctor doctor, int appointmentIndex) {
        if (appointmentIndex >= 0 && appointmentIndex < doctor.getAppointments().size()) {
            PatientHistory appointment = doctor.getAppointments().get(appointmentIndex);
            appointment.setCancelled(true);  
            System.out.println("Appointment for " + appointment.getPatientName() + " has been canceled.");
    
            // Find the patient who owns this appointment and cancel it in their list as well
            for (Patient patient : doctor.getPatients()) {  
                if (patient.getName().equals(appointment.getPatientName())) {
                    for (PatientHistory patientAppointment : patient.getAppointments()) {
                        if (patientAppointment.equals(appointment)) {
                            patientAppointment.setCancelled(true);  
                        }
                    }
                }
            }
        } else {
            System.out.println("Invalid appointment index.");
        }
    }
    
    public void viewAppointmentsAndCancel(Scanner scanner, Doctor doctor) {
        if (doctor.getAppointments().isEmpty()) {
            System.out.println("No appointments scheduled.");
        } else {
            System.out.println("Scheduled appointments for Dr. " + doctor.getName() + ":");
            for (int i = 0; i < doctor.getAppointments().size(); i++) {
                PatientHistory appointment = doctor.getAppointments().get(i);
                System.out.println((i + 1) + ". Patient: " + appointment.getPatientName() + " - " + appointment.getVisitDate());
            }
    
            System.out.println("Enter the number of the appointment to cancel, or 0 to exit:");
            int choice = scanner.nextInt();
    
           
            if (choice > 0 && choice <= doctor.getAppointments().size()) {
                appointments.remove(choice - 1);
                cancelAppointment(doctor, choice - 1);  
            } else {
                System.out.println("Exiting appointment cancellation.");
            }
        }
    }
    
}
