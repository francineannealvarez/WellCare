package display;

import model.AvailableTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DoctorScheduleTableDisplay implements TableDisplay {
    private List<AvailableTime> availableTimes;

    // Constructor
    public DoctorScheduleTableDisplay(List<AvailableTime> availableTimes) {
        this.availableTimes = availableTimes != null ? availableTimes : Collections.emptyList();
    }

    @Override
    public void printTableHeader() {
        System.out.println("\nAvailable Appointment Slots:");
        System.out.printf("%-10s %-20s\n", "Slot No.", "Appointment Time");
        System.out.println("------------------------------");
    }

    @Override
    public void printRow(Object... values) {
        System.out.printf("%-10s %-20s\n", values[0], values[1]);
    }

    public void printAvailableSlots() {
        if (availableTimes.isEmpty()) {
            System.out.println("No available slots at this time.");
            return;
        }

        Collections.sort(availableTimes, new Comparator<AvailableTime>() {
            @Override
            public int compare(AvailableTime t1, AvailableTime t2) {
                return t1.getAvailableTime().compareTo(t2.getAvailableTime());
            }
        });
        int slotNumber = 1; 
        for (AvailableTime time : availableTimes) {
            if (time.isAvailable()) { 
                printRow(slotNumber++, time.getAvailableTime());
            }
        }
    }
}
