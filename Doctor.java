import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Doctor extends User { // Extend User class
    private String department;
    private List<String> availableTimes;
    private List<PatientHistory> appointments;

    public Doctor(String name, String department) {
        super(name, "WellCare");
        this.name = name;
        this.password = "WellCare";
        this.department = department;
        this.availableTimes = new ArrayList<>();
        this.appointments = new ArrayList<>();
        setDefaultTimes();  // Initialize default times when a doctor is created
    }


    public boolean signIn(String inputName, String inputPassword) {
        // Check if the input name and password match the doctor's name and password
        return this.name.equals(inputName) && this.password.equals(inputPassword);
    }

    // Method to set default available times
    private void setDefaultTimes() {
        availableTimes.add("2024-11-01 10:00 AM");
        availableTimes.add("2024-11-01 2:00 PM");
        availableTimes.add("2024-11-02 10:00 AM");
        availableTimes.add("2024-11-02 2:00 PM");
    }

    public String getDepartment() {
        return department;
    }

    public List<String> getAvailableTimes() {
        return availableTimes;
    }

    public static void DoctorMenu(Scanner scanner) {
        // Prompt for doctor's name and password
        System.out.print("Enter your doctor name: ");
        String inputName = scanner.nextLine();  // Read doctor's name
        
        // Create a doctor object (you can add more doctors if needed)
        Doctor doctor = new Doctor(inputName, "General"); // Using the inputName for doctor creation
    
        // Ask for the password
        System.out.print("Enter your password: ");
        String inputPassword = scanner.nextLine(); // Read the password
        
        // Check if the name and password match
        if (doctor.signIn(inputName, inputPassword)) {
            System.out.println("Sign-in successful. Welcome, Dr. " + doctor.getName() + "!");
            doctor.showOptions(scanner, doctor);  // Proceed to show options after successful sign-in
        } else {
            System.out.println("Sign-in failed. Please check your name and password.");
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
            System.out.println("Enter the number corresponding to your choice: " );

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
                    viewDoctorAppointments();
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
        System.out.print("Select a patient to add a diagnosis (enter the number): ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character
    
        if (choice > 0 && choice <= appointments.size()) {
            PatientHistory selectedPatientHistory = appointments.get(choice - 1);
            
            // Ask for the diagnosis
            System.out.print("Enter diagnosis: ");
            String diagnosis = scanner.nextLine();
            
            // Add the diagnosis to the patient's history
            addDiagnosisToAppointment(selectedPatientHistory, diagnosis);
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
        System.out.print("Enter available time to add (e.g., '2023-11-01 10:00 AM'): ");
        String time = scanner.nextLine();
        availableTimes.add(time);
        System.out.println("Time added successfully: " + time);
    }

    public void deleteAvailableTime(Scanner scanner) {
        System.out.println("Available times:");
        for (int i = 0; i < availableTimes.size(); i++) {
            System.out.println((i + 1) + ". " + availableTimes.get(i));
        }

        System.out.print("Enter the number of the time to delete: ");
        int index = scanner.nextInt();
        scanner.nextLine();  

        if (index > 0 && index <= availableTimes.size()) {
            String removedTime = availableTimes.remove(index - 1);
            System.out.println("Time deleted successfully: " + removedTime);
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    public void viewSchedule() {
        System.out.println("Available times for Dr. " + getName() + " in " + department + " department:");
        if (availableTimes.isEmpty()) {
            System.out.println("No available times.");
        } else {
            for (String time : availableTimes) {
                System.out.println(" - " + time);
            }
        }
    }



    // Adding an appointment (after a patient books it)
    public void addAppointment(PatientHistory patientHistory, String bookedTime) {
        appointments.add(patientHistory);
        availableTimes.remove(bookedTime);  // Remove the booked time from available times
        System.out.println("Appointment added for patient: " + patientHistory.getPatientName());
        System.out.println("Appointment time: " + patientHistory.getVisitDate());
    }

    
    
    
    
    public List<PatientHistory> getAppointments() {
        return appointments;
    }

    /*public void viewDoctorAppointments(Doctor doctor) {
        List<PatientHistory> appointments = doctor.getAppointments();
        
        if (appointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
        } else {
            System.out.println("Scheduled appointments for Dr. " + doctor.getName() + ":");
            for (PatientHistory appointment : appointments) {
                System.out.println(appointment.getPatientName() + " - " + appointment.getVisitDate());
            }
        }
    }*/
    public void viewDoctorAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments scheduled for Dr. " + getName() + ".");
        } else {
            System.out.println("Scheduled appointments for Dr. " + getName() + ":");
            for (PatientHistory appointment : appointments) {
                System.out.println(appointment.getPatientName() + " - " + appointment.getVisitDate());
            }
        }
    }

    
    

    public void removeAvailableTime(String time) {
        availableTimes.remove(time);
    
    }

    /*public static void DoctorMenu(Scanner scanner){
        Doctor doctor = new Doctor( "", "1appleadaykeepsthedoctoraway");
        Doctor.signIn(doctor, scanner);
    }*/
}
