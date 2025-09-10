/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog5121;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class RegistrationLoginSystem {
    
    private static String storedUsername = "";
    private static String storedPassword = "";
    private static String storedPhone = "";
    private static String storedFirstName = "";
    private static String storedLastName = "";
    private static boolean isRegistered = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login loginSystem = new Login();
        
        System.out.println("Registration and Login System");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Choose an option (1 or 2): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); 
        
        if (choice == 1) {
            System.out.println("Registration");                       
            System.out.print("Enter username: ");
            String username = scanner.nextLine();                      
            System.out.print("Enter password: ");
            String password = scanner.nextLine();                       
            System.out.print("Enter South African cell phone number (with country code): ");
            String phone = scanner.nextLine();           
            System.out.print("Enter your first name: ");
            storedFirstName = scanner.nextLine();           
            System.out.print("Enter your last name: ");
            storedLastName = scanner.nextLine();                       
            String registrationResult = loginSystem.registerUser(username, password, phone);
            System.out.println(registrationResult);
            
            
            if (registrationResult.equals("Registration successful!")) {
                storedUsername = username;
                storedPassword = password;
                storedPhone = phone;
                isRegistered = true;
                
                
                System.out.println("Would you like to login now? (yes/no)");
                String response = scanner.nextLine();
                
                if (response.equalsIgnoreCase("yes")) {
                    System.out.println("Login ");               
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    boolean loginSuccess = loginUsername.equals(storedUsername) && loginPassword.equals(storedPassword);
                    String loginStatus = loginSystem.returnLoginStatus(loginSuccess, storedFirstName, storedLastName);
                    System.out.println(loginStatus);
                }
            }
            
        } else if (choice == 2) {
            
            if (!isRegistered) {
                System.out.println("No user registered yet. Please register first.");
            } else {
                System.out.println("Login");                
                System.out.print("Enter username: ");
                String username = scanner.nextLine();                
                System.out.print("Enter password: ");
                String password = scanner.nextLine();                
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
    
    