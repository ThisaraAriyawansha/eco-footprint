package model;

public class CarbonFootprint {
    private double travelEmissions;
    private double energyEmissions;
    private double dietEmissions;
    private double shoppingEmissions;
    
    // Bad smell: Long constructor
    public CarbonFootprint(double travelEmissions, double energyEmissions, 
                          double dietEmissions, double shoppingEmissions) {
        this.travelEmissions = travelEmissions;
        this.energyEmissions = energyEmissions;
        this.dietEmissions = dietEmissions;
        this.shoppingEmissions = shoppingEmissions;
    }
    
    // Bad smell: Primitive obsession
    public CarbonFootprint() {
        this.travelEmissions = 0;
        this.energyEmissions = 0;
        this.dietEmissions = 0;
        this.shoppingEmissions = 0;
    }
    
    public double getTotalEmissions() {
        return travelEmissions + energyEmissions + dietEmissions + shoppingEmissions;
    }
    
    // Bad smell: Magic numbers
    public char calculateGrade() {
        double total = getTotalEmissions();
        if (total < 3000) return 'A';
        else if (total < 6000) return 'B';
        else if (total < 9000) return 'C';
        else if (total < 12000) return 'D';
        else return 'F';
    }
    
    // Getters
    public double getTravelEmissions() { return travelEmissions; }
    public double getEnergyEmissions() { return energyEmissions; }
    public double getDietEmissions() { return dietEmissions; }
    public double getShoppingEmissions() { return shoppingEmissions; }
    
    public void setTravelEmissions(double travelEmissions) {
        this.travelEmissions = travelEmissions;
    }
    
    public void setEnergyEmissions(double energyEmissions) {
        this.energyEmissions = energyEmissions;
    }
    
    public void setDietEmissions(double dietEmissions) {
        this.dietEmissions = dietEmissions;
    }
    
    public void setShoppingEmissions(double shoppingEmissions) {
        this.shoppingEmissions = shoppingEmissions;
    }
    
    @Override
    public String toString() {
        return String.format("Total: %.0f kg CO2/year (Grade: %c)", 
            getTotalEmissions(), calculateGrade());
    }
}