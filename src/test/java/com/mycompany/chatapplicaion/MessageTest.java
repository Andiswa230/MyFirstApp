/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapplicaion;

import com.mycompany.chatapplication.Message;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
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

    // Message Hash

    @Test
    public void testCreateMessageHash() {
        Message msg = new Message("+27831234567", "Hello World");
        String hash = msg.createMessageHash(0);

        assertNotNull(hash);
        assertFalse(hash.isEmpty());
    }

    // Send Message

    @Test
    public void testSendMessage() {
        Message msg = new Message("+27831234567", "Test Message");

        String result = msg.SentMessage(1, "Andiswa");

        assertEquals("Message successfully sent", result);
    }

    // Store Message

    @Test
    public void testStoreMessage() {
        Message msg = new Message("+27831234567", "Stored Message");

        String result = msg.SentMessage(3, "Andiswa");

        assertEquals("Message successfully stored", result);
    }

    // Disregard Message

    @Test
    public void testDisregardMessage() {
        Message msg = new Message("+27831234567", "Ignore");

        String result = msg.SentMessage(2, "Andiswa");

        assertEquals("Message disregarded", result);
    }

    // Invalid Choice

    @Test
    public void testInvalidChoice() {
        Message msg = new Message("+27831234567", "Hello");

        String result = msg.SentMessage(5, "Andiswa");

        assertEquals("Invalid choice.", result);
    }

    // Total Messages

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

    // Delete Hash Not Found

    @Test
    public void testDeleteHashNotFound() {

        Message msg = new Message();

        String result = msg.deleteMessageByHash("INVALID_HASH");

        assertTrue(result.contains("not found"));
    }

    // Delete Stored Message

    @Test
    public void testDeleteStoredMessage() {

        Message msg = new Message(
                "+27831234567",
                "Delete this message");

        msg.SentMessage(3, "Andiswa");

        String hash = msg.createMessageHash(
                msg.returnTotalMessagess() - 1);

        String result = msg.deleteMessageByHash(hash);

        assertTrue(result.contains("removed"));
    }

    // Search By Recipient

    @Test
    public void testSearchStoredByRecipient() {

        Message msg = new Message(
                "+27831234567",
                "Recipient Search Test");

        msg.SentMessage(3, "Andiswa");

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        System.setOut(new PrintStream(output));

        Message search = new Message();

        search.searchStoredByRecipient("+27831234567");

        String result = output.toString();

        assertTrue(result.contains("Recipient Search Test"));
    }

    // Search By Message ID

    @Test
    public void testSearchByMessageId() {

        Message msg = new Message(
                "+27831234567",
                "Search By ID Test");

        msg.SentMessage(1, "Andiswa");

        String id = msg.getMessageId();

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        System.setOut(new PrintStream(output));

        Message search = new Message();

        search.searchByMessageId(id);

        String result = output.toString();

        assertTrue(result.contains(id));
    }

    // Display Longest Message

    @Test
    public void testDisplayLongestStoredMessage() {

        Message msg1 = new Message(
                "+27831234567",
                "Short");

        msg1.SentMessage(3, "User");

        Message msg2 = new Message(
                "+27831234567",
                "This is the longest stored message");

        msg2.SentMessage(3, "User");

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        System.setOut(new PrintStream(output));

        Message report = new Message();

        report.displayLongestStoredMessage();

        String result = output.toString();

        assertTrue(
            result.contains(
                "This is the longest stored message"));
    }

    // Display Stored Report

    @Test
    public void testDisplayStoredReport() {

        Message msg = new Message(
                "+27831234567",
                "Stored Report Test");

        msg.SentMessage(3, "Andiswa");

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        System.setOut(new PrintStream(output));

        Message report = new Message();

        report.displayStoredReport();

        String result = output.toString();

        assertTrue(result.contains("Stored Report Test"));
    }

    // Load JSON Messages

    @Test
    public void testLoadMessagesFromJsonFile()
            throws Exception {

        String json =
                "[{\"sender\":\"John\","
              + "\"recipient\":\"+27831234567\","
              + "\"message\":\"Hello JSON\"}]";

        Path tempFile =
                Files.createTempFile(
                        "messages",
                        ".json");

        Files.write(
                tempFile,
                json.getBytes());

        Message msg = new Message();

        int before =
                msg.returnTotalMessagess();

        msg.loadStoredMessagesFromJsonFile(
                tempFile.toString());

        int after =
                msg.returnTotalMessagess();

        assertTrue(after >= before);
    }

    // Delete And Verify Removal

    @Test
    public void testDeleteMessageAndVerifyArraysUpdated() {

        Message msg = new Message(
                "+27831234567",
                "Delete Verification Message");

        msg.SentMessage(3, "Andiswa");

        String hash = msg.createMessageHash(
                msg.returnTotalMessagess() - 1);

        String deleteResult =
                msg.deleteMessageByHash(hash);

        assertTrue(
                deleteResult.contains("removed"));

        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        System.setOut(new PrintStream(output));

        msg.searchStoredByRecipient(
                "+27831234567");

        String result =
                output.toString();

        assertFalse(
                result.contains(
                        "Delete Verification Message"));
    }
}
