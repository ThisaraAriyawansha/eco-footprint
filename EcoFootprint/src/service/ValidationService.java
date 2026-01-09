package service;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ValidationService {
    
    public static String validateName(Scanner scanner) {
        while (true) {
            System.out.print("1. Your name: ");
            String name = scanner.nextLine().trim();
            
            if (name.isEmpty()) {
                System.out.println("❌ Name cannot be empty.");
                continue;
            }
            
            if (!Pattern.matches("^[a-zA-Z\\s]+$", name)) {
                System.out.println("❌ Name should contain only letters.");
                continue;
            }
            
            return name;
        }
    }
    
    public static String validateCarType(Scanner scanner) {
        String[] validTypes = {"petrol", "diesel", "electric", "hybrid"};
        
        while (true) {
            System.out.print("2. Car type (petrol/diesel/electric/hybrid): ");
            String carType = scanner.nextLine().trim().toLowerCase();
            
            for (String type : validTypes) {
                if (type.equals(carType)) {
                    return carType;
                }
            }
            
            System.out.println("❌ Invalid car type.");
            System.out.print("   Use petrol? (yes/no): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("yes") || choice.equals("y")) {
                return "petrol";
            }
        }
    }
    
    public static double validateWeeklyKm(Scanner scanner) {
        while (true) {
            System.out.print("3. Weekly km driven: ");
            
            if (!scanner.hasNextDouble()) {
                System.out.println("❌ Please enter a number.");
                scanner.next();
                continue;
            }
            
            double km = scanner.nextDouble();
            scanner.nextLine();
            
            if (km < 0) {
                System.out.println("❌ Cannot be negative.");
                continue;
            }
            
            if (km > 5000) {
                System.out.println("⚠️  " + km + " km/week is very high.");
                System.out.print("   Confirm? (yes/no): ");
                String confirm = scanner.nextLine().trim().toLowerCase();
                if (!confirm.equals("yes") && !confirm.equals("y")) {
                    continue;
                }
            }
            
            return km;
        }
    }
    
    public static double validateElectricity(Scanner scanner) {
        while (true) {
            System.out.print("4. Monthly electricity (kWh): ");
            
            if (!scanner.hasNextDouble()) {
                System.out.println("❌ Please enter a number.");
                scanner.next();
                continue;
            }
            
            double kwh = scanner.nextDouble();
            scanner.nextLine();
            
            if (kwh < 0) {
                System.out.println("❌ Cannot be negative.");
                continue;
            }
            
            return kwh;
        }
    }
    
    public static int validateMeatMeals(Scanner scanner) {
        while (true) {
            System.out.print("5. Meat meals per week (0-21): ");
            
            if (!scanner.hasNextInt()) {
                System.out.println("❌ Please enter a whole number.");
                scanner.next();
                continue;
            }
            
            int meals = scanner.nextInt();
            scanner.nextLine();
            
            if (meals < 0) {
                System.out.println("❌ Cannot be negative.");
                continue;
            }
            
            if (meals > 21) {
                System.out.println("❌ Maximum 21 meals per week.");
                continue;
            }
            
            return meals;
        }
    }
    
    public static double validateRecyclingPercent(Scanner scanner) {
        while (true) {
            System.out.print("6. Recycling percentage (0-100): ");
            
            if (!scanner.hasNextDouble()) {
                System.out.println("❌ Please enter a number.");
                scanner.next();
                continue;
            }
            
            double percent = scanner.nextDouble();
            scanner.nextLine();
            
            if (percent < 0) {
                System.out.println("❌ Cannot be negative.");
                continue;
            }
            
            if (percent > 100) {
                System.out.println("❌ Cannot exceed 100%.");
                continue;
            }
            
            return percent;
        }
    }
    
    public static String validateJsonPath(Scanner scanner) {
        System.out.print("\nEnter JSON file path (or press Enter for default): ");
        String path = scanner.nextLine().trim();
        
        if (path.isEmpty()) {
            return "data/user_profile.json";
        }
        
        return path;
    }
}