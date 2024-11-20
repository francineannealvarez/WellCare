package display;

public class DisplayUtils {
    public void printHeader(String header) {
        int totalWidth = 100; 
        int padding = (totalWidth - header.length()) / 2;

        String centeredHeader = " ".repeat(Math.max(0, padding)) + header;
        System.out.println("\n" + "=".repeat(totalWidth));
        System.out.println(centeredHeader);
        System.out.println("=".repeat(totalWidth));
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
