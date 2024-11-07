import java.util.ArrayList;
import java.util.List;

public class PatientDatabase {
    private List<Patient> patients;

    public PatientDatabase() {
        patients = new ArrayList<>();
    }

    // Add a new patient to the database
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public Patient findPatient(String name, String password) {
        for (Patient patient : patients) {
            if (patient.getName().equals(name) && 
                patient.getPassword().equals(password)) {
                return patient;
            }
        }
        return null;
    }
}

