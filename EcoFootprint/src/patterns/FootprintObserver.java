package patterns;

@FunctionalInterface
public interface FootprintObserver {
    void update(String message);
}