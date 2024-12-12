package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import model.Appointment;

public interface AppointmentDAO {
    void createAppointment(Appointment appointment, AdminDao adminDao);
    List<Appointment> getAppointmentsByPatientId(String patientId);
    Appointment mapResultSetToAppointment(ResultSet rs) throws SQLException;
    void removeBookedTime(int timeId);
}
