package healthtips;

import display.DisplayUtils;

public class BoostImmunityTips extends HealthTips {
    public BoostImmunityTips() {
        super("Healthy Foods to Boost the Immune System");
    }

    @Override
    public void displayTips(DisplayUtils display) {
        display.printHeader("Healthy Foods to Boost Your Immune System");

        System.out.println("\nHere are some top foods to include in your diet for better immunity:");

        System.out.println("\n1. \"Citrus Fruits:\"");
        System.out.println("   - Rich in Vitamin C, which supports the production of white blood cells.");
        System.out.println("   - Examples: Oranges, lemons, limes, grapefruits.");
        System.out.println("   - Daily Recommendation: 75 mg for women, 90 mg for men.");

        System.out.println("\n2. \"Red Bell Peppers:\"");
        System.out.println("   - Contain high levels of Vitamin C and beta-carotene.");
        System.out.println("   - Enhance skin health and immune function.");

        System.out.println("\n3. \"Broccoli:\"");
        System.out.println("   - Loaded with vitamins A, C, and E, along with powerful antioxidants.");
        System.out.println("   - Best consumed lightly cooked to retain nutrients.");

        System.out.println("\n4. \"Garlic:\"");
        System.out.println("   - Known for its infection-fighting properties.");
        System.out.println("   - Contains compounds that boost immune system response.");

        System.out.println("\n5. \"Ginger\"");
        System.out.println("   - Reduces inflammation and supports overall immunity.");
        System.out.println("   - Perfect for teas, soups, and stir-fries.");

        System.out.println("\n6. \"Spinach:\"");
        System.out.println("   - A powerhouse of Vitamin C, antioxidants, and beta-carotene.");
        System.out.println("   - Light cooking improves nutrient absorption.");

        System.out.println("\n7. \"Almonds:\"");
        System.out.println("   - Packed with Vitamin E and healthy fats.");
        System.out.println("   - A small handful a day is beneficial.");

        System.out.println("\n8. \"Green and Black Teas:\"");
        System.out.println("   - Contain antioxidants that combat free radicals.");
        System.out.println("   - Support overall immune health.");

        System.out.println("\n9. \"Poultry:\"");
        System.out.println("   - High in Vitamin B6, essential for healthy immune function.");
        System.out.println("   - Chicken soup can reduce inflammation and improve hydration.");

        System.out.println("\n10. \"Dark Chocolate:\"");
        System.out.println("   - Contains antioxidants like theobromine.");
        System.out.println("   - Helps protect cells and boost mood—consume in moderation!");

        System.out.println("Combine these foods with regular exercise, proper sleep (7-9 hours), and stress management for optimal results.");
        System.out.println("\n==========================================================================================================================\n");
    }
}
