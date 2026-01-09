package patterns;

import model.CarbonFootprint;

// Make abstract class PUBLIC
public abstract class CarbonDecorator {
    protected CarbonFootprint footprint;
    
    public CarbonDecorator(CarbonFootprint footprint) {
        this.footprint = footprint;
    }
    
    public abstract CarbonFootprint decorate();
}

// Make OffsetDecorator PUBLIC
class OffsetDecorator extends CarbonDecorator {
    private double offsetAmount;
    
    public OffsetDecorator(CarbonFootprint footprint, double offsetAmount) {
        super(footprint);
        this.offsetAmount = offsetAmount;
    }
    
    @Override
    public CarbonFootprint decorate() {
        CarbonFootprint decorated = new CarbonFootprint(
            footprint.getTravelEmissions() - offsetAmount * 0.4,
            footprint.getEnergyEmissions() - offsetAmount * 0.3,
            footprint.getDietEmissions() - offsetAmount * 0.2,
            footprint.getShoppingEmissions() - offsetAmount * 0.1
        );
        return decorated;
    }
}