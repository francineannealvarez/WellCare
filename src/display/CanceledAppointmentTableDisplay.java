package display;

import model.CanceledAppointment;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CanceledAppointmentTableDisplay implements TableDisplay {
    private List<CanceledAppointment> canceledAppointments;

    // Constructor
    public CanceledAppointmentTableDisplay(List<CanceledAppointment> canceledAppointments) {
        this.canceledAppointments = canceledAppointments != null ? canceledAppointments : Collections.emptyList();
    }

    @Override
    public void printTableHeader() {
        System.out.println("\nCanceled Appointments:");
        System.out.printf("%-5s %-15s %-25s %-30s %-30s %-20s %-30s\n", 
                          "No.", "Appointment ID", "Time", "Patient", "Doctor", "Department", "Cancel Reason");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public void printRow(Object... values) {
        System.out.printf("%-5s %-15s %-25s %-30s %-30s %-20s %-30s\n", 
                          values[0], values[1], values[2], values[3], values[4], values[5], values[6]);
    }

    public void printCanceledAppointments() {
        if (canceledAppointments.isEmpty()) {
            System.out.println("No canceled appointments found.");
            return;
        }

        Collections.sort(canceledAppointments, new Comparator<CanceledAppointment>() {
            @Override
            public int compare(CanceledAppointment c1, CanceledAppointment c2) {
                return c1.getAppointmentTime().compareTo(c2.getAppointmentTime());
            }
        });

        int count = 1; 
        for (CanceledAppointment canceledAppointment : canceledAppointments) {
            String formattedTime = formatDate(canceledAppointment.getAppointmentTime());

            printRow(
                count++,
                canceledAppointment.getAppointmentId(),
                formattedTime,
                canceledAppointment.getPatientName(),
                canceledAppointment.getDoctorName(),
                canceledAppointment.getDepartmentName(),
                canceledAppointment.getCancelReason()
            );
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
