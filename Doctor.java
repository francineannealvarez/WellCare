import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Doctor extends User { 
    private String department;
    private List<String> availableTimes;
    private List<PatientHistory> appointments;

    public Doctor(String name, String department) {
        super(name, "WellCare");
        this.password = "WellCare";
        this.department = department;
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

    public static void DoctorMenu(Scanner scanner, Admin admin) {
        System.out.print("Enter Doctor's name to view appointments: ");
        String doctorName = scanner.nextLine();
        
        // Check if the doctor exists by name
        Doctor loggedInDoctor = admin.getDoctorByName(doctorName);
        
        if (loggedInDoctor != null) {
            System.out.println("Found doctor: " + loggedInDoctor.getName() + ", Department: " + loggedInDoctor.getDepartment());
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
            System.out.println("6. Exit");
            System.out.println("Please select an option by entering the corresponding number: " );

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
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
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
        System.out.print("Select a patient by entering the corresponding number: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); 
    
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

        System.out.print("Enter the number of the slot to delete:");
        int index = scanner.nextInt();
        scanner.nextLine();  

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
            System.out.println("Appointment added for Dr. " + getName() + ": " + appointment.getPatientName() + " at " + time);
        } else {
            System.out.println("Appointment not added. The name doesn't match.");
        }
    }
    
    public List<PatientHistory> getAppointments() {
        return appointments;
    }

    public void viewDoctorAppointments(Doctor doctor) {
        //System.out.println("Total appointments for Dr. " + doctor.getName() + ": " + doctor.getAppointments().size());  
        if (doctor.getAppointments().isEmpty()) {
            System.out.println("No appointments scheduled for Dr. " + doctor.getName() + ".");
        } else {
            System.out.println("Scheduled appointments for Dr. " + doctor.getName() + ":");
            for (PatientHistory appointment : doctor.getAppointments()) {
                System.out.println(appointment.getPatientName() + " - " + appointment.getVisitDate());
            }
        }
    }
    
    public void removeAvailableTime(String time) {
        availableTimes.remove(time);
    
    }
}
