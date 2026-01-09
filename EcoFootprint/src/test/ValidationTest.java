package test;

import service.ValidationService;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationTest {
    
    @Test
    void testNameValidation() {
        String input = "John Doe\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        String name = ValidationService.validateName(scanner);
        assertEquals("John Doe", name);
    }
    
    @Test
    void testCarTypeValidation() {
        String input = "electric\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        String carType = ValidationService.validateCarType(scanner);
        assertEquals("electric", carType);
    }
    
    @Test
    void testWeeklyKmValidation() {
        String input = "150\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        double km = ValidationService.validateWeeklyKm(scanner);
        assertEquals(150.0, km);
    }
    
    @Test
    void testInvalidInput() {
        // Test with invalid input followed by valid input
        String input = "abc\n200\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        
        double km = ValidationService.validateWeeklyKm(scanner);
        assertEquals(200.0, km);
    }
}