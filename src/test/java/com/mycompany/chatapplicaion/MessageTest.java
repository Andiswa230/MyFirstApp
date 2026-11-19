/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapplicaion;

import com.mycompany.chatapplication.Message;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Student
 */
public class MessageTest {
    
      @Test
    public void testValidRecipientCell() {
        Message msg = new Message("+27831234567", "Hello");
        assertTrue(msg.checkRecipientCell("+27831234567"));
    }

    @Test
    public void testInvalidRecipientCell() {
        Message msg = new Message("+27831234567", "Hello");
        assertFalse(msg.checkRecipientCell("0831234567"));
    }

    // Test message hash creation
    @Test
    public void testCreateMessageHash() {
        Message msg = new Message("+27831234567", "Hello World");
        String hash = msg.createMessageHash(0);

        assertNotNull(hash);
        assertTrue(hash.contains(":0:"));
        assertTrue(hash.endsWith("HELLOWORLD"));
    }

    // Test sending a message
    @Test
    public void testSendMessage() {
        Message msg = new Message("+27831234567", "Test message");

        String result = msg.SentMessage(1, "Andiswa");

        assertEquals("Message successfully sent", result);
    }

    // Test storing a message
    @Test
    public void testStoreMessage() {
        Message msg = new Message("+27831234567", "Stored message");

        String result = msg.SentMessage(3, "Andiswa");

        assertEquals("Message successfully stored", result);
    }

    // Test disregarding a message
    @Test
    public void testDisregardMessage() {
        Message msg = new Message("+27831234567", "Ignore this");

        String result = msg.SentMessage(2, "Andiswa");

        assertEquals("Message disregarded", result);
    }

    // Invalid menu choice
    @Test
    public void testInvalidChoice() {
        Message msg = new Message("+27831234567", "Hello");

        String result = msg.SentMessage(5, "Andiswa");

        assertEquals("Invalid choice.", result);
    }

    // Total messages counter
    @Test
    public void testTotalMessages() {

        Message msg1 = new Message("+27831234567", "Message One");
        Message msg2 = new Message("+27821234567", "Message Two");

        int before = msg1.returnTotalMessagess();

        msg1.SentMessage(1, "User");
        msg2.SentMessage(1, "User");

        int after = msg1.returnTotalMessagess();

        assertEquals(before + 2, after);
    }

    // Delete hash not found
    @Test
    public void testDeleteHashNotFound() {

        Message msg = new Message();

        String result = msg.deleteMessageByHash("INVALID_HASH");

        assertEquals(
            "Message Hash 'INVALID_HASH' not found.",
            result
        );
    }

    // Store then delete message
    @Test
    public void testDeleteStoredMessage() {

        Message msg = new Message("+27831234567", "Delete this message");

        msg.SentMessage(3, "Andiswa");

        String hash = msg.createMessageHash(
                msg.returnTotalMessagess() - 1
        );

        String result = msg.deleteMessageByHash(hash);

        assertTrue(result.contains("successfully removed"));
    }
}

