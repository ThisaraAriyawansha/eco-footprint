package patterns;

import model.UserProfile;

public class DietCalculator implements CarbonCalculator {
    
    @Override
    public double calculateEmissions(Object data) {
        if (data instanceof UserProfile) {
            UserProfile profile = (UserProfile) data;
            
            double meatEmission = calculateMeatEmission(
                profile.getMeatMealsPerWeek()
            );
            
            double dairyEmission = calculateDairyEmission(profile);
            double foodWasteEmission = calculateFoodWasteEmission(profile);
            
            return meatEmission + dairyEmission + foodWasteEmission;
        }
        return 0;
    }
    
    private double calculateMeatEmission(int meatMealsPerWeek) {
        double annualMeatMeals = meatMealsPerWeek * 52;
        // Average: 3.5kg CO2 per meat meal
        return annualMeatMeals * 3.5;
    }
    
    private double calculateDairyEmission(UserProfile profile) {
        // Estimate dairy based on meat consumption
        if (profile.getMeatMealsPerWeek() > 10) {
            return 800; // High dairy consumption
        } else if (profile.getMeatMealsPerWeek() > 5) {
            return 500; // Medium dairy
        } else {
            return 300; // Low dairy
        }
    }
    
    private double calculateFoodWasteEmission(UserProfile profile) {
        // Estimate food waste based on recycling habits
        if (profile.getRecyclingPercent() < 30) {
            return 500; // Poor waste management
        } else if (profile.getRecyclingPercent() < 70) {
            return 300; // Average waste management
        } else {
            return 150; // Good waste management
        }
    }
}