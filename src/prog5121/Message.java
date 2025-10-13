/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package prog5121;


import javax.swing.JOptionPane;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageContent;
    private String messageHash;
    
    // Generate random 10-digit message ID
    public String generateMessageID() {
        Random random = new Random();
        long id = 1000000000L + random.nextInt(900000000);
        return String.valueOf(id);
    }
    
    public boolean checkMessageID(String messageID) {
        return messageID != null && messageID.length() == 10 && messageID.matches("\\d+");
    }
    
    public int checkRecipientCell(String recipient) {
        if (recipient == null || recipient.length() != 12 || !recipient.startsWith("+27")) {
            return -1;
        }
        
        String numberPart = recipient.substring(3);
        if (numberPart.matches("\\d{9}")) {
            return 1;
        } else {
            return -1;
        }
    }
    
    public String createMessageHash(String messageID, int messageNumber, String messageContent) {
        // Get first two digits of message ID
        String firstTwo = messageID.substring(0, 2);
        
        // Extract first and last words from message
        String[] words = messageContent.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        
        // Create hash in format: 00:0:HITHANKS
        String hash = firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }
    
    public String SentMessage() {
        String[] options = {"Send Message", "Disregard Message", "Store Message to send later"};
        int choice = JOptionPane.showOptionDialog(null,
            "Choose an option for this message:",
            "Message Options",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);
        
        switch (choice) {
            case 0:
                return "Message sent";
            case 1:
                return "Message disregarded";
            case 2:
                return "Message stored for later";
            default:
                return "Message disregarded";
        }
    }
    
    public void displayMessageDetails() {
        String details = "Message Details:\n\n" +
                        "Message ID: " + messageID + "\n" +
                        "Message Hash: " + messageHash + "\n" +
                        "Recipient: " + recipient + "\n" +
                        "Message: " + messageContent + "\n" +
                        "Message Number: " + messageNumber;
        
        JOptionPane.showMessageDialog(null, details, "Message Sent", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void storeMessage() {
        try {
            JSONObject messageJson = new JSONObject();
            messageJson.put("messageID", messageID);
            messageJson.put("messageNumber", messageNumber);
            messageJson.put("recipient", recipient);
            messageJson.put("messageContent", messageContent);
            messageJson.put("messageHash", messageHash);
            messageJson.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            
            
            JSONArray messagesArray;
            try {
                java.io.File file = new java.io.File("messages.json");
                if (file.exists()) {
                    String content = new String(java.nio.file.Files.readAllBytes(file.toPath()));
                    messagesArray = new JSONArray(content);
                } else {
                    messagesArray = new JSONArray();
                }
            } catch (Exception e) {
                messagesArray = new JSONArray();
            }
            
            // Add new message
            messagesArray.put(messageJson);
            
            // Write back to file
            try (FileWriter file = new FileWriter("messages.json")) {
                file.write(messagesArray.toString(4)); // 4 spaces for indentation
                file.flush();
            }
            
        } catch (IOException e) {
            System.out.println("Error storing message to JSON file: " + e.getMessage());
        }
    }
    
    public String printMessages() {
        StringBuilder sb = new StringBuilder();
        sb.append("All Messages:\n");
        
        sb.append("Message ID: ").append(messageID).append("\n");
        sb.append("Recipient: ").append(recipient).append("\n");
        sb.append("Message: ").append(messageContent).append("\n");
        return sb.toString();
    }
    
    public int returnTotalMessages() {
        return this.messageNumber;
    }
    
    // Getters and setters
    public String getMessageID() { return messageID; }
    public void setMessageID(String messageID) { this.messageID = messageID; }
    
    public int getMessageNumber() { return messageNumber; }
    public void setMessageNumber(int messageNumber) { this.messageNumber = messageNumber; }
    
    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    
    public String getMessageContent() { return messageContent; }
    public void setMessageContent(String messageContent) { 
        if (messageContent.length() > 250) {
            this.messageContent = messageContent.substring(0, 250);
        } else {
            this.messageContent = messageContent;
        }
    }
    
    public String getMessageHash() { return messageHash; }
    public void setMessageHash(String messageHash) { this.messageHash = messageHash; }


}