package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.CanceledAppointment;
import model.MedicalHistory;
import model.Patient;
import model.Patient.Gender;

public class PatientDaoJdbc implements PatientDao {
    private Connection connection;

    public PatientDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addPatient(Patient patient) throws SQLException {
        String generatedId = generateUniqueId();  
        String query = "INSERT INTO patient (Patient_ID, Patient_Name, Password, Birthdate, Place_of_Birth, Gender, Email, Contact_No, Emergency_No, Address, Province, City, Barangay, Nationality, Marital_Status, Allergy, Past, Bloodtype, Is_Vaccinated, Has_Disability, Disability_Details) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, generatedId); 
            stmt.setString(2, patient.getName());
            stmt.setString(3, patient.getPassword());
            stmt.setString(4, patient.getBday().toString());
            stmt.setString(5, patient.getPlaceOfBirth());
            stmt.setString(6, patient.getGender().toString());
            stmt.setString(7, patient.getEmail());
            stmt.setString(8, patient.getContactNo());
            stmt.setString(9, patient.getEmergencyNo());
            stmt.setString(10, patient.getAddress());
            stmt.setString(11, patient.getProvince());
            stmt.setString(12, patient.getCity());
            stmt.setString(13, patient.getBarangay());
            stmt.setString(14, patient.getNationality());
            stmt.setString(15, patient.getMaritalStatus());
            stmt.setString(16, patient.getAllergy());
            stmt.setString(17, patient.getPast());
            stmt.setString(18, patient.getBloodtype());
            stmt.setBoolean(19, patient.isVaccinated());
            stmt.setBoolean(20, patient.isHasDisability());
            stmt.setString(21, patient.getDisabilityDetails());

            stmt.executeUpdate();
            System.out.println("Sign-up successful! Your Patient ID is: " + generatedId);
        }
    }

    public String generateUniqueId() throws SQLException {
        String date = java.time.LocalDate.now().toString().replace("-", ""); // Signup date in YYYYMMDD format
    
        // Query to find the maximum numeric part of the Patient_ID across all records
        String query = "SELECT MAX(CAST(SUBSTRING(Patient_ID, 9) AS UNSIGNED)) FROM patient";
    
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                int nextNumber = 1; // Default starting number
                if (rs.next() && rs.getString(1) != null) {
                    // Increment the highest numeric part of Patient_ID found
                    nextNumber = rs.getInt(1) + 1;
                }
                // Combine the date with the globally incremented number
                return date + String.format("%04d", nextNumber);
            }
        }
    }
    

    @Override
    public Patient login(String name, String password) {
        String sql = "SELECT Patient_ID, Patient_Name, Password FROM patient WHERE Patient_Name = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name); 

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("Password");
                    String patientId = rs.getString("Patient_ID");

                    if (storedPassword.equals(password)) {
                        return new Patient(patientId, name, storedPassword);
                    } else {
                        System.out.println("Incorrect password. Please try again.");
                    }
                } else {
                    System.out.println("Name does not exist. Please try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return null; 
    }

    @Override
    public Patient getPatientDetails(String patientId) {
        try {
            String query = "SELECT * FROM patient WHERE Patient_ID = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, patientId); 
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Patient patient = new Patient(rs.getString("Patient_Name"), rs.getString("Password"));

                patient.setPatientId(rs.getString("Patient_ID"));
                patient.setBday(LocalDate.parse(rs.getString("Birthdate"))); 
                patient.setPlaceOfBirth(rs.getString("Place_of_Birth"));
                patient.setGender(Gender.valueOf(rs.getString("Gender"))); 
                patient.setEmail(rs.getString("Email"));
                patient.setContactNo(rs.getString("Contact_No"));
                patient.setEmergencyNo(rs.getString("Emergency_No"));
                patient.setAddress(rs.getString("Address"));
                patient.setProvince(rs.getString("Province"));
                patient.setCity(rs.getString("City"));
                patient.setBarangay(rs.getString("Barangay"));
                patient.setNationality(rs.getString("Nationality"));
                patient.setMaritalStatus(rs.getString("Marital_Status"));
                patient.setAllergy(rs.getString("Allergy"));
                patient.setPast(rs.getString("Past"));
                patient.setBloodtype(rs.getString("Bloodtype"));
                patient.setVaccinated(rs.getBoolean("Is_Vaccinated"));
                patient.setHasDisability(rs.getBoolean("Has_Disability"));
                patient.setDisabilityDetails(rs.getString("Disability_Details"));

                return patient;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }

    @Override
    public void updatePatient(Patient patient) {
        String query = "UPDATE patient SET Patient_Name = ?, Password = ?, Birthdate = ?, Place_of_Birth = ?, Gender = ?, Email = ?, Contact_No = ?, Emergency_No = ?, Address = ?, Province = ?, City = ?, Barangay = ?, Nationality = ?, Marital_Status = ?, Allergy = ?, Past = ?, Bloodtype = ?, Is_Vaccinated = ?, Has_Disability = ?, Disability_Details = ? WHERE Patient_ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, patient.getName());
            stmt.setString(2, patient.getPassword());
            stmt.setDate(3, Date.valueOf(patient.getBday()));
            stmt.setString(4, patient.getPlaceOfBirth());
            stmt.setString(5, patient.getGender().name());
            stmt.setString(6, patient.getEmail());
            stmt.setString(7, patient.getContactNo());
            stmt.setString(8, patient.getEmergencyNo());
            stmt.setString(9, patient.getAddress());
            stmt.setString(10, patient.getProvince());
            stmt.setString(11, patient.getCity());
            stmt.setString(12, patient.getBarangay());
            stmt.setString(13, patient.getNationality());
            stmt.setString(14, patient.getMaritalStatus());
            stmt.setString(15, patient.getAllergy());
            stmt.setString(16, patient.getPast());
            stmt.setString(17, patient.getBloodtype());
            stmt.setBoolean(18, patient.isVaccinated());
            stmt.setBoolean(19, patient.isHasDisability());
            stmt.setString(20, patient.getDisabilityDetails());
            stmt.setString(21, patient.getUniqueId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MedicalHistory> getMedicalHistory(String patientId) throws SQLException {
        List<MedicalHistory> medicalHistoryList = new ArrayList<>();
        
        String query = """
            SELECT mh.MedicalHistory_ID, mh.Appointment_ID, mh.Patient_ID, mh.Doctor_ID, p.Patient_Name, 
                   mh.Diagnosis, a.Appointment_Time, mh.Medical_Condition, mh.Department_ID,
                   d.Department_Name, dr.Doctor_Name
            FROM medical_history mh
            JOIN patient p ON mh.Patient_ID = p.Patient_ID
            JOIN doctor dr ON mh.Doctor_ID = dr.Doctor_ID
            JOIN department d ON mh.Department_ID = d.Department_ID
            JOIN appointments a ON mh.Appointment_ID = a.Appointment_ID
            WHERE mh.Patient_ID = ?
        """;
        
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, patientId); 
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MedicalHistory history = new MedicalHistory(
                            rs.getInt("MedicalHistory_ID"),
                            rs.getString("Patient_ID"),
                            rs.getString("Doctor_ID"),
                            rs.getInt("Department_ID"),
                            rs.getInt("Appointment_ID"),
                            rs.getString("Medical_Condition"),
                            rs.getString("Diagnosis"),
                            rs.getString("Patient_Name"),  
                            rs.getString("Doctor_Name"), 
                            rs.getString("Department_Name"), 
                            rs.getString("Appointment_Time")
                    );
                    medicalHistoryList.add(history);
                }
            }
        }
        return medicalHistoryList;
    }
    
    @Override
    public List<CanceledAppointment> getCanceledAppointments(String patientId) throws SQLException {
        List<CanceledAppointment> canceledAppointments = new ArrayList<>();
        
        String query = """
            SELECT ca.CanceledAppointment_ID, ca.Appointment_ID, d.Doctor_Name, p.Patient_Name, 
                   a.Appointment_Time, dep.Department_Name, ca.Cancel_Reason
            FROM canceled_appointments ca
            JOIN doctor d ON ca.Doctor_ID = d.Doctor_ID
            JOIN patient p ON ca.Patient_ID = p.Patient_ID
            JOIN department dep ON ca.Department_ID = dep.Department_ID
            JOIN appointments a ON ca.Appointment_ID = a.Appointment_ID  
            WHERE ca.Patient_ID = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, patientId); 
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    canceledAppointments.add(new CanceledAppointment(
                            rs.getInt("CanceledAppointment_ID"),
                            rs.getInt("Appointment_ID"),
                            rs.getString("Doctor_Name"),
                            rs.getString("Patient_Name"),
                            rs.getString("Appointment_Time"),
                            rs.getString("Department_Name"),
                            rs.getString("Cancel_Reason")
                    ));
                }
            }
        }
        return canceledAppointments;
    }

}
