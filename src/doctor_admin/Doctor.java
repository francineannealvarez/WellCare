package doctor_admin;

import java.util.Scanner;
import patient.Patient;
import patient.PatientHistory;
import patient.PatientDatabase;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import user.User;


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
    
    public String getPassword(){
        return this.password;
    }

    public void addPatient(Patient patient) {
        this.patients.add(patient);
    }

    public List<Patient> getPatients() {
        return patients; 
    }

    public boolean signIn(String inputName, String inputPassword) {
        return this.name.equals(inputName) && this.password.equals(inputPassword);
    }

    public String getDepartment() {
        return department;
    }

    public List<String> getAvailableTimes() {
        return availableTimes;
    }

    public List<PatientHistory> getAppointments() {
        return appointments;
    }

    private void setDefaultTimes() {
        // Add a fixed time slot directly
        availableTimes.add("Dec 12, 2024 10:00 AM");
        availableTimes.add("Dec 12, 2024 2:00 PM");
        availableTimes.add("Dec 13, 2024 10:00 AM");
        availableTimes.add("Dec 13, 2024 2:00 PM");
        availableTimes.add("Dec 14, 2024 10:00 AM");
        availableTimes.add("Dec 14, 2024 2:00 PM");
    }
    

    public static void DoctorMenu(Scanner scanner, Admin admin, PatientDatabase patientDatabase) {
        System.out.print("Enter Doctor's name: ");
        String doctorName = scanner.nextLine();
        
        Doctor loggedInDoctor = admin.getDoctorByName(doctorName);
        
        if (loggedInDoctor != null) {
            System.out.println("Found doctor: Dr. " + loggedInDoctor.getName() + ", Department: " + loggedInDoctor.getDepartment());
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            
            if (password.equals(loggedInDoctor.getPassword())) {  
                loggedInDoctor.showOptions(scanner, loggedInDoctor, patientDatabase);
            } 
            else {
                System.out.println("Invalid password. Access denied.");
            }
        } 
        else {
            System.out.println("Doctor not found.");
        }
    }

    public void showOptions(Scanner scanner, Doctor doctor, PatientDatabase patientDatabase) {
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
                        addDiagnosis(scanner, patientDatabase);
                        break;
                    case 6: 
                        cancelAppointments(scanner, doctor);
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

    public void addAvailableTime(Scanner scanner) {
        System.out.print("Enter date & time to add (e.g., '2023-11-01 10:00 AM'): ");
        String time = scanner.nextLine();
        if (!availableTimes.contains(time)) {
            availableTimes.add(time);
            System.out.println("Time added successfully: " + time);
        } else {
            System.out.println("This time slot already exists.");
        }
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

    public void viewDoctorAppointments(Doctor doctor) {
        if (doctor.getAppointments().isEmpty()) {
            System.out.println("No appointments scheduled for Dr. " + doctor.getName() + ".");
        } else {
            // Sorting appointments by date
            Collections.sort(doctor.getAppointments(), new Comparator<PatientHistory>() {
                @Override
                public int compare(PatientHistory appointment1, PatientHistory appointment2) {
                    try {
                        // Adjusting the SimpleDateFormat to handle the format used for appointment date
                        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
                        Date date1 = sdf.parse(appointment1.getVisitDate()); // Dec 14, 2024 10:00 AM
                        Date date2 = sdf.parse(appointment2.getVisitDate());
                        return date1.compareTo(date2);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
            });
    
            // Printing header for the table
            System.out.printf("%-5s %-20s %-20s %-30s %-20s\n", "No.", "Patient", "Diagnosis", "Appointment Date", "Status");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
    
            // Displaying the appointments
            for (int i = 0; i < doctor.getAppointments().size(); i++) {
                PatientHistory appointment = doctor.getAppointments().get(i);
    
                // Fetching patient details
                String patientName = appointment.getPatient().getName();
                String diagnosis = appointment.getDiagnosis();
                String visitDate = appointment.getVisitDate();
                String status = appointment.isCancelled() ? "Cancelled" : "Scheduled";
    
                // Parsing visitDate for more consistent formatting
                String formattedDate = formatDate(visitDate);
    
                // Printing appointment details in table format
                System.out.printf("%-5d %-20s %-20s %-30s %-20s\n", i + 1, patientName, diagnosis, formattedDate, status);
            }
    
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
        }
    }
    
    // Method to format the visit date for display (e.g., Dec 14, 2024 10:00 AM)
    private String formatDate(String visitDate) {
        try {
            // Using the same format used for appointment date parsing
            SimpleDateFormat sdfInput = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
            Date date = sdfInput.parse(visitDate);
    
            // Formatting date for better readability
            SimpleDateFormat sdfOutput = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
            return sdfOutput.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return visitDate;  // In case of error, return original visitDate
        }
    }
    
    
    public void addDiagnosis(Scanner scanner, PatientDatabase patientDatabase) {
        System.out.println("Appointments:");
        
        if (appointments.isEmpty()) {
            System.out.println("No patients available.");
            return;
        }
    
        for (int i = 0; i < appointments.size(); i++) {
            PatientHistory history = appointments.get(i);
            if ("Pending".equals(history.getDiagnosis())) { 
                System.out.println((i + 1) + ". " + history);
            }
        }
    
        // Let the doctor select a patient to diagnose
        int choice = -1;
        while (choice < 1 || choice > appointments.size()) {
            System.out.print("Select a patient by entering the corresponding number: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 1 || choice > appointments.size()) {
                    System.out.println("Invalid choice. Please select a number between 1 and " + appointments.size() + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); 
            }
        }
    
        PatientHistory selectedPatientHistory = appointments.get(choice - 1);
    
        System.out.print("Enter diagnosis for " + selectedPatientHistory.getPatient() + ": ");
        String diagnosis = scanner.nextLine();
    
        // Use the addDiagnosisToAppointment method to update the diagnosis and status
        addDiagnosisToAppointment(selectedPatientHistory, diagnosis);
    
        // Remove this appointment from the scheduled appointments list since diagnosis is added
        appointments.remove(selectedPatientHistory);
        System.out.println("The appointment has been removed from the scheduled list.");
        
        // Optionally, add the diagnosed appointment to the patient's medical history here if needed
        addToMedicalHistory(selectedPatientHistory, patientDatabase);
    }
    
        // Method to add the diagnosed appointment to the patient's medical history
        public void addToMedicalHistory(PatientHistory patientHistory, PatientDatabase patientDatabase) {
            Patient patient = patientHistory.getPatient(); // Get the associated patient
            if (patient != null) {
                // Add the PatientHistory object to the patient's history list
                patient.getHistoryRecords().add(patientHistory);
                System.out.println("Patient's medical history updated.");
            } else {
                System.out.println("Error: Could not find the patient to update their medical history.");
            }
        }
     // Method to add diagnosis to the appointment and update status
     public void addDiagnosisToAppointment(PatientHistory patientHistory, String diagnosis) {
        if ("Pending".equals(patientHistory.getDiagnosis())) {
            patientHistory.setDiagnosis(diagnosis);  // Update the diagnosis
            System.out.println("Diagnosis added: " + diagnosis);
        } else {
            System.out.println("Diagnosis already exists or is not pending.");
        }
    }
    
    
    public void cancelAppointments(Scanner scanner, Doctor doctor) {
        List<PatientHistory> appointments = doctor.getAppointments();
        if (appointments.isEmpty()) {
            System.out.println("No scheduled appointments for Dr. " + doctor.getName() + ".");
            return;
        }
    
        // Display the list of appointments
        System.out.println("Scheduled appointments for Dr. " + doctor.getName() + ":");
        for (int i = 0; i < appointments.size(); i++) {
            PatientHistory appointment = appointments.get(i);
            System.out.println((i + 1) + ". Patient: " + appointment.getPatient().getName() + 
                               " - " + appointment.getVisitDate());
        }
    
        System.out.print("Enter the number of the appointment to cancel, or 0 to exit: ");
        int choice = -1;
    
        if (scanner.hasNextInt()) {
            choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer
    
            if (choice == 0) {
                System.out.println("Exiting cancellation process.");
                return;
            }
    
            if (choice > 0 && choice <= appointments.size()) {
                // Proceed with cancellation
                System.out.print("Enter the reason for cancellation: ");
                String reason = scanner.nextLine();
    
                // Retrieve and remove the selected appointment
                PatientHistory selectedAppointment = appointments.get(choice - 1);
    
                // Remove from doctor's appointments
                appointments.remove(selectedAppointment);
    
                // Remove from patient's scheduled appointments
                Patient patient = selectedAppointment.getPatient();
                if (patient != null) {
                    patient.getAppointments().remove(selectedAppointment);
    
                    // Mark the appointment as cancelled and log the reason
                    selectedAppointment.setCancelled(true);
                    selectedAppointment.setCancellationReason(reason);
    
                    // Add to patient's cancelled appointments
                    patient.addCancelledAppointment(selectedAppointment);
    
                    System.out.println("Appointment successfully cancelled for patient: " +
                                       patient.getName());
                } else {
                    System.out.println("Error: The patient object is null.");
                }
            } else {
                System.out.println("Invalid selection. No appointment cancelled.");
            }
        } else {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); 
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
    
    public void removeAvailableTime(String time) {
        availableTimes.remove(time);
    }

}
