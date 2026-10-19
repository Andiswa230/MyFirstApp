/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapplication;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author Student
 */
public class Message {
     // Arrays required explicitly by the assignment instructions
    private static final String[] sentMessagesArray = new String[100];
    private static final String[] disregardedMessagesArray = new String[100];
    private static final String[] storedMessagesArray = new String[100];
    private static final String[] messageHashArray = new String[100];
    private static final String[] messageIdArray = new String[100];
   
    // To handle requirements 'a' (sender) and 'c/d' (recipient matching) smoothly
    private static final String[] storedRecipientsArray = new String[100];
    private static final String[] storedSendersArray = new String[100]; // Maps to the logged-in user

    private static int sentCount = 0;
    private static int disregardedCount = 0;
    private static int storedCount = 0;
    private static int totalGlobalCounter = 0; // Tracking for hashes

    private String messageId;
    private String recipient;
    private String messageBody;

    public Message() {}

    public Message(String recipient, String messageBody) {
        long randomTenDigit = (long) (Math.random() * 9000000000L) + 1000000000L;
        this.messageId = String.valueOf(randomTenDigit);
        this.recipient = recipient;
        this.messageBody = messageBody;
    }

    public String getMessageId() { return messageId; }
    public String getRecipient() { return recipient; }
    public String getMessageBody() { return messageBody; }

    public boolean checkRecipientCell(String cellNumber) {
        return cellNumber != null && cellNumber.startsWith("+27") && cellNumber.length() <= 12;
    }

    public String createMessageHash(int counterValue) {
        String idPrefix = this.messageId.substring(0, 2);
        String[] words = this.messageBody.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        return idPrefix + ":" + counterValue + ":" + (firstWord + lastWord).toUpperCase();
    }

}