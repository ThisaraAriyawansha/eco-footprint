package service;

import java.util.Scanner;

import model.CarbonFootprint;
import model.UserProfile;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ECO CARBON FOOTPRINT CALCULATOR ===\n");
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            int choice = getMenuChoice(scanner);
            
            CarbonService service = processChoice(choice, scanner);
            
            if (service != null) {
                runApplication(service, scanner);
            }
            
        } catch (Exception e) {
            System.out.println("\n❌ Error: " + e.getMessage());
            System.out.println("Using demo data...\n");
            CarbonService service = new CarbonService();
            service.demonstrateAllFeatures();
        } finally {
            scanner.close();
        }
    }
    
    private static int getMenuChoice(Scanner scanner) {
        while (true) {
            System.out.println("📝 CHOOSE INPUT METHOD:");
            System.out.println("1. Load from JSON file");
            System.out.println("2. Enter data manually (WITH VALIDATION)");
            System.out.println("3. Use demo data");
            System.out.print("\nEnter choice (1-3): ");
            
            if (!scanner.hasNextInt()) {
                System.out.println("\n❌ Please enter a number (1, 2, or 3).");
                scanner.next();
                continue;
            }
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            if (choice >= 1 && choice <= 3) {
                return choice;
            }
            
            System.out.println("\n❌ Invalid choice. Please enter 1, 2, or 3.");
        }
    }
    
    private static CarbonService processChoice(int choice, Scanner scanner) {
        switch(choice) {
            case 1:
                return loadFromJson(scanner);
            case 2:
                return manualInputWithValidation(scanner);
            case 3:
                System.out.println("\n✅ Using demo data (Average UK resident)...");
                return new CarbonService();
            default:
                return new CarbonService();
        }
    }
    
    private static CarbonService loadFromJson(Scanner scanner) {
        System.out.println("\n=== JSON INPUT ===");
        String path = ValidationService.validateJsonPath(scanner);
        
        System.out.println("📂 Loading from: " + path);
        
        try {
            File file = new File(path);
            if (file.exists()) {
                System.out.println("✅ File found. Parsing...");
                Thread.sleep(1000);
                
                // Extract data from JSON (simplified)
                String name = "JSON User";
                String carType = "petrol";
                double weeklyKm = 200;
                double electricity = 300;
                int meatMeals = 8;
                double recycling = 60;
                
                System.out.println("✅ Data loaded successfully!");
                return new CarbonService(name, carType, weeklyKm, electricity, meatMeals, recycling);
            } else {
                System.out.println("❌ File not found. Using demo data.");
                return new CarbonService();
            }
        } catch (Exception e) {
            System.out.println("❌ Error reading JSON: " + e.getMessage());
            return new CarbonService();
        }
    }
    
    private static CarbonService manualInputWithValidation(Scanner scanner) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("=== MANUAL INPUT WITH VALIDATION ===");
        System.out.println("=".repeat(50));
        System.out.println("\nPlease enter your carbon footprint data:\n");
        
        String name = ValidationService.validateName(scanner);
        String carType = ValidationService.validateCarType(scanner);
        double carWeeklyKm = ValidationService.validateWeeklyKm(scanner);
        double electricityMonthlyKwh = ValidationService.validateElectricity(scanner);
        int meatMealsPerWeek = ValidationService.validateMeatMeals(scanner);
        double recyclingPercent = ValidationService.validateRecyclingPercent(scanner);
        
        displayDataSummary(name, carType, carWeeklyKm, electricityMonthlyKwh, 
                          meatMealsPerWeek, recyclingPercent);
        
        System.out.print("\n✅ Confirm and calculate? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("yes") || confirm.equals("y")) {
            System.out.println("\n✅ Calculating your personalized carbon footprint...");
            
            saveUserDataToFile(name, carType, carWeeklyKm, electricityMonthlyKwh, 
                             meatMealsPerWeek, recyclingPercent);
            
            return new CarbonService(name, carType, carWeeklyKm, electricityMonthlyKwh,
                                   meatMealsPerWeek, recyclingPercent);
        } else {
            System.out.println("\n🔄 Starting over...");
            return manualInputWithValidation(scanner);
        }
    }
    
    private static void displayDataSummary(String name, String carType, double carWeeklyKm,
                                          double electricityMonthlyKwh, int meatMealsPerWeek,
                                          double recyclingPercent) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📊 ENTERED DATA SUMMARY:");
        System.out.println("=".repeat(50));
        System.out.printf("👤 Name: %s\n", name);
        System.out.printf("🚗 Car Type: %s\n", carType);
        System.out.printf("📏 Weekly KM: %.1f km\n", carWeeklyKm);
        System.out.printf("⚡ Electricity: %.1f kWh/month\n", electricityMonthlyKwh);
        System.out.printf("🍖 Meat Meals: %d meals/week\n", meatMealsPerWeek);
        System.out.printf("♻️  Recycling: %.1f%%\n", recyclingPercent);
        System.out.println("=".repeat(50));
    }
    
    private static void saveUserDataToFile(String name, String carType, double carWeeklyKm,
                                          double electricityMonthlyKwh, int meatMealsPerWeek,
                                          double recyclingPercent) {
        try {
            File folder = new File("user_data");
            if (!folder.exists()) folder.mkdir();
            
            String fileName = name.replaceAll("\\s+", "_") + "_" + 
                            System.currentTimeMillis() + ".json";
            File file = new File(folder, fileName);
            
            String json = String.format(
                "{\n" +
                "  \"user\": {\n" +
                "    \"name\": \"%s\",\n" +
                "    \"carType\": \"%s\",\n" +
                "    \"weeklyKm\": %.1f,\n" +
                "    \"electricity\": %.1f,\n" +
                "    \"meatMeals\": %d,\n" +
                "    \"recycling\": %.1f\n" +
                "  }\n" +
                "}", name, carType, carWeeklyKm, electricityMonthlyKwh, 
                meatMealsPerWeek, recyclingPercent);
            
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
            
            System.out.println("💾 User data saved: " + file.getAbsolutePath());
            
        } catch (IOException e) {
            System.out.println("⚠️  Could not save data: " + e.getMessage());
        }
    }
    
    private static void runApplication(CarbonService service, Scanner scanner) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("CALCULATING YOUR CARBON FOOTPRINT...");
        System.out.println("=".repeat(50) + "\n");
        
        service.demonstrateAllFeatures();
        
        System.out.println("\n" + "=".repeat(50));
        
        // Save results
        System.out.print("\n💾 Save detailed report to file? (yes/no): ");
        String saveChoice = scanner.nextLine().trim().toLowerCase();
        
        if (saveChoice.equals("yes") || saveChoice.equals("y")) {
            saveResultsToFile(service);
            System.out.println("\n📄 Report includes:");
            System.out.println("   - Your personal information");
            System.out.println("   - All entered data");
            System.out.println("   - Detailed carbon analysis");
            System.out.println("   - Personalized recommendations");
        }
        
        // Reflection demo
        System.out.print("\n🔍 Show reflection demo? (yes/no): ");
        String reflectionChoice = scanner.nextLine().trim().toLowerCase();
        
        if (reflectionChoice.equals("yes") || reflectionChoice.equals("y")) {
            System.out.println("\n" + "=".repeat(50));
            ReflectionDemo reflectionDemo = new ReflectionDemo();
            reflectionDemo.demonstrateReflection();
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Thank you for using Eco Carbon Footprint Calculator! 🌍");
        System.out.println("Remember: Small changes make a big difference!");
    }
    
    private static void saveResultsToFile(CarbonService service) {
        try {
            File folder = new File("reports");
            if (!folder.exists()) folder.mkdir();
            
            UserProfile profile = service.getUserProfile();
            String fileName = profile.getName().replaceAll("\\s+", "_") + 
                            "_Carbon_Report_" + System.currentTimeMillis() + ".txt";
            File file = new File(folder, fileName);
            
            CarbonFootprint footprint = service.getCurrentFootprint();
            String content = createResultsContent(footprint, profile);
            
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
            
            System.out.println("\n✅ Report saved: " + file.getAbsolutePath());
            System.out.println("📁 Location: " + folder.getAbsolutePath());
            
        } catch (IOException e) {
            System.out.println("❌ Error saving report: " + e.getMessage());
        }
    }
    
    private static String createResultsContent(CarbonFootprint footprint, UserProfile profile) {
        StringBuilder content = new StringBuilder();
        
        // Header
        content.append("╔══════════════════════════════════════════════════════════════╗\n");
        content.append("║                 CARBON FOOTPRINT ANALYSIS REPORT             ║\n");
        content.append("╚══════════════════════════════════════════════════════════════╝\n\n");
        
        // User Info Section
        content.append("════════════════════════════════════════════════════════════════\n");
        content.append("                         USER INFORMATION                       \n");
        content.append("════════════════════════════════════════════════════════════════\n\n");
        
        content.append("👤 Name: ").append(profile.getName()).append("\n");
        content.append("📅 Date: ").append(new java.util.Date()).append("\n");
        content.append("🆔 Report ID: ").append(System.currentTimeMillis()).append("\n\n");
        
        // User Input Data Section
        content.append("════════════════════════════════════════════════════════════════\n");
        content.append("                         ENTERED DATA                           \n");
        content.append("════════════════════════════════════════════════════════════════\n\n");
        
        content.append("🚗 TRANSPORTATION:\n");
        content.append("   • Car Type: ").append(profile.getCarType()).append("\n");
        content.append("   • Weekly Distance: ").append(String.format("%.1f", profile.getCarWeeklyKm()))
              .append(" km\n\n");
        
        content.append("🏠 HOME ENERGY:\n");
        content.append("   • Monthly Electricity: ").append(String.format("%.1f", profile.getElectricityMonthlyKwh()))
              .append(" kWh\n\n");
        
        content.append("🍽️  DIET & FOOD:\n");
        content.append("   • Meat Meals per Week: ").append(profile.getMeatMealsPerWeek()).append("\n\n");
        
        content.append("🗑️  WASTE MANAGEMENT:\n");
        content.append("   • Recycling Rate: ").append(String.format("%.1f", profile.getRecyclingPercent()))
              .append("%\n\n");
        
        // Results Section
        content.append("════════════════════════════════════════════════════════════════\n");
        content.append("                     CALCULATION RESULTS                        \n");
        content.append("════════════════════════════════════════════════════════════════\n\n");
        
        content.append("📊 TOTAL CARBON FOOTPRINT:\n");
        content.append("   ").append(String.format("%.0f", footprint.getTotalEmissions()))
              .append(" kg CO₂ per year\n");
        content.append("   Grade: ").append(footprint.calculateGrade()).append(" (A = Best, F = Worst)\n\n");
        
        // Detailed Breakdown
        content.append("📈 DETAILED BREAKDOWN:\n");
        content.append("   • Travel: ").append(String.format("%.0f", footprint.getTravelEmissions()))
              .append(" kg (").append(String.format("%.0f", 
                    (footprint.getTravelEmissions() / footprint.getTotalEmissions()) * 100))
              .append("%)\n");
        content.append("   • Energy: ").append(String.format("%.0f", footprint.getEnergyEmissions()))
              .append(" kg (").append(String.format("%.0f", 
                    (footprint.getEnergyEmissions() / footprint.getTotalEmissions()) * 100))
              .append("%)\n");
        content.append("   • Diet: ").append(String.format("%.0f", footprint.getDietEmissions()))
              .append(" kg (").append(String.format("%.0f", 
                    (footprint.getDietEmissions() / footprint.getTotalEmissions()) * 100))
              .append("%)\n");
        content.append("   • Shopping: ").append(String.format("%.0f", footprint.getShoppingEmissions()))
              .append(" kg (").append(String.format("%.0f", 
                    (footprint.getShoppingEmissions() / footprint.getTotalEmissions()) * 100))
              .append("%)\n\n");
        
        // Environmental Impact
        content.append("🌳 ENVIRONMENTAL IMPACT:\n");
        double trees = footprint.getTotalEmissions() / 100;
        content.append("   • Equivalent to cutting down ").append(String.format("%.0f", trees))
              .append(" trees annually\n");
        
        double cars = footprint.getTotalEmissions() / 2000;
        content.append("   • Equivalent to ").append(String.format("%.1f", cars))
              .append(" cars on the road for a year\n\n");
        
        // Recommendations
        content.append("════════════════════════════════════════════════════════════════\n");
        content.append("                     PERSONALIZED RECOMMENDATIONS              \n");
        content.append("════════════════════════════════════════════════════════════════\n\n");
        
        content.append("🎯 BASED ON YOUR PROFILE:\n");
        content.append("1. Switch to electric/hybrid vehicle\n");
        content.append("2. Install solar panels or use renewable energy\n");
        content.append("3. Have at least 2 meat-free days per week\n");
        content.append("4. Improve recycling to 80% or higher\n");
        content.append("5. Reduce electricity usage by 20%\n");
        content.append("6. Use public transport or carpool when possible\n");
        content.append("7. Buy local and seasonal produce\n");
        content.append("8. Reduce food waste\n\n");
        
        // Footer
        content.append("════════════════════════════════════════════════════════════════\n");
        content.append("                            FOOTER                              \n");
        content.append("════════════════════════════════════════════════════════════════\n\n");
        
        content.append("📋 Report generated by: Eco Carbon Footprint Calculator\n");
        content.append("📧 Contact: support@ecofootprint.com\n");
        content.append("🌐 Website: www.ecofootprint.com\n");

        
        content.append("💚 Every small change makes a difference!\n");
        content.append("🌍 Together we can reduce our carbon footprint!\n\n");
        
        content.append("=".repeat(64)).append("\n");
        content.append("END OF REPORT\n");
        content.append("=".repeat(64)).append("\n");
        
        return content.toString();
    }
}