public class HighBloodTips extends HealthTips {

    public HighBloodTips() {
        super("Healthy Foods for High Blood");
    }

    @Override
    public void displayTips() {
        System.out.println("Tips for High Blood:");
        System.out.println("- Reduce salt intake.");
        System.out.println("- Eat potassium-rich foods like bananas and potatoes.");
    }
}
