/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog5121;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Registration and Login System
 * 
 * This implementation uses regular expressions for validation, with the cell phone validation
 * regex created with assistance from ChatGPT (OpenAI, 2023).
 * 
 * Reference for AI-generated code:
 * OpenAI. (2023). ChatGPT (September 25 version) [Large language model]. 
 * https://chat.openai.com/chat
 */
public class RegistrationLoginSystem {
    // User details storage - made static to be accessible across methods
    private static String storedUsername = "";
    private static String storedPassword = "";
    private static String storedPhone = "";
    private static String storedFirstName = "";
    private static String storedLastName = "";
    private static boolean isRegistered = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login loginSystem = new Login();
        
        System.out.println("=== Registration and Login System ===");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Choose an option (1 or 2): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        if (choice == 1) {
            // Registration process
            System.out.println("\n=== Registration ===");
            
            // Get username
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            
            // Get password
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            
            // Get cell phone number
            System.out.print("Enter South African cell phone number (with country code): ");
            String phone = scanner.nextLine();
            
            // Get first and last name for greeting
            System.out.print("Enter your first name: ");
            storedFirstName = scanner.nextLine();
            
            System.out.print("Enter your last name: ");
            storedLastName = scanner.nextLine();
            
            // Validate and register user
            String registrationResult = loginSystem.registerUser(username, password, phone);
            System.out.println(registrationResult);
            
            // If registration was successful, store the credentials
            if (registrationResult.equals("Registration successful!")) {
                storedUsername = username;
                storedPassword = password;
                storedPhone = phone;
                isRegistered = true;
                
                // After successful registration, prompt for login
                System.out.println("\nWould you like to login now? (yes/no)");
                String response = scanner.nextLine();
                
                if (response.equalsIgnoreCase("yes")) {
                    // Login process
                    System.out.println("\n=== Login ===");
                    
                    // Get username
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();
                    
                    // Get password
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    
                    // Attempt login using the stored credentials
                    boolean loginSuccess = loginUsername.equals(storedUsername) && loginPassword.equals(storedPassword);
                    String loginStatus = loginSystem.returnLoginStatus(loginSuccess, storedFirstName, storedLastName);
                    System.out.println(loginStatus);
                }
            }
            
        } else if (choice == 2) {
            // Login process
            if (!isRegistered) {
                System.out.println("No user registered yet. Please register first.");
            } else {
                System.out.println("\n=== Login ===");
                
                // Get username
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                
                // Get password
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                
                // Attempt login using the stored credentials
                boolean loginSuccess = username.equals(storedUsername) && password.equals(storedPassword);
                String loginStatus = loginSystem.returnLoginStatus(loginSuccess, storedFirstName, storedLastName);
                System.out.println(loginStatus);
            }
        } else {
            System.out.println("Invalid option selected.");
        }
        
        scanner.close();
    }
}
    
    