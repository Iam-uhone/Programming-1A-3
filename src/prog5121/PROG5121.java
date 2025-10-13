/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package prog5121;

import javax.swing.JOptionPane;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author RC_Student_Lab
 */



public class PROG5121 {
    
    private static String storedUsername = "";
    private static String storedPassword = "";
    private static String storedPhone = "";
    private static String storedFirstName = "";
    private static String storedLastName = "";
    private static boolean isRegistered = false;
    private static boolean isLoggedIn = false;
    private static ArrayList<Message> messages = new ArrayList<>();
    private static int totalMessagesSent = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login loginSystem = new Login();
        
        System.out.println("Welcome.");
        
        while (true) {
            if (!isLoggedIn) {
                showLoginMenu(loginSystem, scanner);
            } else {
                showMainMenu(scanner);
            }
        }
    }
    
    private static void showLoginMenu(Login loginSystem, Scanner scanner) {
        System.out.println("\n=== Registration and Login System ===");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Choose an option (1-3): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); 
        
        switch (choice) {
            case 1:
                registerUser(loginSystem, scanner);
                break;
            case 2:
                loginUser(loginSystem, scanner);
                break;
            case 3:
                System.out.println(" Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option selected.");
        }
    }
    
    private static void registerUser(Login loginSystem, Scanner scanner) {
        System.out.println("\n=== Registration ==="); 
        System.out.print("Enter your first name: ");
        storedFirstName = scanner.nextLine();           
        System.out.print("Enter your last name: ");
        storedLastName = scanner.nextLine();  
        System.out.println();
        System.out.print("Enter username (must contain underscore and have no more than 5 characters): ");
        String username = scanner.nextLine();                      
        System.out.print("Enter password (must contain special character, a number and have a minimum of 8 characters): ");
        String password = scanner.nextLine();                       
        System.out.print("Enter cell phone number (+27xxxxxxxxx): ");
        String phone = scanner.nextLine();           
                             
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
                loginUser(loginSystem, scanner);
            }
        }
    }
    
    private static void loginUser(Login loginSystem, Scanner scanner) {
        if (!isRegistered) {
            System.out.println("No user registered yet. Please register first.");
            return;
        }
        
        System.out.println("\n=== Login ===");                
        System.out.print("Enter username: ");
        String username = scanner.nextLine();                
        System.out.print("Enter password: ");
        String password = scanner.nextLine();                
        boolean loginSuccess = username.equals(storedUsername) && password.equals(storedPassword);
        String loginStatus = loginSystem.returnLoginStatus(loginSuccess, storedFirstName, storedLastName);
        System.out.println(loginStatus);
        
        if (loginSuccess) {
            isLoggedIn = true;
        }
    }
    
    private static void showMainMenu(Scanner scanner) {
        while (isLoggedIn) {
            String[] options = {"1) Send Messages", "2) Show recently sent messages", "3) Quit"};
            int choice = JOptionPane.showOptionDialog(null,
                "=== QuickChat Main Menu ===\nPlease choose an option:",
                "QuickChat Main Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
            
            switch (choice) {
                case 0:
                    sendMessages();
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Coming Soon.", "Feature in Development", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Thank you for using QuickChat. Goodbye!");
                    isLoggedIn = false;
                    break;
                default:
                    // User closed the dialog
                    isLoggedIn = false;
            }
        }
    }
    
    private static void sendMessages() {
        String messageCountStr = JOptionPane.showInputDialog("How many messages do you wish to enter?");
        if (messageCountStr == null) return;
        
        int messageCount;
        try {
            messageCount = Integer.parseInt(messageCountStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            return;
        }
        
        int messagesEntered = 0;
        
        while (messagesEntered < messageCount) {
            JOptionPane.showMessageDialog(null, "=== Message " + (messagesEntered + 1) + " ===", 
                                         "New Message", JOptionPane.INFORMATION_MESSAGE);
            
            Message message = new Message();
            
            // Set message ID
            String messageId = message.generateMessageID();
            if (!message.checkMessageID(messageId)) {
                JOptionPane.showMessageDialog(null, "Error generating message ID.");
                continue;
            }
            
            // Get recipient
            String recipient = JOptionPane.showInputDialog("Enter recipient cell number:\n(+27xxxxxxxxx)");
            if (recipient == null) continue;
            
            int recipientCheck = message.checkRecipientCell(recipient);
            if (recipientCheck != 1) {
                JOptionPane.showMessageDialog(null, "Invalid recipient cell number.\nPlease ensure it starts with +27 and has 12 characters total.");
                continue;
            }
            
            // Get message content
            String messageContent = JOptionPane.showInputDialog("Enter your message (max 250 characters):");
            if (messageContent == null) continue;
            
            if (messageContent.length() > 250) {
                JOptionPane.showMessageDialog(null, "Please enter a message of less than 250 characters.");
                continue;
            }
            
            // Create and set message hash
            String messageHash = message.createMessageHash(messageId, messagesEntered + 1, messageContent);
            
            // Set all message properties
            message.setMessageID(messageId);
            message.setRecipient(recipient);
            message.setMessageContent(messageContent);
            message.setMessageHash(messageHash);
            message.setMessageNumber(messagesEntered + 1);
            
            // Show message options
            String result = message.SentMessage();
            JOptionPane.showMessageDialog(null, result, "Message Status", JOptionPane.INFORMATION_MESSAGE);
            
            if (result.equals("Message sent")) {
                messages.add(message);
                totalMessagesSent++;
                
                // Display message details using JOptionPane
                message.displayMessageDetails();
                
                // Store message in JSON
                message.storeMessage();
            }
            
            messagesEntered++;
        }
        
        JOptionPane.showMessageDialog(null, "Total messages sent: " + totalMessagesSent, 
                                     "Message Summary", JOptionPane.INFORMATION_MESSAGE);
    }
}