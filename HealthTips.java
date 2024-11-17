public abstract class HealthTips {
    protected String title; // Each health tip can have a title or category name

    // Constructor
    public HealthTips(String title) {
        this.title = title;
    }

    // Abstract method to be implemented by subclasses
    public abstract void displayTips();

    // Optional: Common methods
    public String getTitle() {
        return title;
    }
}
