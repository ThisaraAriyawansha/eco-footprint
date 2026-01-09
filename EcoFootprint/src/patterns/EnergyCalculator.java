package patterns;

import model.UserProfile;

public class EnergyCalculator implements CarbonCalculator {
    
    @Override
    public double calculateEmissions(Object data) {
        if (data instanceof UserProfile) {
            UserProfile profile = (UserProfile) data;
            
            double electricityEmission = calculateElectricityEmission(
                profile.getElectricityMonthlyKwh()
            );
            
            double heatingEmission = calculateHeatingEmission(profile);
            double reductionFromRecycling = calculateRecyclingReduction(
                profile.getRecyclingPercent()
            );
            
            return electricityEmission + heatingEmission - reductionFromRecycling;
        }
        return 0;
    }
    
    private double calculateElectricityEmission(double monthlyKwh) {
        double annualKwh = monthlyKwh * 12;
        return annualKwh * 0.233; // UK average: 233g CO2 per kWh
    }
    
    private double calculateHeatingEmission(UserProfile profile) {
        // Estimate heating based on electricity usage (proxy for home size)
        if (profile.getElectricityMonthlyKwh() > 400) {
            return 2000; // Large home
        } else if (profile.getElectricityMonthlyKwh() > 200) {
            return 1200; // Medium home
        } else {
            return 800;  // Small home
        }
    }
    
    private double calculateRecyclingReduction(double recyclingPercent) {
        // Maximum reduction of 500kg for 100% recycling
        return (recyclingPercent / 100) * 500;
    }
}