package patterns;

import java.util.ArrayList;
import java.util.List;

public class CarbonSubject {
    private List<FootprintObserver> observers = new ArrayList<>();
    
    public void addObserver(FootprintObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(FootprintObserver observer) {
        observers.remove(observer);
    }
    
    // FIX: Changed from protected to public
    public void notifyObservers(String message) {
        for (FootprintObserver observer : observers) {
            observer.update(message);
        }
    }
}