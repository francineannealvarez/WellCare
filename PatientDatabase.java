import java.util.ArrayList;
import java.util.List;

public class PatientDatabase {
    private List<Patient> patients;

    public PatientDatabase() {
        patients = new ArrayList<>();
    }

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

    public Patient findByName(String name) {
        for (Patient patient : patients) {
            if (patient.getName().equalsIgnoreCase(name)) {
                return patient;
            }
        }
        return null;// will return null if no match is found
    }
}

