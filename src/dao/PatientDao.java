package dao;

import java.sql.SQLException;
import java.util.List;
import model.CanceledAppointment;
import model.MedicalHistory;
import model.Patient; 

public interface PatientDao {
        void addPatient(Patient patient) throws SQLException;
        Patient login(String username, String password);
        void updatePatient(Patient patient);
        Patient getPatientDetails(String patientId);
        public List<MedicalHistory> getMedicalHistory(String patientId) throws SQLException;
        String generateUniqueId() throws SQLException;
        public List<CanceledAppointment> getCanceledAppointments(String patientId) throws SQLException;
}
