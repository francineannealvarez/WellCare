package healthtips;

public class DietingTips extends HealthTips{
    public DietingTips() {
        super("Healthy Foods for those who wants to Diet");
    }

    @Override
    public void displayTips() {
        System.out.println("Tips for High Blood:");
        System.out.println("- Reduce salt intake.");
        System.out.println("- Eat potassium-rich foods like bananas and potatoes.");
    }
}
