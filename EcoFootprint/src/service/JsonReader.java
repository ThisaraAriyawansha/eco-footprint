package service;

import model.UserProfile;
import java.io.*;
import java.nio.file.*;

public class JsonReader {
    
    public static UserProfile readProfileFromFile(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            return parseJson(content);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return null;
        }
    }
    
    private static UserProfile parseJson(String jsonContent) {
        // Simple JSON parser (for demo purposes)
        // In real app, use Jackson or Gson library
        
        // Extract values using string operations
        String name = extractValue(jsonContent, "name");
        double carWeeklyKm = Double.parseDouble(extractValue(jsonContent, "carWeeklyKm", "150"));
        String carType = extractValue(jsonContent, "carType", "petrol");
        double electricityMonthlyKwh = Double.parseDouble(
            extractValue(jsonContent, "electricityMonthlyKwh", "280"));
        int meatMealsPerWeek = Integer.parseInt(
            extractValue(jsonContent, "meatMealsPerWeek", "7"));
        double recyclingPercent = Double.parseDouble(
            extractValue(jsonContent, "recyclingPercent", "50"));
        
        return new UserProfile(name, carWeeklyKm, carType, 
                              electricityMonthlyKwh, meatMealsPerWeek, recyclingPercent);
    }
    
    private static String extractValue(String json, String key) {
        return extractValue(json, key, "");
    }
    
    private static String extractValue(String json, String key, String defaultValue) {
        String pattern = "\"" + key + "\":\\s*\"?([^,\"}]+)\"?";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = p.matcher(json);
        
        if (m.find()) {
            return m.group(1).replace("\"", "").trim();
        }
        return defaultValue;
    }
}