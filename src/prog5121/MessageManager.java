package prog5121;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class MessageManager {
    private ArrayList<Message> sentMessages;
    private ArrayList<Message> disregardedMessages;
    private ArrayList<Message> storedMessages;
    private ArrayList<String> messageHashes;
    private ArrayList<String> messageIDs;
    
    public MessageManager() {
        sentMessages = new ArrayList<>();
        disregardedMessages = new ArrayList<>();
        storedMessages = new ArrayList<>();
        messageHashes = new ArrayList<>();
        messageIDs = new ArrayList<>();
        loadStoredMessages();
    }
    
    private void loadStoredMessages() {
        try {
            java.io.File file = new java.io.File("messages.json");
            if (file.exists()) {
                String content = new String(Files.readAllBytes(file.toPath()));
                JSONArray messagesArray = new JSONArray(content);
                
                for (int i = 0; i < messagesArray.length(); i++) {
                    JSONObject messageJson = messagesArray.getJSONObject(i);
                    Message message = new Message();
                    message.setMessageID(messageJson.getString("messageID"));
                    message.setMessageNumber(messageJson.getInt("messageNumber"));
                    message.setRecipient(messageJson.getString("recipient"));
                    message.setMessageContent(messageJson.getString("messageContent"));
                    message.setMessageHash(messageJson.getString("messageHash"));
                    
                    storedMessages.add(message);
                    messageHashes.add(message.getMessageHash());
                    messageIDs.add(message.getMessageID());
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading stored messages: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing JSON file: " + e.getMessage());
        }
    }
    
    public void addMessage(Message message, String status) {
        switch (status.toLowerCase()) {
            case "sent":
                sentMessages.add(message);
                break;
            case "disregarded":
                disregardedMessages.add(message);
                break;
            case "stored":
                storedMessages.add(message);
                break;
        }
        
        if (message.getMessageHash() != null) {
            messageHashes.add(message.getMessageHash());
        }
        if (message.getMessageID() != null) {
            messageIDs.add(message.getMessageID());
        }
    }
    
    public void displaySentMessageSendersRecipients() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages found.", "Sent Messages", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== Senders and Recipients of All Sent Messages ===\n\n");
        
        for (Message message : sentMessages) {
            String sender = PROG5121.getStoredFirstName() + " " + PROG5121.getStoredLastName();
            sb.append("Sender: ").append(sender).append("\n");
            sb.append("Recipient: ").append(message.getRecipient()).append("\n");
            sb.append("------------------------\n");
        }
        
        JOptionPane.showMessageDialog(null, sb.toString(), "Sent Messages - Senders & Recipients", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void displayLongestSentMessage() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages found.", "Longest Message", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Message longestMessage = sentMessages.get(0);
        for (Message message : sentMessages) {
            if (message.getMessageContent().length() > longestMessage.getMessageContent().length()) {
                longestMessage = message;
            }
        }
        
        String result = "=== Longest Sent Message ===\n\n" +
                       "Message ID: " + longestMessage.getMessageID() + "\n" +
                       "Recipient: " + longestMessage.getRecipient() + "\n" +
                       "Message Length: " + longestMessage.getMessageContent().length() + " characters\n" +
                       "Message: " + longestMessage.getMessageContent() + "\n" +
                       "Message Hash: " + longestMessage.getMessageHash();
        
        JOptionPane.showMessageDialog(null, result, "Longest Sent Message", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void searchMessageByID() {
        String searchID = JOptionPane.showInputDialog("Enter Message ID to search for:");
        if (searchID == null || searchID.trim().isEmpty()) {
            return;
        }
        
        Message foundMessage = null;
        
        for (Message message : sentMessages) {
            if (message.getMessageID().equals(searchID)) {
                foundMessage = message;
                break;
            }
        }
        
        if (foundMessage == null) {
            for (Message message : storedMessages) {
                if (message.getMessageID().equals(searchID)) {
                    foundMessage = message;
                    break;
                }
            }
        }
        
        if (foundMessage == null) {
            for (Message message : disregardedMessages) {
                if (message.getMessageID().equals(searchID)) {
                    foundMessage = message;
                    break;
                }
            }
        }
        
        if (foundMessage != null) {
            String result = "=== Message Found ===\n\n" +
                           "Message ID: " + foundMessage.getMessageID() + "\n" +
                           "Recipient: " + foundMessage.getRecipient() + "\n" +
                           "Message: " + foundMessage.getMessageContent() + "\n" +
                           "Status: " + (sentMessages.contains(foundMessage) ? "Sent" : 
                                       storedMessages.contains(foundMessage) ? "Stored" : "Disregarded");
            
            JOptionPane.showMessageDialog(null, result, "Message Search Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No message found with ID: " + searchID, "Message Not Found", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void searchMessagesByRecipient() {
        String recipient = JOptionPane.showInputDialog("Enter recipient phone number to search for:");
        if (recipient == null || recipient.trim().isEmpty()) {
            return;
        }
        
        ArrayList<Message> matchingMessages = new ArrayList<>();
        
        for (Message message : sentMessages) {
            if (message.getRecipient().equals(recipient)) {
                matchingMessages.add(message);
            }
        }
        
        if (matchingMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages found for recipient: " + recipient, "No Messages Found", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== Messages for Recipient: ").append(recipient).append(" ===\n\n");
        
        for (Message message : matchingMessages) {
            sb.append("Message ID: ").append(message.getMessageID()).append("\n");
            sb.append("Message: ").append(message.getMessageContent()).append("\n");
            sb.append("Message Hash: ").append(message.getMessageHash()).append("\n");
            sb.append("------------------------\n");
        }
        
        JOptionPane.showMessageDialog(null, sb.toString(), "Messages by Recipient", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void deleteMessageByHash() {
        String hashToDelete = JOptionPane.showInputDialog("Enter Message Hash to delete:");
        if (hashToDelete == null || hashToDelete.trim().isEmpty()) {
            return;
        }
        
        boolean found = false;
        
        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).getMessageHash().equals(hashToDelete)) {
                sentMessages.remove(i);
                found = true;
                break;
            }
        }
        
        if (!found) {
            for (int i = 0; i < storedMessages.size(); i++) {
                if (storedMessages.get(i).getMessageHash().equals(hashToDelete)) {
                    storedMessages.remove(i);
                    found = true;
                    break;
                }
            }
        }
        
        if (!found) {
            for (int i = 0; i < disregardedMessages.size(); i++) {
                if (disregardedMessages.get(i).getMessageHash().equals(hashToDelete)) {
                    disregardedMessages.remove(i);
                    found = true;
                    break;
                }
            }
        }
        
        messageHashes.remove(hashToDelete);
        
        if (found) {
            JOptionPane.showMessageDialog(null, "Message with hash '" + hashToDelete + "' has been deleted.", "Message Deleted", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No message found with hash: " + hashToDelete, "Message Not Found", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void displaySentMessagesReport() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages found.", "Sent Messages Report", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== FULL SENT MESSAGES REPORT ===\n\n");
        
        for (Message message : sentMessages) {
            String sender = PROG5121.getStoredFirstName() + " " + PROG5121.getStoredLastName();
            sb.append("Message ID: ").append(message.getMessageID()).append("\n");
            sb.append("Sender: ").append(sender).append("\n");
            sb.append("Recipient: ").append(message.getRecipient()).append("\n");
            sb.append("Message: ").append(message.getMessageContent()).append("\n");
            sb.append("Message Hash: ").append(message.getMessageHash()).append("\n");
            sb.append("Message Number: ").append(message.getMessageNumber()).append("\n");
            sb.append("====================================\n\n");
        }
        
        sb.append("Total Sent Messages: ").append(sentMessages.size());
        
        JOptionPane.showMessageDialog(null, sb.toString(), "Full Sent Messages Report", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public ArrayList<Message> getSentMessages() { return sentMessages; }
    public ArrayList<Message> getDisregardedMessages() { return disregardedMessages; }
    public ArrayList<Message> getStoredMessages() { return storedMessages; }
    public ArrayList<String> getMessageHashes() { return messageHashes; }
    public ArrayList<String> getMessageIDs() { return messageIDs; }
}