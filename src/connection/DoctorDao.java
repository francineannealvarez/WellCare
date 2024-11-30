package connection;

import java.sql.SQLException;
import java.util.List;
import model.Doctor;
import model.Appointment;
import model.AvailableTime;
import model.MedicalHistory;

public interface DoctorDao {
    boolean login(String doctorId, String password) throws SQLException;
    Doctor getDoctorById(String doctorId) throws SQLException;
    boolean addAvailableTime(String doctorId, String availableTime) throws SQLException;
    boolean deleteAvailableTime(int appointmentId) throws SQLException;
    List<AvailableTime> getAvailableTimes(String doctorId) throws SQLException;
    List<Appointment> getDoctorAppointments(String doctorId) throws SQLException;
    void updateDiagnosis(int appointmentId, String diagnosis) throws SQLException;
    void addToMedicalHistory(MedicalHistory medicalHistory) throws SQLException;
    void updateAppointmentStatusToCompleted(int appointmentId) throws SQLException;
    boolean cancelAppointment(String doctorId, int appointmentId, String cancelReason) throws SQLException;
    List<Doctor> getDoctorsByDepartment(int departmentId);
    String getDoctorNameById(String doctorId) throws SQLException;
    String getPatientNameById(String patientId) throws SQLException;
}
