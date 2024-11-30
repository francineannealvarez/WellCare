package display;

import java.text.SimpleDateFormat;
import java.util.List;
import model.MedicalHistory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class MedicalHistoryTableDisplay implements TableDisplay {
    private List<MedicalHistory> medicalHistoryList;

    // Constructor
    public MedicalHistoryTableDisplay(List<MedicalHistory> medicalHistoryList) {
        this.medicalHistoryList = medicalHistoryList != null ? medicalHistoryList : new ArrayList<>();
    }

    @Override
    public void printTableHeader() {
        System.out.println("\nYour Medical History:");
        System.out.format("%-5s %-20s %-40s %-25s %-30s %-30s %-20s\n", 
            "No.", "Medical History ID", "Diagnosis", "Appointment Time", 
            "Described Condition", "Doctor", "Department");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    @Override
    public void printRow(Object... values) {
        System.out.format("%-5s %-20s %-40s %-25s %-30s %-30s %-20s\n", 
            values[0], values[1], values[2], values[3], values[4], values[5], values[6]);
    }

    public void printMedicalHistory() {
        Collections.sort(medicalHistoryList, new Comparator<MedicalHistory>() {
            @Override
            public int compare(MedicalHistory m1, MedicalHistory m2) {
                return m2.getAppointmentTime().compareTo(m1.getAppointmentTime());
            }
        });

        int count = 1;
        for (MedicalHistory history : medicalHistoryList) {
            String formattedDate = formatDate(history.getAppointmentTime());
            printRow(
                count++,
                String.valueOf(history.getMedicalID()), 
                history.getDiagnosis(),
                formattedDate, 
                history.getMedicalCondition(),
                history.getDoctorName(),
                history.getDepartmentName()
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
