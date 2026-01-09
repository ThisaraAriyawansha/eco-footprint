package service;

import java.lang.reflect.*;

public class ReflectionDemo {
    
    public void demonstrateReflection() {
        try {
            System.out.println("=== USING REFLECTION ===");
            
            // Get CarbonFootprint class
            Class<?> footprintClass = Class.forName("model.CarbonFootprint");
            
            System.out.println("Inspecting CarbonCalculator class...");
            System.out.println("Class Name: " + footprintClass.getSimpleName());
            
            // Get all methods
            System.out.println("\nFound methods:");
            Method[] methods = footprintClass.getDeclaredMethods();
            for (Method method : methods) {
                System.out.println("- " + method.getName() + 
                    "() returns " + method.getReturnType().getSimpleName());
            }
            
            // Get all fields
            System.out.println("\nFound fields:");
            Field[] fields = footprintClass.getDeclaredFields();
            for (Field field : fields) {
                System.out.println("- " + field.getName() + 
                    " (" + field.getType().getSimpleName() + ")");
            }
            
            // Create instance using reflection
            System.out.println("\nCreating instance via reflection...");
            Constructor<?> constructor = footprintClass.getConstructor();
            Object footprintInstance = constructor.newInstance();
            
            // Invoke method dynamically
            Method totalMethod = footprintClass.getMethod("getTotalEmissions");
            Object result = totalMethod.invoke(footprintInstance);
            System.out.println("Invoked getTotalEmissions(): " + result);
            
            // Check annotations
            System.out.println("\nChecking for @Override annotations...");
            for (Method method : methods) {
                if (method.isAnnotationPresent(Override.class)) {
                    System.out.println(method.getName() + " has @Override annotation");
                }
            }
            
        } catch (Exception e) {
            System.out.println("Reflection error: " + e.getMessage());
        }
    }
}