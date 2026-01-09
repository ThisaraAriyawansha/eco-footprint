package model;

public class UserProfile {
    private String name;
    private double carWeeklyKm;
    private String carType;
    private double electricityMonthlyKwh;
    private int meatMealsPerWeek;
    private double recyclingPercent;
    
    // Constructor with user data
    public UserProfile(String name, double carWeeklyKm, String carType, 
                      double electricityMonthlyKwh, int meatMealsPerWeek, 
                      double recyclingPercent) {
        this.name = (name == null || name.trim().isEmpty()) ? "Anonymous" : name.trim();
        this.carWeeklyKm = Math.max(0, carWeeklyKm);
        this.carType = validateCarType(carType);
        this.electricityMonthlyKwh = Math.max(0, electricityMonthlyKwh);
        this.meatMealsPerWeek = Math.max(0, Math.min(meatMealsPerWeek, 21));
        this.recyclingPercent = Math.max(0, Math.min(recyclingPercent, 100));
    }
    
    // Default constructor
    public UserProfile() {
        this("Default User", 150, "petrol", 280, 7, 50);
    }
    
    private String validateCarType(String carType) {
        String[] validTypes = {"petrol", "diesel", "electric", "hybrid"};
        carType = carType.toLowerCase().trim();
        for (String type : validTypes) {
            if (type.equals(carType)) {
                return carType;
            }
        }
        return "petrol";
    }
    
    // Getters
    public String getName() { return name; }
    public double getCarWeeklyKm() { return carWeeklyKm; }
    public String getCarType() { return carType; }
    public double getElectricityMonthlyKwh() { return electricityMonthlyKwh; }
    public int getMeatMealsPerWeek() { return meatMealsPerWeek; }
    public double getRecyclingPercent() { return recyclingPercent; }
    
    @Override
    public String toString() {
        return String.format("User: %s | Car: %.0f km/week (%s) | Electricity: %.0f kWh/month | Meat: %d meals/week | Recycling: %.0f%%",
            name, carWeeklyKm, carType, electricityMonthlyKwh, meatMealsPerWeek, recyclingPercent);
    }
}