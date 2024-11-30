package model;

public class CanceledAppointment {
    private int canceledAppointmentId;
    private int appointmentId;
    private String doctorName;  // Changed to store doctor name instead of doctor ID
    private String patientName; // Changed to store patient name instead of patient ID
    private String appointmentTime;
    private String cancelReason;
    private String departmentName;

    // Constructor
    public CanceledAppointment(int canceledAppointmentId, int appointmentId, String doctorName, String patientName, 
                                String appointmentTime, String departmentName, String cancelReason) {
        this.canceledAppointmentId = canceledAppointmentId;
        this.appointmentId = appointmentId;
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.appointmentTime = appointmentTime;
        this.cancelReason = cancelReason;
        this.departmentName = departmentName;
    }

    // Getters
    public int getCanceledAppointmentId() { return canceledAppointmentId; }
    public int getAppointmentId() { return appointmentId; }
    public String getDoctorName() { return doctorName; }
    public String getPatientName() { return patientName; }
    public String getAppointmentTime() { return appointmentTime; }
    public String getCancelReason() { return cancelReason; }
    public String getDepartmentName() { return departmentName; }

    // Setters
    public void setCanceledAppointmentId(int canceledAppointmentId) { this.canceledAppointmentId = canceledAppointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public void setAppointmentTime(String appointmentTime) { this.appointmentTime = appointmentTime; }
    public void setCancelReason(String cancelReason) { this.cancelReason = cancelReason; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
}
