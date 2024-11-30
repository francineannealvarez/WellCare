package healthtips;

import display.DisplayUtils;

public class LowBloodTips extends HealthTips {
    public LowBloodTips() {
        super("Healthy Foods for Low Blood");
    }

    @Override
    public void displayTips(DisplayUtils display) {
        display.printHeader("Tips for Low Blood Pressure");

        System.out.println("Here are some tips to help improve your blood levels:");
        System.out.println("\n1. \"Stay Hydrated\"");
        System.out.println("   - Drink plenty of water to maintain good circulation.");

        System.out.println("\n2. \"Include Caffeine\"");
        System.out.println("   - Drink coffee or tea occasionally to boost blood pressure.");

        System.out.println("\n3. \"Eat Iron-Rich Foods\"");
        System.out.println("   - Red Meats: Beef, lamb, pork, goat meat, and liver.");
        System.out.println("   - Eggs: One egg per day is a good source of iron.");
        System.out.println("   - Legumes: Lentils, chickpeas, and beans are great choices.");

        System.out.println("\n4. \"Boost Your Vitamin B12 and Folate\"");
        System.out.println("   - Foods like fish, chicken, and cottage cheese are excellent.");
        System.out.println("   - Include leafy greens such as broccoli and asparagus.");

        System.out.println("\n5. \"Enhance Iron Absorption\"");
        System.out.println("   - Pair iron-rich foods with Vitamin C sources like oranges or bell peppers.");

        System.out.println("\n6. \"Increase Your Salt Intake (Moderately)\"");
        System.out.println("   - Foods like olives can help maintain healthy sodium levels.\n");

        System.out.println("Stay consistent with these tips, and consult your doctor for personalized advice!");
        System.out.println("\n==========================================================================================================================\n");
    }
}
