package model;

public class Appointment {
    private int appointmentId;
    private String doctorId;
    private String patientId;
    private String patientName;  
    private String department;  
    private String appointmentTime;
    private String medicalCondition;
    private String status;
    private String doctorName;  
    private String diagnosis;

    //Constructor
    public Appointment(int appointmentId, String doctorId, String doctorName, String patientId, String patientName,  
                       String department, String appointmentTime, String medicalCondition, 
                       String status, String diagnosis) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.department = department;
        this.appointmentTime = appointmentTime;
        this.medicalCondition = medicalCondition;
        this.status = status;
        this.diagnosis = diagnosis;

    }

    // Getters
    public int getAppointmentId() { return appointmentId; }
    public String getDoctorId() { return doctorId; }
    public String getPatientId() { return patientId; }
    public String getPatientName() { return patientName; }
    public String getAppointmentTime() { return appointmentTime; }
    public String getMedicalCondition() { return medicalCondition; }
    public String getStatus() { return status; }
    public String getDepartment() { return department; }
    public String getDoctorName() { return doctorName; } 
    public String getDiagnosis() { return diagnosis; }

    //Setters
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public void setDepartment(String department) { this.department = department; }
    public void setAppointmentTime(String appointmentTime) { this.appointmentTime = appointmentTime; }
    public void setMedicalCondition(String medicalCondition) { this.medicalCondition = medicalCondition; }
    public void setStatus(String status) { this.status = status; }

}
