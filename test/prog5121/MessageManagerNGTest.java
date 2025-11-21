package prog5121;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import java.util.ArrayList;

public class MessageManagerNGTest {
    
    private MessageManager messageManager;
    private Message testMessage1;
    private Message testMessage2;
    private Message testMessage4;
    private Message testMessage5;
    
    @BeforeMethod
    public void setUp() {
        messageManager = new MessageManager();
        
        testMessage1 = new Message();
        testMessage1.setMessageID("1000000001");
        testMessage1.setRecipient("+27834557896");
        testMessage1.setMessageContent("Did you get the cake?");
        testMessage1.setMessageHash("10:1:Didcake?");
        
        testMessage2 = new Message();
        testMessage2.setMessageID("1000000002");
        testMessage2.setRecipient("+27838884567");
        testMessage2.setMessageContent("Where are you? You are late! I have asked you to be on time.");
        testMessage2.setMessageHash("10:2:Wheretime.");
        
        testMessage4 = new Message();
        testMessage4.setMessageID("1000000004");
        testMessage4.setRecipient("0838884567");
        testMessage4.setMessageContent("It is dinner time!");
        testMessage4.setMessageHash("10:4:Ittime!");
        
        testMessage5 = new Message();
        testMessage5.setMessageID("1000000005");
        testMessage5.setRecipient("+27838884567");
        testMessage5.setMessageContent("Ok, I am leaving without you.");
        testMessage5.setMessageHash("10:5:Okyou.");
        
        messageManager.addMessage(testMessage1, "sent");
        messageManager.addMessage(testMessage2, "stored");
        messageManager.addMessage(testMessage4, "sent");
        messageManager.addMessage(testMessage5, "stored");
    }
    
    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        ArrayList<Message> sentMessages = messageManager.getSentMessages();
        
        assertEquals(sentMessages.size(), 2);
        
        boolean foundMessage1 = false;
        boolean foundMessage4 = false;
        
        for (Message message : sentMessages) {
            if ("Did you get the cake?".equals(message.getMessageContent())) {
                foundMessage1 = true;
            }
            if ("It is dinner time!".equals(message.getMessageContent())) {
                foundMessage4 = true;
            }
        }
        
        assertTrue(foundMessage1);
        assertTrue(foundMessage4);
    }
    
    @Test
    public void testDisplayLongestMessage() {
        messageManager.getSentMessages().clear();
        messageManager.getSentMessages().add(testMessage1);
        messageManager.getSentMessages().add(testMessage2);
        messageManager.getSentMessages().add(testMessage4);
        
        Message longestManual = testMessage1;
        for (Message msg : messageManager.getSentMessages()) {
            if (msg.getMessageContent().length() > longestManual.getMessageContent().length()) {
                longestManual = msg;
            }
        }
        
        assertEquals(longestManual, testMessage2);
        assertEquals(longestManual.getMessageContent(), "Where are you? You are late! I have asked you to be on time.");
    }
    
    @Test
    public void testSearchForMessageID() {
        String targetMessageID = "1000000004";
        Message foundMessage = null;
        
        for (Message msg : messageManager.getSentMessages()) {
            if (targetMessageID.equals(msg.getMessageID())) {
                foundMessage = msg;
                break;
            }
        }
        
        assertNotNull(foundMessage);
        assertEquals(foundMessage.getRecipient(), "0838884567");
        assertEquals(foundMessage.getMessageContent(), "It is dinner time!");
    }
    
    @Test
    public void testSearchMessagesByRecipient() {
        String targetRecipient = "+27838884567";
        ArrayList<Message> matchingMessages = new ArrayList<>();
        
        for (Message msg : messageManager.getSentMessages()) {
            if (targetRecipient.equals(msg.getRecipient())) {
                matchingMessages.add(msg);
            }
        }
        
        for (Message msg : messageManager.getStoredMessages()) {
            if (targetRecipient.equals(msg.getRecipient())) {
                matchingMessages.add(msg);
            }
        }
        
        assertEquals(matchingMessages.size(), 2);
        
        boolean foundMessage2 = false;
        boolean foundMessage5 = false;
        
        for (Message msg : matchingMessages) {
            if ("Where are you? You are late! I have asked you to be on time.".equals(msg.getMessageContent())) {
                foundMessage2 = true;
            }
            if ("Ok, I am leaving without you.".equals(msg.getMessageContent())) {
                foundMessage5 = true;
            }
        }
        
        assertTrue(foundMessage2);
        assertTrue(foundMessage5);
    }
    
    @Test
    public void testDeleteMessageByHash() {
        String hashToDelete = "10:2:Wheretime.";
        
        boolean messageExistsBefore = false;
        for (Message msg : messageManager.getStoredMessages()) {
            if (hashToDelete.equals(msg.getMessageHash())) {
                messageExistsBefore = true;
                break;
            }
        }
        assertTrue(messageExistsBefore);
        
        boolean deleted = false;
        ArrayList<Message> storedMessages = messageManager.getStoredMessages();
        for (int i = 0; i < storedMessages.size(); i++) {
            if (hashToDelete.equals(storedMessages.get(i).getMessageHash())) {
                storedMessages.remove(i);
                deleted = true;
                break;
            }
        }
        
        messageManager.getMessageHashes().remove(hashToDelete);
        
        assertTrue(deleted);
        
        boolean messageExistsAfter = false;
        for (Message msg : messageManager.getStoredMessages()) {
            if (hashToDelete.equals(msg.getMessageHash())) {
                messageExistsAfter = true;
                break;
            }
        }
        assertFalse(messageExistsAfter);
    }
    //LAST TEST
    @Test
    public void testDisplayReport() {
        ArrayList<Message> sentMessages = messageManager.getSentMessages();
        
        for (Message message : sentMessages) {
            assertNotNull(message.getMessageHash());
            assertNotNull(message.getRecipient());
            assertNotNull(message.getMessageContent());
            
            assertFalse(message.getMessageHash().isEmpty());
            assertFalse(message.getRecipient().isEmpty());
            assertFalse(message.getMessageContent().isEmpty());
        }
        
        assertEquals(sentMessages.size(), 2);
    }
}