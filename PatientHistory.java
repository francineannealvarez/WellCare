

public class PatientHistory {
    private String patientName;
    private String visitDate; 
    private String department;
    private String doctorName;
    private String medicalCondition;
    private String diagnosis;
    private boolean isCancelled;

    public PatientHistory(String patientName, String visitDate, String department, String doctorName, String medicalCondition, String diagnosis) {
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
        if (diagnosis == null || diagnosis.isEmpty()) {
            diagnosis = "Pending";  // Set diagnosis to "Pending" if not provided
        }
        if (medicalCondition == null || medicalCondition.trim().isEmpty()) {
            throw new IllegalArgumentException("Doctor name cannot be null or empty");
        }
        this.patientName = patientName;
        this.visitDate = visitDate; 
        this.department = department;
        this.doctorName = doctorName;
        this.medicalCondition = medicalCondition;
        this.diagnosis = diagnosis;
        this.isCancelled = false; 
    }

    public String getPatientName() {
        return patientName;
    }

    public String getVisitDate() {
        return visitDate;
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

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public void setDiagnosis(String diagnosis) {
        if (diagnosis != null && !diagnosis.isEmpty()) {
            this.diagnosis = diagnosis;
        } else {
            System.out.println("Diagnosis cannot be empty.");
        }
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
    

  

    // Override toString method for easy display of the patient's history record
    @Override
    public String toString() {
        return "\nPatient History Record:\n" +
           "Patient Name: " + patientName + "\n" +
           "Visit Date: " + visitDate + "\n" +
           "Department: " + department + "\n" +
           "Doctor: " + doctorName + "\n" +
           "Medical Condition: " + medicalCondition + "\n" +
           "Diagnosis: " + diagnosis + "\n" +
           "Status: " + (isCancelled ? "Cancelled" : "Scheduled");
    }
    
    //Used in booking appointment to avoid duplication
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PatientHistory)) return false;
        PatientHistory other = (PatientHistory) obj;
        return patientName.equals(other.patientName) &&
               visitDate.equals(other.visitDate) &&
               department.equals(other.department) &&
               doctorName.equals(other.doctorName) &&
               medicalCondition.equals(other.medicalCondition) &&
               diagnosis.equals(other.diagnosis);
    }
}
