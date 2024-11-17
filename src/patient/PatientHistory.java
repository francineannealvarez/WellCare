package patient;

public class PatientHistory {
    private Patient patient;
    private String patientName;
    private String visitDate; 
    private String department;
    private String doctorName;
    private String medicalCondition;
    private String diagnosis;
    private boolean isCancelled;
    private String cancellationReason;

    public PatientHistory(Patient patient, String visitDate, String department, String doctorName, String medicalCondition, String diagnosis) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null");
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
        if (medicalCondition == null || medicalCondition.trim().isEmpty()) {
            throw new IllegalArgumentException("Medical condition cannot be null or empty");
        }
    
        this.patient = patient;
        this.patientName = patient.getName();  // Set the patient's name from the Patient object
        this.visitDate = visitDate;
        this.department = department;
        this.doctorName = doctorName;
        this.medicalCondition = medicalCondition;
        this.diagnosis = (diagnosis != null && !diagnosis.isEmpty()) ? diagnosis : "Pending";
        this.isCancelled = false;
        this.cancellationReason = "";
    }
    

    public Patient getPatient() {
        return patient;
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
            this.setCancelled(true);  // Mark appointment as "Completed" or similar status
        } else {
            System.out.println("Diagnosis cannot be empty.");
        }
    }
        

    public boolean hasDiagnosis() {
        return !diagnosis.equals("Pending");
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public void addDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    
    @Override
    public String toString() {
            return String.format("%-20s %-30s\n", "Patient Name:", patientName) +
                   String.format("%-20s %-30s\n", "Visit Date:", visitDate) +
                   String.format("%-20s %-30s\n", "Department:", department) +
                   String.format("%-20s %-30s\n", "Doctor:", doctorName) +
                   String.format("%-20s %-30s\n", "Medical Condition:", medicalCondition) +
                   String.format("%-20s %-30s\n", "Diagnosis:", diagnosis);
        }
        
    


    public String toStringForAppointment(boolean includeStatus) {
        String status = includeStatus ? String.format("%-20s %-30s\n", "Status:", (hasDiagnosis() ? "Scheduled" : "Pending")) : "";
        return String.format("%-20s %-30s\n", "Patient Name:", patientName) +
           String.format("%-20s %-30s\n", "Visit Date:", visitDate) +
           String.format("%-20s %-30s\n", "Department:", department) +
           String.format("%-20s %-30s\n", "Doctor:", doctorName) +
           String.format("%-20s %-30s\n", "Medical Condition:", medicalCondition) +
           String.format("%-20s %-30s\n", "Diagnosis:", hasDiagnosis() ? diagnosis : "Pending") +
           status;
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
