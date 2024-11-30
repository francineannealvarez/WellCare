package model;

public class AvailableTime {
    private int availableTimeId;
    private int doctorId;
    private String availableTime;
    private boolean isAvailable;

    // Constructor
    public AvailableTime(int availableTimeId, int doctorId, String availableTime, boolean isAvailable) {
        this.availableTimeId = availableTimeId;
        this.doctorId = doctorId;
        this.availableTime = availableTime;
        this.isAvailable = isAvailable;
    }

    // Getters
    public int getAvailableTimeId() { return availableTimeId; }
    public int getDoctorId() { return doctorId; }
    public String getAvailableTime() { return availableTime; }
    public boolean isAvailable() { return isAvailable; }

    // Setters
    public void setAvailableTimeId(int availableTimeId) { this.availableTimeId = availableTimeId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public void setAvailableTime(String availableTime) { this.availableTime = availableTime; }
    public void setAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }
}
