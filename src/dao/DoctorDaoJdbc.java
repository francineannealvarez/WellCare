package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Doctor;
import model.Appointment;
import model.AvailableTime;
import model.MedicalHistory;

public class DoctorDaoJdbc implements DoctorDao {
    private Connection connection;
    private static final String DOCTOR_PASSWORD = "WellCare";

    public DoctorDaoJdbc( Connection connection) {
        this.connection = connection;
    }

    public boolean login(String doctorId, String password) throws SQLException {
        if (!password.equals(DOCTOR_PASSWORD)) {
            return false; 
        }
    
        String query = "SELECT * FROM doctor WHERE Doctor_ID = ? AND employment_status = 'active'";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true; 
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
            //e.printStackTrace();
        }
        return false; 
    }
    
    
    @Override
    public Doctor getDoctorById(String doctorId) throws SQLException {
        String query = "SELECT d.Doctor_ID, d.Doctor_Name, dept.Department_Name " +
                       "FROM doctor d " +
                       "JOIN department dept ON d.Department_ID = dept.Department_ID " +
                       "WHERE d.Doctor_ID = ? AND d.employment_status = 'active'";
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Doctor(
                        rs.getString("Doctor_ID"),
                        rs.getString("Doctor_Name"),
                        rs.getString("Department_Name")
                    );
                }
            }
        }
        return null; 
    }
    
    @Override
    public boolean addAvailableTime(String doctorId, String availableTime) throws SQLException {
        String query = "INSERT INTO available_times (Doctor_ID, Available_Time, Is_Available) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, doctorId);
            ps.setString(2, availableTime);
            ps.setBoolean(3, true);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public boolean deleteAvailableTime(int appointmentId) throws SQLException {
        String query = "DELETE FROM available_times WHERE AvailableTimeSlot_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, appointmentId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public List<AvailableTime> getAvailableTimes(String doctorId) throws SQLException {
        List<AvailableTime> availableTimes = new ArrayList<>();
        String query = "SELECT * FROM available_times WHERE Doctor_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    availableTimes.add(new AvailableTime(
                            rs.getInt("AvailableTimeSlot_ID"),
                            rs.getInt("Doctor_ID"),
                            rs.getString("Available_Time"),
                            rs.getBoolean("Is_Available")
                    ));
                }
            }
        }
        return availableTimes;
    }
    
    @Override
    public List<Appointment> getDoctorAppointments(String doctorId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String query = """
            SELECT a.Appointment_ID, a.Doctor_ID, a.Patient_ID, p.Patient_Name, 
                   a.Appointment_Time, a.Medical_Condition, a.Status, a.Diagnosis, 
                   dep.Department_Name, d.Doctor_Name 
            FROM appointments a
            JOIN patient p ON a.Patient_ID = p.Patient_ID
            JOIN doctor d ON a.Doctor_ID = d.Doctor_ID
            JOIN department dep ON d.Department_ID = dep.Department_ID  
            WHERE a.Doctor_ID = ? AND Status = 'Scheduled';
        """;
    
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    appointments.add(new Appointment(
                            rs.getInt("Appointment_ID"),        
                            rs.getString("Doctor_ID"),    
                            rs.getString("Doctor_Name"),         
                            rs.getString("Patient_ID"),           
                            rs.getString("Patient_Name"),       
                            rs.getString("Department_Name"),         
                            rs.getString("Appointment_Time"),  
                            rs.getString("Medical_Condition"),  
                            rs.getString("Status"),             
                            rs.getString("Diagnosis")      
                    ));
                }
            }
        }
        return appointments;
    }

    public void updateDiagnosis(int appointmentId, String diagnosis) throws SQLException {
        String query = "UPDATE appointments SET diagnosis = ? WHERE Appointment_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, diagnosis);
            ps.setInt(2, appointmentId);
            ps.executeUpdate();
        }
    }
    
    public void addToMedicalHistory(MedicalHistory medicalHistory) throws SQLException {
        String query = "INSERT INTO medical_history (Patient_ID, Doctor_ID, Department_ID, Appointment_ID, Medical_Condition, Diagnosis) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, medicalHistory.getPatientId());
            ps.setString(2, medicalHistory.getDoctorId());
            ps.setInt(3, medicalHistory.getDepartmentId());
            ps.setInt(4, medicalHistory.getAppointmentId());
            ps.setString(5, medicalHistory.getMedicalCondition());
            ps.setString(6, medicalHistory.getDiagnosis());
            ps.executeUpdate();
        }
    }
    
    public void updateAppointmentStatusToCompleted(int appointmentId) throws SQLException {
        String query = "UPDATE appointments SET Status = 'Completed' WHERE Appointment_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, appointmentId);
            ps.executeUpdate();
        }
    }

    @Override
    public boolean cancelAppointment(String doctorId, int appointmentId, String cancelReason) throws SQLException {
        String validationQuery = """
            SELECT Appointment_ID 
            FROM appointments 
            WHERE Appointment_ID = ? AND Doctor_ID = ?
        """;
    
        String insertCancelQuery = """
            INSERT INTO canceled_appointments 
            (Appointment_ID, Doctor_ID, Patient_ID, Department_ID, Cancel_Reason)
            SELECT 
                Appointment_ID, Doctor_ID, Patient_ID, Department_ID, ?
            FROM appointments 
            WHERE Appointment_ID = ?
        """;
    
        String updateStatusQuery = """
            UPDATE appointments
            SET Status = 'Canceled'
            WHERE Appointment_ID = ? AND Doctor_ID = ?
        """;
    
        try (PreparedStatement validateStmt = connection.prepareStatement(validationQuery)) {
            validateStmt.setInt(1, appointmentId);
            validateStmt.setString(2, doctorId);
            
            try (ResultSet rs = validateStmt.executeQuery()) {
                // If no record is found, the appointment does not belong to the doctor
                if (!rs.next()) {
                    System.out.println("Error: Appointment not found or does not belong to this doctor.");
                    return false;
                }
            }
        }
    
        try (PreparedStatement insertStmt = connection.prepareStatement(insertCancelQuery)) {
            insertStmt.setString(1, cancelReason);
            insertStmt.setInt(2, appointmentId);
            int rowsInserted = insertStmt.executeUpdate();
    
            if (rowsInserted == 0) {
                System.out.println("Error: Failed to move the appointment to the canceled appointments table.");
                return false;
            }
        }
    
        try (PreparedStatement updateStmt = connection.prepareStatement(updateStatusQuery)) {
            updateStmt.setInt(1, appointmentId);
            updateStmt.setString(2, doctorId);
            int rowsUpdated = updateStmt.executeUpdate();
    
            if (rowsUpdated > 0) {
                //System.out.println("Appointment marked as canceled."); for debugging
                return true;
            } else {
                System.out.println("Error: Failed to update the appointment status.");
                return false;
            }
        }
    }
    
    public List<Doctor> getDoctorsByDepartment(int departmentId) {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT d.Doctor_ID, d.Doctor_Name, dep.Department_Name " +
                     "FROM doctor d " +
                     "JOIN department dep ON d.Department_ID = dep.Department_ID " +
                     "WHERE dep.Department_ID = ? AND d.employment_status = 'active'";; 
         
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, departmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Doctor doctor = new Doctor(
                        rs.getString("Doctor_ID"),
                        rs.getString("Doctor_Name"),
                        rs.getString("Department_Name")
                    );
                    doctors.add(doctor); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }
    
    @Override
    public String getPatientNameById(String patientId) throws SQLException {
        String query = "SELECT Patient_Name FROM patient WHERE Patient_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Patient_Name");
                }
            }
        }
        return null;
    }

    public String getDoctorNameById(String doctorId) throws SQLException {
        String query = "SELECT Doctor_Name FROM doctor WHERE Doctor_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, doctorId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Doctor_Name");
                }
            }
        }
        return null; 
    }

}