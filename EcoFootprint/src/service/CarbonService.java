package service;

import model.*;
import patterns.*;
import java.util.*;

public class CarbonService {
    private UserProfile currentProfile;
    private CarbonFootprint currentFootprint;
    
    // Default constructor - uses demo data
    public CarbonService() {
        this.currentProfile = new UserProfile();
        this.currentFootprint = calculateFootprint();
    }
    
    // Constructor with custom user data
    public CarbonService(String name, String carType, double carWeeklyKm, 
                        double electricityMonthlyKwh, int meatMealsPerWeek, 
                        double recyclingPercent) {
        this.currentProfile = new UserProfile(name, carWeeklyKm, carType, 
                                            electricityMonthlyKwh, meatMealsPerWeek, 
                                            recyclingPercent);
        this.currentFootprint = calculateFootprint();
    }
    
    public CarbonFootprint calculateFootprint() {
        // Strategy Pattern: Different calculators for different categories
        CarbonCalculator travelCalc = new TravelCalculator();
        CarbonCalculator energyCalc = new EnergyCalculator();
        CarbonCalculator dietCalc = new DietCalculator();
        
        double travel = travelCalc.calculateEmissions(currentProfile);
        double energy = energyCalc.calculateEmissions(currentProfile);
        double diet = dietCalc.calculateEmissions(currentProfile);
        double shopping = calculateShoppingEmissions(currentProfile);
        
        return new CarbonFootprint(travel, energy, diet, shopping);
    }
    
    private double calculateShoppingEmissions(UserProfile profile) {
        // Base shopping emissions + extra based on consumption
        double base = 600;
        double extra = (profile.getCarWeeklyKm() > 200) ? 100 : 0;
        extra += (profile.getElectricityMonthlyKwh() > 300) ? 50 : 0;
        return base + extra;
    }
    
    // Functional Programming with Streams
    public List<String> getReductionSuggestions() {
        List<String> allSuggestions = Arrays.asList(
            "Switch to electric car: -1200 kg/year",
            "Install solar panels: -800 kg/year", 
            "Meat-free days (2/week): -600 kg/year",
            "Improve recycling to 80%: -300 kg/year",
            "Reduce electricity by 20%: -500 kg/year",
            "Carpool to work: -400 kg/year",
            "Buy local food: -200 kg/year"
        );
        
        // Filter based on user profile
        return allSuggestions.stream()
            .filter(s -> isRelevantSuggestion(s))
            .limit(3) // Show top 3 most relevant
            .collect(java.util.stream.Collectors.toList());
    }
    
    private boolean isRelevantSuggestion(String suggestion) {
        if (suggestion.contains("electric car") && 
            currentProfile.getCarWeeklyKm() > 100 && 
            !currentProfile.getCarType().equals("electric")) {
            return true;
        }
        if (suggestion.contains("solar") && currentProfile.getElectricityMonthlyKwh() > 200) {
            return true;
        }
        if (suggestion.contains("Meat-free") && currentProfile.getMeatMealsPerWeek() > 5) {
            return true;
        }
        if (suggestion.contains("recycling") && currentProfile.getRecyclingPercent() < 70) {
            return true;
        }
        if (suggestion.contains("electricity") && currentProfile.getElectricityMonthlyKwh() > 250) {
            return true;
        }
        if (suggestion.contains("Carpool") && currentProfile.getCarWeeklyKm() > 150) {
            return true;
        }
        return suggestion.contains("local food"); // Always suggest this
    }
    
    public void demonstrateAllFeatures() {
        System.out.println("=== ECO CARBON FOOTPRINT CALCULATOR ===\n");
        
        System.out.println("User Profile:");
        System.out.println(currentProfile + "\n");
        
        currentFootprint = calculateFootprint();
        
        System.out.println("📊 YOUR CARBON FOOTPRINT ANALYSIS:");
        System.out.println("Total Carbon Footprint: " + 
            String.format("%.0f", currentFootprint.getTotalEmissions()) + " kg CO2/year");
        System.out.println("Grade: " + currentFootprint.calculateGrade() + 
            " (Average: 10,000 kg CO2/year)\n");
        
        System.out.println("BREAKDOWN:");
        System.out.println("- Travel: " + 
            String.format("%.0f", currentFootprint.getTravelEmissions()) + 
            " kg (" + getPercentage(currentFootprint.getTravelEmissions()) + "%) 🚗");
        System.out.println("- Energy: " + 
            String.format("%.0f", currentFootprint.getEnergyEmissions()) + 
            " kg (" + getPercentage(currentFootprint.getEnergyEmissions()) + "%) ⚡");
        System.out.println("- Diet: " + 
            String.format("%.0f", currentFootprint.getDietEmissions()) + 
            " kg (" + getPercentage(currentFootprint.getDietEmissions()) + "%) 🍖");
        System.out.println("- Shopping: " + 
            String.format("%.0f", currentFootprint.getShoppingEmissions()) + 
            " kg (" + getPercentage(currentFootprint.getShoppingEmissions()) + "%) 🛍️\n");
        
        System.out.println("🎯 PERSONALIZED SUGGESTIONS TO REDUCE:");
        List<String> suggestions = getReductionSuggestions();
        if (suggestions.isEmpty()) {
            System.out.println("Great job! Your footprint is already very low. 🌟");
        } else {
            for (int i = 0; i < suggestions.size(); i++) {
                System.out.println((i + 1) + ". " + suggestions.get(i));
            }
        }
        
        System.out.println("\n🌳 ENVIRONMENTAL IMPACT:");
        double trees = currentFootprint.getTotalEmissions() / 100;
        System.out.println("Your footprint equals cutting down " + 
            String.format("%.0f", trees) + " trees annually!");
        
        char currentGrade = currentFootprint.calculateGrade();
        if (currentGrade != 'A') {
            double reductionNeeded = currentFootprint.getTotalEmissions() * 0.2;
            System.out.println("Reduce by " + String.format("%.0f", reductionNeeded) + 
                " kg to reach Grade " + (char)(currentGrade - 1) + ".");
        } else {
            System.out.println("Excellent! You have the best grade (A). Keep it up! 🏆");
        }
        
        // Observer Pattern Demo
        System.out.println("\n=== OBSERVER PATTERN DEMO ===");
        CarbonSubject subject = new CarbonSubject();
        subject.addObserver(msg -> System.out.println("📢 Observer 1: " + msg));
        subject.addObserver(msg -> System.out.println("📢 Observer 2: " + msg));
        subject.notifyObservers("Carbon footprint calculated: " + 
            String.format("%.0f", currentFootprint.getTotalEmissions()) + " kg CO2");
        
        // Decorator Pattern Demo
        System.out.println("\n=== DECORATOR PATTERN DEMO ===");
        System.out.println("Applying carbon offset (planting trees)...");
        CarbonFootprint offsetFootprint = applyOffset(currentFootprint, 500);
        System.out.println("After carbon offset: " + 
            String.format("%.0f", offsetFootprint.getTotalEmissions()) + " kg CO2/year");
    }
    
    // Helper method for offset
    private CarbonFootprint applyOffset(CarbonFootprint footprint, double offsetAmount) {
        return new CarbonFootprint(
            footprint.getTravelEmissions() - offsetAmount * 0.4,
            footprint.getEnergyEmissions() - offsetAmount * 0.3,
            footprint.getDietEmissions() - offsetAmount * 0.2,
            footprint.getShoppingEmissions() - offsetAmount * 0.1
        );
    }
    
    private int getPercentage(double value) {
        double total = currentFootprint.getTotalEmissions();
        if (total == 0) return 0;
        return (int) ((value / total) * 100);
    }
    
    // Getter for current footprint (for file saving)
    public CarbonFootprint getCurrentFootprint() {
        return currentFootprint;
    }
    
    public UserProfile getUserProfile() {
        return currentProfile;
    }
}