public class AppointmentDeets {
    private String patientName;
    private String selectedTime;
    private String doctorName;

    public AppointmentDeets(String patientName, String selectedTime, String doctorName) {
        this.patientName = patientName;
        this.selectedTime = selectedTime;
        this.doctorName = doctorName;
    }

    public String appointmentDeets() {
        return "Patient Name: " + patientName + ", Selected Time: " + selectedTime + ", Doctor Name: " + doctorName;
    }
}
