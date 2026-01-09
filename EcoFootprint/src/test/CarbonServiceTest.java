package test;

import service.CarbonService;
import service.ReflectionDemo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class CarbonServiceTest {
    private CarbonService carbonService;
    
    @BeforeEach
    void setUp() {
        carbonService = new CarbonService();
    }
    
    @Test
    void testServiceCreation() {
        assertNotNull(carbonService);
    }
    
    @Test
    void testReflectionDemo() {
        ReflectionDemo reflectionDemo = new ReflectionDemo();
        assertDoesNotThrow(() -> reflectionDemo.demonstrateReflection());
    }
    
    @Test
    void testFootprintCalculation() {
        assertDoesNotThrow(() -> carbonService.calculateFootprint());
    }
    
    @Test
    void testSuggestionsNotEmpty() {
        assertFalse(carbonService.getReductionSuggestions().isEmpty());
    }
}