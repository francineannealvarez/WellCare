package display;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Appointment;
import java.util.Collections;
import java.util.Comparator;


public class AppointmentTableDisplay implements TableDisplay {
    private List<Appointment> appointments;

    // Constructor
    public AppointmentTableDisplay(List<Appointment> appointments) {
        this.appointments = appointments != null ? appointments : new ArrayList<>();
    }

    @Override
    public void printTableHeader() {
        System.out.println("\nAppointments available for cancellation:");
        System.out.format("%-5s %-15s %-35s %-40s %-25s %-25s %-15s\n", 
            "No.", "Appointment ID", "Patient Name", "Medical Condition", 
            "Department", "Appointment Date", "Status");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public void printRow(Object... values) {
        System.out.format("%-5s %-15s %-35s %-40s %-25s %-25s %-15s\n", 
            values[0], values[1], values[2], values[3], values[4], values[5], values[6]);
    }

    public void printAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments available.");
            return;
        }

        Collections.sort(appointments, new Comparator<Appointment>() {
            @Override
            public int compare(Appointment a1, Appointment a2) {
                return a1.getAppointmentTime().compareTo(a2.getAppointmentTime());
            }
        });

        int count = 1; 
        for (Appointment appointment : appointments) {
            if ("Scheduled".equals(appointment.getStatus())) {
                String formattedDate = formatDate(appointment.getAppointmentTime());

                printRow(
                    count++, 
                    appointment.getAppointmentId(), 
                    appointment.getPatientName(),
                    appointment.getMedicalCondition(),
                    appointment.getDepartment(),
                    formattedDate, 
                    appointment.getStatus() 
                );
            }
        }
    }

    private String formatDate(String appointmentTime) {
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            java.util.Date date = originalFormat.parse(appointmentTime);

            SimpleDateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
            return targetFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return appointmentTime;
        }
    }
}
