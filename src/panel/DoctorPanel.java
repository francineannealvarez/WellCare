package panel;

import java.util.Scanner;
import java.sql.Connection;

import model.Patient;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import display.AppointmentTableDisplay;
import display.DisplayUtils;
import display.DoctorScheduleTableDisplay;
import display.TableDisplay;
import model.Appointment;
import model.AvailableTime;
import model.MedicalHistory;
import model.Doctor;
import admin.Admin;
import dao.AdminDao;
import dao.AdminDaoJdbc;
import dao.DoctorDao;
import dao.DoctorDaoJdbc;
import dao.PatientDao;
import dao.PatientDaoJdbc;

public class DoctorPanel {  
        private DisplayUtils display;
        private PatientDao patientDao;
        private AdminDao adminDao;
        private DoctorDao doctorDao;
        private Doctor loggedInDoctor; 

    public DoctorPanel(Doctor doctor, Connection connection) {
        this.loggedInDoctor = doctor;
        this.display = new DisplayUtils();
        this.patientDao = new PatientDaoJdbc(connection);
        this.adminDao = new AdminDaoJdbc(connection);
        this.doctorDao = new DoctorDaoJdbc(connection);
    }
   
    public void signIn(Scanner scanner, Admin admin) {
        display.printHeader("LOG IN");
        System.out.print("Enter Doctor's ID: ");
        String doctorId = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            boolean isLoggedIn = doctorDao.login(doctorId, password); 

            if (isLoggedIn) {
                loggedInDoctor = doctorDao.getDoctorById(doctorId); 
                
                if (loggedInDoctor != null) {
                    showOptions(scanner); 
                } else {
                    System.out.println("Doctor not found. Please try again.");
                }
            } else {
                System.out.println("Invalid password or Doctor ID. Access denied.");
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }

    public void showOptions(Scanner scanner) throws SQLException {
        boolean exit = false;

        while (!exit) {
            display.printHeader("DOCTOR'S MENU");
            System.out.println("Select an option:");
            System.out.println("1. Add available time");
            System.out.println("2. Delete available time");
            System.out.println("3. View schedule");
            System.out.println("4. View Appointments");
            System.out.println("5. Add Diagnosis");
            System.out.println("6. Cancel Appointment");
            System.out.println("7. View Patient Information");
            System.out.println("8. Exit");
            System.out.print("Please select an option by entering the corresponding number: " );

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); 
            
                switch (choice) {
                    case 1 -> addAvailableTime(scanner);
                    case 2 -> deleteAvailableTime(scanner);
                    case 3 -> viewSchedule();
                    case 4 -> viewDoctorAppointments();
                    case 5 -> addDiagnosisToPatient(scanner);
                    case 6 -> cancelAppointment(scanner);
                    case 7 -> viewPatientInfo(scanner);
                    case 8 -> {
                        System.out.println("Exiting...");
                        exit = true;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }  catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); 
                }
            }
        }

    public void addAvailableTime(Scanner scanner) {
        display.printHeader("UPDATE YOUR SCHEDULE: ADD TIME SLOT");
        System.out.print("Enter date & time to add (e.g., '2023-11-01 10:00 AM'): ");
        String availableTime = scanner.nextLine();
            
        try {
            if (doctorDao.addAvailableTime(loggedInDoctor.getDoctorId(), availableTime)) {
                System.out.println("Time slot added successfully: " + availableTime);
            } else {
                System.out.println("Failed to add the time slot.");
            }
        } catch (IllegalArgumentException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
        
    public void deleteAvailableTime(Scanner scanner) throws SQLException {
        display.printHeader("UPDATE YOUR SCHEDULE: REMOVE TIME SLOT");

        // Fetch the list of available times from the database
        List<AvailableTime> availableTimes = doctorDao.getAvailableTimes(loggedInDoctor.getDoctorId());

        if (availableTimes.isEmpty()) {
            System.out.println("No available time slots to remove.");
            return;
        }

        System.out.println("Available appointment slots:");
        for (int i = 0; i < availableTimes.size(); i++) {
            System.out.println((i + 1) + ". " + availableTimes.get(i).getAvailableTime());
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
            AvailableTime selectedTime = availableTimes.get(index - 1);

            int appointmentId = selectedTime.getAvailableTimeId();
            try {
                if (doctorDao.deleteAvailableTime(appointmentId)) {
                    System.out.println("Slot deleted successfully: " + selectedTime.getAvailableTime());
                } else {
                    System.out.println("Failed to delete the slot.");
                }
            } catch (SQLException e) {
                System.out.println("Error deleting slot: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }

    public void viewSchedule() {
        display.printHeader("AVAILABLE APPOINTMENT SLOTS");
        System.out.println("Available appointment slots for Dr. " + loggedInDoctor.getName() + " (" + loggedInDoctor.getDepartment() + " Department):\n");

        try {
            List<AvailableTime> availableTimes = doctorDao.getAvailableTimes(loggedInDoctor.getDoctorId()); 

            if (availableTimes.isEmpty()) {
                System.out.println("No available slots at this time.");
            } else {
                DoctorScheduleTableDisplay scheduleDisplay = new DoctorScheduleTableDisplay(availableTimes);

                scheduleDisplay.printTableHeader();
                scheduleDisplay.printAvailableSlots();
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while fetching available slots: " + e.getMessage());
        }
    }

    public void viewDoctorAppointments() {
        display.printHeader("YOUR SCHEDULED APPOINTMENTS");
        try {
            String docId = loggedInDoctor.getDoctorId();
            
            List<Appointment> appointments = doctorDao.getDoctorAppointments(docId);
    
            if (appointments.isEmpty()) {
                System.out.println("No appointments scheduled for Dr. " + loggedInDoctor.getName() + ".");
            } else {
                TableDisplay appointmentTable = new AppointmentTableDisplay(appointments);
                appointmentTable.printTableHeader();
                ((AppointmentTableDisplay) appointmentTable).printAppointments();
            }
        }catch (SQLException e) {
            System.out.println("Error fetching appointments: " + e.getMessage());
        }
    }
    
    public void addDiagnosisToPatient(Scanner scanner) {
        display.printHeader("ADD DIAGNOSIS TO PATIENT");
    
        List<Appointment> appointments;
        try {
            appointments = doctorDao.getDoctorAppointments(loggedInDoctor.getDoctorId());
        } catch (SQLException e) {
            System.out.println("Error fetching appointments: " + e.getMessage());
            return;
        }
    
        if (appointments.isEmpty()) {
            System.out.println("No appointments available for diagnosis.");
            return;
        }
    
        TableDisplay appointmentTable = new AppointmentTableDisplay(appointments);
        appointmentTable.printTableHeader();
        ((AppointmentTableDisplay) appointmentTable).printAppointments();
    
        int selectedAppointmentId = -1;
        while (true) {
            System.out.print("Enter the Appointment ID to add a diagnosis (or type 'back' to return to the doctor menu): ");
            String input = scanner.nextLine().trim();
    
            if (input.equalsIgnoreCase("back")) {
                System.out.println("Returning to the doctor's menu.");
                return; 
            }
    
            try {
                selectedAppointmentId = Integer.parseInt(input);
                boolean valid = false;
                for (Appointment appointment : appointments) {
                    if (appointment.getAppointmentId() == selectedAppointmentId && "Scheduled".equals(appointment.getStatus())) {
                        valid = true;
                        break;
                    }
                }
                if (valid) {
                    break;
                } else {
                    System.out.println("Invalid Appointment ID or the appointment is not scheduled. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid Appointment ID.");
            }
        }
    
        System.out.print("Enter the diagnosis for the selected appointment: ");
        String diagnosis = scanner.nextLine();
    
        try {
            Appointment selectedAppointment = null;
            for (Appointment appointment : appointments) {
                if (appointment.getAppointmentId() == selectedAppointmentId) {
                    selectedAppointment = appointment;
                    break;
                }
            }
    
            if (selectedAppointment == null) {
                System.out.println("Error: Appointment not found.");
                return;
            }
    
            int departmentId = adminDao.getDepartmentIdByName(selectedAppointment.getDepartment());
            String patientName = doctorDao.getPatientNameById(selectedAppointment.getPatientId());
            String doctorName = doctorDao.getDoctorNameById(selectedAppointment.getDoctorId());
    
            int medicalHistoryId = 0;
            String appointmentTime = selectedAppointment.getAppointmentTime();
    
            MedicalHistory medicalHistory = new MedicalHistory(
                medicalHistoryId,
                selectedAppointment.getPatientId(),
                selectedAppointment.getDoctorId(),
                departmentId,
                selectedAppointment.getAppointmentId(),
                selectedAppointment.getMedicalCondition(),
                diagnosis,
                patientName,
                doctorName,
                selectedAppointment.getDepartment(),
                appointmentTime
            );
    
            doctorDao.updateAppointmentStatusToCompleted(selectedAppointmentId);
            doctorDao.addToMedicalHistory(medicalHistory);
            System.out.println("Appointment moved to the patient's medical history.");
    
        } catch (SQLException e) {
            System.out.println("Error processing the diagnosis: " + e.getMessage());
        }
    }

    public void cancelAppointment(Scanner scanner) {
        display.printHeader("CANCEL APPOINTMENT");

        List<Appointment> appointments;
        try {
            appointments = doctorDao.getDoctorAppointments(loggedInDoctor.getDoctorId()); 
        } catch (SQLException e) {
            System.out.println("Error fetching appointments: " + e.getMessage());
            return;
        }

        if (appointments.isEmpty()) {
            System.out.println("No appointments available for cancellation.");
            return;
        }

        TableDisplay appointmentTable = new AppointmentTableDisplay(appointments);
        appointmentTable.printTableHeader();
        ((AppointmentTableDisplay) appointmentTable).printAppointments();

        int selectedAppointmentId = -1;
        while (true) {
            System.out.print("Enter the Appointment ID to cancel: ");
            if (scanner.hasNextInt()) {
                selectedAppointmentId = scanner.nextInt();
                scanner.nextLine();
                boolean valid = false;
                for (Appointment appointment : appointments) {
                    if (appointment.getAppointmentId() == selectedAppointmentId && "Scheduled".equals(appointment.getStatus())) {
                        valid = true;
                        break;
                    }
                }
                if (valid) {
                    break;
                } else {
                    System.out.println("Invalid Appointment ID. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid Appointment ID.");
                scanner.nextLine(); 
            }
        }

        System.out.print("Enter the reason for cancellation: ");
        String cancelReason = scanner.nextLine();

        try {
            doctorDao.cancelAppointment(loggedInDoctor.getDoctorId(), selectedAppointmentId, cancelReason); 
            System.out.println("Appointment with ID " + selectedAppointmentId + " has been successfully canceled.");
        } catch (SQLException e) {
            System.out.println("Error canceling the appointment: " + e.getMessage());
        }
    }

    public void viewPatientInfo(Scanner scanner) {
        display.printHeader("VIEW PATIENT INFORMATION");

        System.out.print("Enter Patient's ID to View Information: ");
        String patientId = scanner.nextLine();

        Patient foundPatient = patientDao.getPatientDetails(patientId);

        if (foundPatient != null) {
            System.out.println("\nPatient Details:");
            System.out.println("---------------------------------------------------");
            System.out.println("ID: " + foundPatient.getUniqueId());
            System.out.println("Name: " + foundPatient.getName());
            System.out.println("Birthday: " + foundPatient.getBday());
            System.out.println("Place of Birth: " + foundPatient.getPlaceOfBirth());
            System.out.println("Gender: " + foundPatient.getGender());
            System.out.println("Email: " + foundPatient.getEmail());
            System.out.println("Contact Number: " + foundPatient.getContactNo());
            System.out.println("Emergency Contact: " + foundPatient.getEmergencyNo());
            System.out.println("Address: " + foundPatient.getAddress());
            System.out.println("Province: " + foundPatient.getProvince());
            System.out.println("City: " + foundPatient.getCity());
            System.out.println("Barangay: " + foundPatient.getBarangay());
            System.out.println("Nationality: " + foundPatient.getNationality());
            System.out.println("Marital Status: " + foundPatient.getMaritalStatus());
            System.out.println("Allergies: " + foundPatient.getAllergy());
            System.out.println("Past Medical History: " + foundPatient.getPast());
            System.out.println("Blood Type: " + foundPatient.getBloodtype());
            System.out.println("Vaccinated: " + (foundPatient.isVaccinated() ? "Yes" : "No"));
            System.out.println("Has Disability: " + (foundPatient.isHasDisability() ? "Yes" : "No"));
            if (foundPatient.isHasDisability()) {
                System.out.println("Disability Details: " + foundPatient.getDisabilityDetails());
            }
            System.out.println("---------------------------------------------------");
        } else {
            display.printMessage("Patient not found! Please check the ID and try again.");
        }
    }

}
