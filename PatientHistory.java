

public class PatientHistory {
    private String patientName;
    private String visitDate; 
    private String department;
    private String doctorName;
    private String diagnosis;

    public PatientHistory(String patientName, String visitDate, String department, String doctorName, String diagnosis) {
        if (patientName == null || patientName.trim().isEmpty()) {
            throw new IllegalArgumentException("Patient name cannot be null or empty");
        }
        if (visitDate == null) {
            throw new IllegalArgumentException("Visit date cannot be null");
        }
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be null or empty");
        }
        if (doctorName == null || doctorName.trim().isEmpty()) {
            throw new IllegalArgumentException("Doctor name cannot be null or empty");
        }
        if (diagnosis == null || diagnosis.trim().isEmpty()) {
            throw new IllegalArgumentException("Diagnosis cannot be null or empty");
        }

        this.patientName = patientName;
        this.visitDate = visitDate; 
        this.department = department;
        this.doctorName = doctorName;
        this.diagnosis = diagnosis;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getVisitDate() {
        return visitDate; // Return the date as LocalDate
    }

    public String getDepartment() {
        return department;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    // Override toString method for easy display of the patient's history record
    @Override
    public String toString() {
        return "Patient History Record: " +
                "Patient Name: " + patientName +
                ", Visit Date: " + visitDate +
                ", Department: " + department +
                ", Doctor: " + doctorName +
                ", Diagnosis: " + diagnosis;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PatientHistory)) return false;
        PatientHistory other = (PatientHistory) obj;
        return patientName.equals(other.patientName) &&
               visitDate.equals(other.visitDate) &&
               department.equals(other.department) &&
               doctorName.equals(other.doctorName) &&
               diagnosis.equals(other.diagnosis);
    }

    /*@Override
    public int hashCode() {
        return Objects.hash(patientName, visitDate, department, doctorName, diagnosis);
    }
        */
}
