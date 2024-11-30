package connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Appointment;

public class AppointmentDaoJdbc implements AppointmentDAO {
    private final Connection conn;

    public AppointmentDaoJdbc(Connection conn) {
        try {
            this.conn = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database.", e);
        }
    }

    public void createAppointment(Appointment appointment, AdminDao adminDao) {
        int departmentId = adminDao.getDepartmentIdByName(appointment.getDepartment());

        if (departmentId == -1) {
            System.err.println("Invalid department name.");
            return; 
        }

        String sql = "INSERT INTO appointments (Doctor_ID, Patient_ID, Department_ID, Appointment_Time, Medical_Condition, Diagnosis, Status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, appointment.getDoctorId());
            stmt.setString(2, appointment.getPatientId());
            stmt.setInt(3, departmentId);
            stmt.setString(4, appointment.getAppointmentTime());
            stmt.setString(5, appointment.getMedicalCondition());
            stmt.setString(6, appointment.getDiagnosis());
            stmt.setString(7, appointment.getStatus());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    appointment.setDiagnosis(String.valueOf(generatedKeys.getInt(1)));
                }
            }
            System.out.println("Appointment scheduled successfully!");
        } catch (SQLException e) {
            System.err.println("Error confirming appointment: " + e.getMessage());
        }
    }

    public List<Appointment> getAppointmentsByPatientId(String patientId) {
        List<Appointment> appointments = new ArrayList<>();

        String sql = "SELECT a.Appointment_ID, a.Doctor_ID, a.Patient_ID, a.Department_ID, a.Appointment_Time, " +
                     "a.Medical_Condition, a.Diagnosis, a.Status, d.Department_Name, p.Patient_Name, doc.Doctor_Name " +
                     "FROM appointments a " +
                     "JOIN department d ON a.Department_ID = d.Department_ID " +
                     "JOIN patient p ON a.Patient_ID = p.Patient_ID " +
                     "JOIN doctor doc ON a.Doctor_ID = doc.Doctor_ID " +
                     "WHERE a.Patient_ID = ? AND a.Status = 'Scheduled'"; 
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patientId);  
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Appointment appointment = mapResultSetToAppointment(rs);
                    
                    // Set additional fields for names
                    appointment.setPatientName(rs.getString("Patient_Name"));
                    appointment.setDoctorName(rs.getString("Doctor_Name"));
                    appointment.setDepartment(rs.getString("Department_Name"));
                    appointments.add(appointment);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching appointments by patient ID: " + e.getMessage());
        }
        return appointments;
    }

    public void removeBookedTime(int timeId) {
        String sql = "DELETE FROM available_times WHERE AvailableTimeSlot_ID = ?";
    
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, timeId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                //System.out.println("The booked time has been removed from available times."); (for debugging)
            } else {
                System.out.println("No matching time found to remove.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Appointment mapResultSetToAppointment(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment(0, null, null, null, null, null, null, null, null, null);
        appointment.setAppointmentId(rs.getInt("Appointment_ID"));
        appointment.setDoctorId(rs.getString("Doctor_ID"));
        appointment.setDoctorName(rs.getString("Doctor_Name"));
        appointment.setPatientId(rs.getString("Patient_ID"));
        appointment.setPatientName(rs.getString("Patient_Name"));
        appointment.setDepartment(rs.getString("Department_Name"));
        appointment.setAppointmentTime(rs.getString("Appointment_Time"));
        appointment.setMedicalCondition(rs.getString("Medical_Condition"));
        appointment.setStatus(rs.getString("Status"));
        appointment.setDiagnosis(rs.getString("Diagnosis"));
        return appointment;
    }
    
}
