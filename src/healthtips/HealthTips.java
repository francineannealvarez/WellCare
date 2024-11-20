package healthtips;

import java.util.InputMismatchException;
import java.util.Scanner;
import display.DisplayUtils;

// Base class
public abstract class HealthTips {
    protected String title; 

    public HealthTips(String title) {
        this.title = title;
    }

    public abstract void displayTips(DisplayUtils display);

    public String getTitle() {
        return title;
    }

    

    public static void showCategories(Scanner scanner, DisplayUtils display) {
        boolean exit = false;

        while (!exit) {
            display.printHeader("Healthy Food Tips Categories");
            System.out.println("1. For Low Blood");
            System.out.println("2. For High Blood");
            System.out.println("3. For Weight Loss");
            System.out.println("4. Exit");

            System.out.print("Please select a category (1-3): ");
            
            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        LowBloodTips lowBlood = new LowBloodTips();
                        System.out.println("\nCategory: " + lowBlood.getTitle());
                        lowBlood.displayTips(display);
                        break;
                    case 2:
                        HighBloodTips highBlood = new HighBloodTips();
                        System.out.println("\nCategory: " + highBlood.getTitle());
                        highBlood.displayTips(display);
                        break;
                    case 3:
                        DietingTips dieting = new DietingTips();
                        System.out.println("\nCategory: " + dieting.getTitle());
                        dieting.displayTips(display);
                        break;
                    case 4:
                        exit = true;
                        System.out.println("Exiting...");
                        break; 
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    // Subclass: LowBloodTips
    public static class LowBloodTips extends HealthTips {
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

    // Subclass: HighBloodTips
    public static class HighBloodTips extends HealthTips {
        public HighBloodTips() {
            super("Healthy Foods for High Blood");
        }

        @Override
        public void displayTips(DisplayUtils display) {
            display.printHeader("Healthy Foods for High Blood Pressure");

            System.out.println("Here are some tips to help manage high blood pressure:");
            System.out.println("\n1. \"Fruits\"");
            System.out.println("   - Include fruits like kiwi, oranges, bananas, and apricots.");
            System.out.println("   - Prunes and acorn squash are also excellent choices.");

            System.out.println("\n2. \"Vegetables\"");
            System.out.println("   - Green leafy vegetables such as cabbage, collard greens, and spinach.");
            System.out.println("   - Beets are especially beneficial for lowering blood pressure.");

            System.out.println("\n3. \"Nuts and Seeds\"");
            System.out.println("   - Include nuts like pistachios and walnuts as healthy snacks.");

            System.out.println("\n4. \"Oily Fish\"");
            System.out.println("   - Choose fish rich in omega-3 fatty acids, such as mackerel and salmon.");

            System.out.println("\n5. \"Spices\"");
            System.out.println("   - Add spices like cinnamon and garlic to your meals for extra health benefits.");

            System.out.println("\n6. \"Berries\"");
            System.out.println("   - Blueberries and strawberries are great for heart health.");
            System.out.println("   - Tips: Enjoy them as snacks, add them to smoothies, or sprinkle them on oatmeal for breakfast.");

            System.out.println("\n7. \"Dark Chocolate\"");
            System.out.println("   - Opt for dark chocolate in moderation to satisfy your sweet tooth and support heart health.");

            System.out.println("\n8. \"Fermented Foods\"");
            System.out.println("   - Include foods like kimchi, miso, and yogurt for their gut and heart benefits.");

            System.out.println("\n9. \"Foods Rich in Potassium\"");
            System.out.println("   - Bananas, potatoes, apricots, prunes, and squash help balance sodium levels in the body.");

            System.out.println("\n10. \"Foods to Avoid\"");
            System.out.println("   - Limit processed foods, coffee, and salt to maintain healthy blood pressure.\n");

            System.out.println("Consistency is key! Make these foods part of your routine, and consult a healthcare provider for personalized guidance.");
            System.out.println("\n==========================================================================================================================\n");
        }
    }

    // Subclass: DietingTips
    public static class DietingTips extends HealthTips {
        public DietingTips() {
            super("Healthy Foods to Support Weight Loss");
        }
    
        @Override
        public void displayTips(DisplayUtils display) {
            display.printHeader("Healthy Foods to Support Weight Loss ");

            System.out.println("Here are some tips for weight loss and healthy eating:");

            System.out.println("\n1. \"Eggs - Nutrient-Dense\"");
            System.out.println("   - Eggs are rich in protein and healthy fats, helping you feel full and satisfied.");

            System.out.println("\n2. \"Leafy Greens\"");
            System.out.println("   - Vegetables like kale, spinach, and collard greens are low in calories but high in nutrients.");

            System.out.println("\n3. \"Fish\"");
            System.out.println("   - Rich in protein and healthy fats, fish like salmon and sardines help you manage your weight and promote heart health.");

            System.out.println("\n4. \"Cruciferous Vegetables\"");
            System.out.println("   - Broccoli, cauliflower, and cabbage are high in fiber and help fill you up, making them great for weight loss.");

            System.out.println("\n5. \"Lean Meats\"");
            System.out.println("   - Chicken breast, turkey, and lean cuts of beef provide a great source of protein to support your weight loss goals.");

            System.out.println("\n6. \"Potatoes and Root Vegetables\"");
            System.out.println("   - Despite being carbs, they are filling and promote overall health when consumed in moderation.");

            System.out.println("\n7. \"Legumes\"");
            System.out.println("   - Beans, lentils, and chickpeas are high in protein and fiber, helping you feel fuller longer.");

            System.out.println("\n8. \"Cottage Cheese\"");
            System.out.println("   - A great source of protein, calcium, and probiotics, cottage cheese helps with satiety and weight control.");

            System.out.println("\n9. \"Avocados\"");
            System.out.println("   - Packed with healthy fats, avocados keep you full and provide essential nutrients for a balanced diet.");

            System.out.println("\n10. \"Nuts\"");
            System.out.println("   - Almonds, walnuts, and other nuts provide healthy fats, protein, and fiber, supporting heart health and weight loss.");

            System.out.println("\n11. \"Whole Grains\"");
            System.out.println("   - Swap refined grains for whole grains like quinoa, oats, and brown rice to support healthy weight management.");

            System.out.println("\n12. \"Chia Seeds\"");
            System.out.println("   - High in fiber and protein, chia seeds promote fullness and can help with weight control.");

            System.out.println("\n13. \"Greek Yogurt\"");
            System.out.println("   - Packed with protein and probiotics, Greek yogurt is great for digestion and supporting weight loss.");

            System.out.println("\n14. \"Dark Chocolate\"");
            System.out.println("   - In moderation, dark chocolate can be a satisfying treat while dieting.");

            System.out.println("\n15. \"Limit Processed Foods and Sugar\"");
            System.out.println("   - Focus on whole, unprocessed foods to avoid hidden sugars and unhealthy fats.\n");

            System.out.println("Combine these tips with regular exercise and a positive mindset to reach your weight loss goals!");
            System.out.println("\n==========================================================================================================================\n");
        }
    }
}

