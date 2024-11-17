package healthtips;

public class DiabeticTips extends HealthTips{
    public DiabeticTips() {
        super("Healthy Foods for Diabetic");
    }

    @Override
    public void displayTips() {
        System.out.println("Tips for High Blood:");
        System.out.println("- Reduce salt intake.");
        System.out.println("- Eat potassium-rich foods like bananas and potatoes.");
    }
}
