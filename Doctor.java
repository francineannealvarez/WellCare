import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Doctor extends User { // Extend User class
    private String department;
    private List<String> availableTimes;
    private List<PatientHistory> appointments;

    
    public Doctor(String name, String department) {
        super(name, "1appleadaykeepsthedoctoraway");
        this.name = name;
        this.password = "1appleadaykeepsthedoctoraway";
        this.department = department;
        this.availableTimes = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }


    public String getDepartment() {
        return department;
    }

    public List<String> getAvailableTimes() {
        return availableTimes;
    }

    public static void signIn(Doctor doctor) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Doctor Sign-In:");
        System.out.print("Enter your name: ");
        String inputName = scanner.nextLine();

        System.out.print("Enter your password: ");
        String inputPassword = scanner.nextLine();

  
        if (doctor.getName().equals(inputName) && doctor.signIn(inputPassword)) {
            System.out.println("Sign-in successful. Welcome, Dr. " + doctor.getName() + "!");
            doctor.showOptions(); 
        } else {
            System.out.println("Sign-in failed. Please check your name and password.");
        }
        scanner.close();
    }


    public void showOptions() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Add available time");
            System.out.println("2. Delete available time");
            System.out.println("3. View schedule");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); 
            
            switch (choice) {
                case 1:
                    addAvailableTime();
                    break;
                case 2:
                    deleteAvailableTime();
                    break;
                case 3:
                    viewSchedule();
                    break;
                case 4:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            scanner.close();
        }
    }

    public void addAvailableTime() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter available time to add (e.g., '2023-11-01 10:00 AM'): ");
        String time = scanner.nextLine();
        availableTimes.add(time);
        System.out.println("Time added successfully: " + time);
        scanner.close();
    }

    public void deleteAvailableTime() {
        Scanner scanner = new Scanner(System.in);
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
        scanner.close();
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
    public void addAppointment(PatientHistory patientHistory) {
        appointments.add(patientHistory);
        removeAvailableTime(patientHistory.getVisitDate()); // Use getVisitDate to remove the correct time
    }

    public void viewAppointments() {
        System.out.println("Appointments for Dr. " + getName() + ":");
        for (PatientHistory appointment : appointments) {
            System.out.println(appointment);
        }
    }

    public void removeAvailableTime(String time) {
        if (availableTimes.remove(time)) {
            System.out.println("Time removed successfully: " + time);
        } else {
            System.out.println("Time not found: " + time);
        }
    }

    public static void DoctorMenu(){
        Doctor doctor = new Doctor( "", "1appleadaykeepsthedoctoraway");
        Doctor.signIn(doctor);
    }
}
