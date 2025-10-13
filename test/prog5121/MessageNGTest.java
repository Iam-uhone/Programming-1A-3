/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package prog5121;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;

/**
 *
 * @author RC_Student_Lab
 */
public class MessageNGTest {
    
    private Message message;
    
    // Test Data 
    private static final String VALID_RECIPIENT_1 = "+27718693002";
    private static final String INVALID_RECIPIENT_2 = "08575975889";
    private static final String VALID_MESSAGE_1 = "Hi Mike, can you join us for dinner tonight";
    private static final String VALID_MESSAGE_2 = "Hi Keegan, did you receive the payment?";
    private static final String LONG_MESSAGE = "This is a very long message that exceeds the 250 character limit. "
            + "This is a very long message that exceeds the 250 character limit. "
            + "This is a very long message that exceeds the 250 character limit. "
            + "This is a very long message that exceeds the 250 character limit. "
            + "This is a very long message that exceeds the 250 character limit.";
    
    @BeforeMethod
    public void setUp() {
        message = new Message();
    }

    @Test
    public void testMessageHashCreation() {
        // Test Case 1: Message hash creation 
        String messageID = "0012345678"; // Sample message ID
        int messageNumber = 1;
        String messageContent = "Hi Mike, can you join us for dinner tonight";
        
        String messageHash = message.createMessageHash(messageID, messageNumber, messageContent);
        Assert.assertEquals(messageHash, "00:1:HITONIGHT");
        
        // Test Case 2: Another message hash
        messageID = "0098765432";
        messageNumber = 2;
        messageContent = "Hi Keegan, did you receive the payment?";
        
        messageHash = message.createMessageHash(messageID, messageNumber, messageContent);
        Assert.assertEquals(messageHash, "00:2:HIPAYMENT?");
    }
    
    @Test
    public void testTotalMessagesCount() {
        // Test total messages count functionality
        message.setMessageNumber(1);
        int total = message.returnTotalMessages();
        Assert.assertEquals(total, 1);
        
        message.setMessageNumber(2);
        total = message.returnTotalMessages();
        Assert.assertEquals(total, 2);
    }
    
    @Test
    public void testGenerateMessageID() {
        String messageID = message.generateMessageID();
        Assert.assertNotNull(messageID);
        Assert.assertEquals(messageID.length(), 10);
        Assert.assertTrue(messageID.matches("\\d{10}"));
    }

    @Test
    public void testCheckMessageID() {
        // Test valid message ID
        Assert.assertTrue(message.checkMessageID("1234567890"));
        
        // Test invalid message IDs
        Assert.assertFalse(message.checkMessageID("123")); // too short
        Assert.assertFalse(message.checkMessageID("12345678901")); // too long
        Assert.assertFalse(message.checkMessageID("abcdefghij")); // non-digit characters
    }

    @Test
    public void testCheckRecipientCell() {
        // Test valid recipient
        Assert.assertEquals(message.checkRecipientCell("+27718693002"), 1);
        
        // Test invalid recipients
        Assert.assertEquals(message.checkRecipientCell("08575975889"), -1); // wrong format
        Assert.assertEquals(message.checkRecipientCell("+2771869300"), -1); // too short
        Assert.assertEquals(message.checkRecipientCell("+277186930022"), -1); // too long
        Assert.assertEquals(message.checkRecipientCell("27718693002"), -1); // missing +
    }

    @Test
    public void testCreateMessageHash() {
        // Test various message hash creations
        String messageID = "0012345678";
        int messageNumber = 1;
        String messageContent = "Hello World";
        
        String hash = message.createMessageHash(messageID, messageNumber, messageContent);
        Assert.assertEquals(hash, "00:1:HELLOWORLD");
        
        // Test with single word message
        hash = message.createMessageHash(messageID, messageNumber, "Hello");
        Assert.assertEquals(hash, "00:1:HELLOHELLO");
        
        // Test with multiple words
        hash = message.createMessageHash(messageID, messageNumber, "This is a test message");
        Assert.assertEquals(hash, "00:1:THISMESSAGE");
    }

    @Test
    public void testReturnTotalMessages() {
        // Test initial state
        Assert.assertEquals(message.returnTotalMessages(), 0);
        
        // Test after setting message number
        message.setMessageNumber(5);
        Assert.assertEquals(message.returnTotalMessages(), 5);
    }

    @Test
    public void testSetAndGetMessageID() {
        message.setMessageID("1234567890");
        Assert.assertEquals(message.getMessageID(), "1234567890");
    }

    @Test
    public void testSetAndGetMessageNumber() {
        message.setMessageNumber(3);
        Assert.assertEquals(message.getMessageNumber(), 3);
    }

    @Test
    public void testSetAndGetRecipient() {
        message.setRecipient("+27718693002");
        Assert.assertEquals(message.getRecipient(), "+27718693002");
    }

    @Test
    public void testSetAndGetMessageContent() {
        message.setMessageContent("Test message content");
        Assert.assertEquals(message.getMessageContent(), "Test message content");
    }

    @Test
    public void testSetAndGetMessageHash() {
        message.setMessageHash("00:1:TESTHASH");
        Assert.assertEquals(message.getMessageHash(), "00:1:TESTHASH");
    }
    
    @Test
    public void testCompleteMessageFlow() {
        // Test a complete message flow with Test Case 1 data
        String messageID = message.generateMessageID();
        Assert.assertTrue(message.checkMessageID(messageID));
        
        int recipientCheck = message.checkRecipientCell(VALID_RECIPIENT_1);
        Assert.assertEquals(recipientCheck, 1);
        
       
    }
}

