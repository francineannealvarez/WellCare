package model;

public class MedicalHistory {
    private int medicalID;
    private int appointmentId;
    private String patientId;
    private String doctorId;
    private String diagnosis;
    private int departmentId;
    private String medicalCondition;  
    private String patientName;
    private String doctorName;
    private String departmentName;
    private String appointmentTime;

    // Constructor 
    public MedicalHistory(int medicalID, String patientId, String doctorId, int departmentId, int appointmentId, 
                          String medicalCondition, String diagnosis, String patientName, String doctorName, String departmentName, String appointmentTime) {
        this.medicalID = medicalID;
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.diagnosis = diagnosis;
        this.doctorId = doctorId;
        this.medicalCondition = medicalCondition;
        this.departmentId = departmentId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.departmentName = departmentName;
        this.appointmentTime = appointmentTime;
    }

    // Getters
    public int getMedicalID() { return medicalID; }
    public int getAppointmentId() { return appointmentId; }
    public String getPatientId() { return patientId; }
    public String getDoctorId() { return doctorId; }
    public String getDiagnosis() { return diagnosis; }
    public int getDepartmentId() { return departmentId; }
    public String getMedicalCondition() { return medicalCondition; }
    public String getPatientName() { return patientName; }
    public String getDoctorName() { return doctorName; }
    public String getDepartmentName() { return departmentName; }
    public String getAppointmentTime() { return appointmentTime; }

    // Setters
    public void setMedicalID(int medicalID) { this.medicalID = medicalID; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }
    public void setMedicalCondition(String medicalCondition) { this.medicalCondition = medicalCondition; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    public void setAppointmentTime(String appointmentTime) { this.appointmentTime = appointmentTime; }
}
