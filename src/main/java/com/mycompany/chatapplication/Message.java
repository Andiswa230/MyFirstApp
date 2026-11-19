/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapplication;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Student
 */
public class Message {
    private static final String[] sentMessagesArray = new String[100];
    private static final String[] disregardedMessagesArray = new String[100];
    private static final String[] storedMessagesArray = new String[100];
    private static final String[] messageHashArray = new String[100];
    private static final String[] messageIdArray = new String[100];

    private static final String[] storedRecipientsArray = new String[100];
    private static final String[] storedSendersArray = new String[100];
    private static final String[] storedHashArray = new String[100];
    private static final String[] storedIdArray = new String[100];

    private static int sentCount = 0;
    private static int disregardedCount = 0;
    private static int storedCount = 0;
    private static int totalGlobalCounter = 0; // number of entries in the global hash/id arrays
    private static int nextHashCounter = 0;

    private String messageId;
    private String recipient;
    private String messageBody;

    public Message() {}

    public Message(String recipient, String messageBody) {
        this.messageId = generateMessageId();
        this.recipient = recipient;
        this.messageBody = messageBody;
    }

    private static String generateMessageId() {
        long randomTenDigit = (long) (Math.random() * 9000000000L) + 1000000000L;
        return String.valueOf(randomTenDigit);
    }

    public String getMessageId() { return messageId; }
    public String getRecipient() { return recipient; }
    public String getMessageBody() { return messageBody; }

    // FIX 6: previously accepted "+27" followed by anything up to 12 chars (e.g. "+27abc").
    // Now uses the same rule as Login: +27 followed by exactly 9 digits.
    public boolean checkRecipientCell(String cellNumber) {
        return cellNumber != null && cellNumber.matches("^\\+27\\d{9}$");
    }

    private static String buildHash(String id, String body, int counterValue) {
        String idPrefix = id.substring(0, 2);
        String[] words = body.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        return idPrefix + ":" + counterValue + ":" + (firstWord + lastWord).toUpperCase();
    }

    public String createMessageHash(int counterValue) {
        return buildHash(this.messageId, this.messageBody, counterValue);
    }

    public String SentMessage(int choice, String currentUser) {
        if (totalGlobalCounter >= 100) {
            return "Storage full.";
        }
        if (choice < 1 || choice > 3) {
            return "Invalid choice.";
        }

        String computedHash = createMessageHash(nextHashCounter);

        switch (choice) {
            case 1: // Sent
                sentMessagesArray[sentCount] = this.messageBody;
                sentCount++;
                registerGlobal(computedHash, this.messageId);
                return "Message successfully sent";

            case 2: // Disregarded
                disregardedMessagesArray[disregardedCount] = this.messageBody;
                disregardedCount++;
                registerGlobal(computedHash, this.messageId);
                // FIX 7b: old text said "Press 0 to delete the message" but nothing
                // ever happened when 0 was pressed.
                return "Message disregarded";

            default: // 3: Stored
                addStored(this.messageBody, this.recipient, currentUser, computedHash, this.messageId);
                registerGlobal(computedHash, this.messageId);
                return "Message successfully stored";
        }
    }

    private static void registerGlobal(String hash, String id) {
        messageHashArray[totalGlobalCounter] = hash;
        messageIdArray[totalGlobalCounter] = id;
        totalGlobalCounter++;
        nextHashCounter++;
    }

    private static void addStored(String body, String recipient, String sender, String hash, String id) {
        storedMessagesArray[storedCount] = body;
        storedRecipientsArray[storedCount] = recipient;
        storedSendersArray[storedCount] = sender;
        storedHashArray[storedCount] = hash;
        storedIdArray[storedCount] = id;
        storedCount++;
    }

    public int returnTotalMessagess() {
        return totalGlobalCounter;
    }

    /**
     * FIX 4: previously this only opened the file and then inserted a hard-coded
     * mock message. It now really parses the JSON file. Expected format:
     * [ {"sender":"..","recipient":"+27..","message":"..","id":"..","hash":".."}, ... ]
     * "id" and "hash" are optional (they are generated if missing).
     */
    public void loadStoredMessagesFromJsonFile(String filePath) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            System.out.println("No '" + filePath + "' file found - starting with no stored messages.");
            return;
        }

        String json;
        try {
            json = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Could not read '" + filePath + "': " + e.getMessage());
            return;
        }

        Matcher objects = Pattern.compile("\\{[^{}]*\\}").matcher(json);
        int loaded = 0;
        while (objects.find()) {
            String obj = objects.group();
            String body = jsonField(obj, "message");
            String recipient = jsonField(obj, "recipient");
            String sender = jsonField(obj, "sender");
            if (body == null || body.trim().isEmpty() || recipient == null) {
                continue; // skip malformed entries
            }
            if (totalGlobalCounter >= 100) {
                System.out.println("Storage full - remaining JSON entries were not loaded.");
                break;
            }
            String id = jsonField(obj, "id");
            if (id == null || id.length() < 2) {
                id = generateMessageId();
            }
            String hash = jsonField(obj, "hash");
            if (hash == null || hash.isEmpty()) {
                hash = buildHash(id, body, nextHashCounter);
            }
            addStored(body, recipient, sender == null ? "Unknown" : sender, hash, id);
            registerGlobal(hash, id);
            loaded++;
        }
        System.out.println("Loaded " + loaded + " stored message(s) from " + filePath + ".");
    }

    // Reads a "key":"value" string field out of one JSON object (handles \" \\ \n escapes).
    private static String jsonField(String obj, String key) {
        Matcher m = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"").matcher(obj);
        if (!m.find()) return null;
        return m.group(1).replace("\\n", "\n").replace("\\\"", "\"").replace("\\\\", "\\");
    }

    // =========================================================================
    // STORED MESSAGES SUB-MENU LOGIC
    // =========================================================================

    // a. Display sender and recipient of all stored messages
    public void displayStoredSendersAndRecipients() {
        if (storedCount == 0) {
            System.out.println("No stored messages found.");
            return;
        }
        System.out.println("\n--- Stored Messages: Senders & Recipients ---");
        for (int i = 0; i < storedCount; i++) {
            System.out.println("Message [" + i + "] -> From: " + storedSendersArray[i] + " | To: " + storedRecipientsArray[i]);
        }
    }

    // b. Display the longest stored message
    public void displayLongestStoredMessage() {
        if (storedCount == 0) {
            System.out.println("No stored messages found.");
            return;
        }
        String longest = "";
        for (int i = 0; i < storedCount; i++) {
            if (storedMessagesArray[i].length() > longest.length()) {
                longest = storedMessagesArray[i];
            }
        }
        System.out.println("\nLongest Stored Message:\n\"" + longest + "\" (" + longest.length() + " characters)");
    }

    // c. Search for a message ID
    public void searchByMessageId(String targetId) {
        boolean found = false;
        for (int i = 0; i < totalGlobalCounter; i++) {
            if (messageIdArray[i] != null && messageIdArray[i].equals(targetId)) {
                System.out.println("\nMessage ID Found!");
                System.out.println("ID: " + targetId);
                System.out.println("Hash: " + messageHashArray[i]);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Message ID '" + targetId + "' not discovered.");
        }
    }

    // Search for all messages stored for a particular recipient
    public void searchStoredByRecipient(String targetRecipient) {
        boolean found = false;
        System.out.println("\n--- Stored Items for Recipient: " + targetRecipient + " ---");
        for (int i = 0; i < storedCount; i++) {
            if (storedRecipientsArray[i] != null && storedRecipientsArray[i].equals(targetRecipient)) {
                System.out.println("Msg: " + storedMessagesArray[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No stored messages found for this recipient.");
        }
    }

    public String deleteMessageByHash(String targetHash) {
        int storedIdx = -1;
        for (int i = 0; i < storedCount; i++) {
            if (storedHashArray[i] != null && storedHashArray[i].equalsIgnoreCase(targetHash)) {
                storedIdx = i;
                break;
            }
        }

        if (storedIdx == -1) {
            for (int i = 0; i < totalGlobalCounter; i++) {
                if (messageHashArray[i] != null && messageHashArray[i].equalsIgnoreCase(targetHash)) {
                    return "Message Hash '" + targetHash + "' belongs to a sent/disregarded message, not a stored one.";
                }
            }
            return "Message Hash '" + targetHash + "' not found.";
        }

        // Remove from the stored arrays (shift left)
        for (int i = storedIdx; i < storedCount - 1; i++) {
            storedMessagesArray[i] = storedMessagesArray[i + 1];
            storedRecipientsArray[i] = storedRecipientsArray[i + 1];
            storedSendersArray[i] = storedSendersArray[i + 1];
            storedHashArray[i] = storedHashArray[i + 1];
            storedIdArray[i] = storedIdArray[i + 1];
        }
        storedMessagesArray[storedCount - 1] = null;
        storedRecipientsArray[storedCount - 1] = null;
        storedSendersArray[storedCount - 1] = null;
        storedHashArray[storedCount - 1] = null;
        storedIdArray[storedCount - 1] = null;
        storedCount--;

        // Remove the same hash/id from the global arrays
        int globalIdx = -1;
        for (int i = 0; i < totalGlobalCounter; i++) {
            if (messageHashArray[i] != null && messageHashArray[i].equalsIgnoreCase(targetHash)) {
                globalIdx = i;
                break;
            }
        }
        if (globalIdx != -1) {
            for (int i = globalIdx; i < totalGlobalCounter - 1; i++) {
                messageHashArray[i] = messageHashArray[i + 1];
                messageIdArray[i] = messageIdArray[i + 1];
            }
            messageHashArray[totalGlobalCounter - 1] = null;
            messageIdArray[totalGlobalCounter - 1] = null;
            totalGlobalCounter--;
        }

        return "Message with Hash '" + targetHash + "' successfully removed.";
    }

    // f. Display a report that lists full details of all stored messages
    public void displayStoredReport() {
        if (storedCount == 0) {
            System.out.println("No stored items in log system.");
            return;
        }
        System.out.println("\n================ STORED MESSAGES REPORT ================");
        for (int i = 0; i < storedCount; i++) {
            System.out.println("From: " + storedSendersArray[i] + " | To: " + storedRecipientsArray[i]);
            System.out.println("ID: " + storedIdArray[i] + " | Hash: " + storedHashArray[i]);
            System.out.println("Content: " + storedMessagesArray[i]);
            System.out.println("-------------------------------------------------------");
        }
    }
}
