package patterns;

import model.UserProfile;

public class TravelCalculator implements CarbonCalculator {
    
    @Override
    public double calculateEmissions(Object data) {
        if (data instanceof UserProfile) {
            UserProfile profile = (UserProfile) data;
            
            double carEmission = calculateCarEmission(
                profile.getCarWeeklyKm(), 
                profile.getCarType()
            );
            
            // Public transport based on car usage
            double publicTransportEmission = (profile.getCarWeeklyKm() > 100) ? 50 : 100;
            
            // Flights estimation based on lifestyle
            double flightEmission = estimateFlightEmissions(profile);
            
            return carEmission + publicTransportEmission + flightEmission;
        }
        return 0;
    }
    
    private double calculateCarEmission(double weeklyKm, String carType) {
        double annualKm = weeklyKm * 52;
        
        // Emission factors (kg CO2 per km)
        switch(carType.toLowerCase()) {
            case "petrol": return annualKm * 0.18;  // 180g/km
            case "diesel": return annualKm * 0.16;  // 160g/km  
            case "electric": return annualKm * 0.04; // 40g/km (grid electricity)
            case "hybrid": return annualKm * 0.10;   // 100g/km
            default: return annualKm * 0.15;
        }
    }
    
    private double estimateFlightEmissions(UserProfile profile) {
        // Estimate flights based on car usage (proxy for travel frequency)
        if (profile.getCarWeeklyKm() > 300) {
            return 500; // Frequent traveler
        } else if (profile.getCarWeeklyKm() > 100) {
            return 200; // Occasional traveler
        } else {
            return 50;  // Rare traveler
        }
    }
}