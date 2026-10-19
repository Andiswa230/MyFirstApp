/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapplication;

import java.util.Scanner;
/**
 *
 * @author Student
 */
public class ChatApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login ln = new Login();
        
        String Username = " ";
        String  Password = " ";
        String  Phone= "";
        
         while (true) {
            System.out.println("\n===== CHAT APPLICATION =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Stored Messages Dashboard (Req 4)");
            System.out.println("4. Quit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.println("\n===== REGISTRATION =====");
                    while (true) {
                        System.out.print("Enter username: ");
                        Username = scanner.nextLine();
                        if (Login.isValidUsername(Username)) {
                            System.out.println("Username successfully captured.");
                            break;
                        } else {
                            System.out.println("Username is invalid. It must contain an underscore (_) and be max 5 chars.");
                        }
                    }

                    while (true) {
                        System.out.print("Enter password: ");
                        Password = scanner.nextLine();
                        if (Login.isValidPassword(Password)) {
                            System.out.println("Password successfully captured.");
                            break;
                        } else {
                            System.out.println("Password invalid. Requires 8+ chars, Uppercase, Number, Special character.");
                        }
                    }

                    while (true) {
                        System.out.print("Enter SA phone number (+27...): ");
                        Phone = scanner.nextLine();
                        if (Login.isValidPhoneNumber(Phone)) {
                            System.out.println("Cell phone number successfully added.");
                            break;
                        } else {
                            System.out.println("Invalid format. Must match '+27' followed by 9 digits.");
                        }
                    }

                    System.out.println(ln.registerUser(Username, Password, Phone));
                    break;

    }
            
            
             switch (option){
                case 2:
                    System.out.println("\n===== LOGIN =====");
                    boolean loggedIn = false;
                    int attempts = 3;

                    while (attempts > 0 && !loggedIn) {
                        System.out.print("Enter username: ");
                        String loginUser = scanner.nextLine();

                        System.out.print("Enter password: ");
                        String loginPass = scanner.nextLine();

                        loggedIn = ln.loginUser(loginUser, loginPass);
                        System.out.println(ln.returnLoginStatus(loggedIn));

                        if (!loggedIn) {
                            attempts--;
                            System.out.println("Attempts remaining: " + attempts);
                        }
                        if (!loggedIn){
                        System.out.println("login failed. too many incorrect attempts");
                        break;
                        }
                        System.out.println("\nWelcome to QuickChat.");
                    case 3:
                    boolean storedDashboard = true;
                    while (storedDashboard) {
                        System.out.println("\n===== STORED MESSAGES DASHBOARD =====");
                        System.out.println("a. Display Sender and Recipient of all stored messages");
                        System.out.println("b. Display longest stored message");
                        System.out.println("c. Search for a message ID");
                        System.out.println("d. Search all messages for a particular recipient");
                        System.out.println("e. Delete a message using Message Hash");
                        System.out.println("f. Display full Stored Report");
                        System.out.println("g. Back to Main Menu");
                        System.out.print("Selection option letter: ");
                       
                        String selection = scanner.nextLine().trim().toLowerCase();
                        switch (selection) {
                            case "a":
                                messageManager.displayStoredSendersAndRecipients();
                                break;
                            case "b":
                                messageManager.displayLongestStoredMessage();
                                break;
                            case "c":
                                System.out.print("Enter Message ID to search: ");
                                String targetId = scanner.nextLine();
                                messageManager.searchByMessageId(targetId);
                                break;
                            case "d":
                                System.out.print("Enter Recipient cell number (+27...): ");
                                String targetRecip = scanner.nextLine();
                                messageManager.searchStoredByRecipient(targetRecip);
                                break;
                            case "e":
                                System.out.print("Enter exact Message Hash string to delete: ");
                                String targetHash = scanner.nextLine();
                                System.out.println(messageManager.deleteMessageByHash(targetHash));
                                break;
                            case "f":
                                messageManager.displayStoredReport();
                                break;
                            case "g":
                                storedDashboard = false;
                                break;
                            default:
                                System.out.println("Invalid selection option.");
                        }
                    }
                    break;

                case 4:
                    System.out.println("Thank you for using the Chat Application.");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");


             
                    }
         }
    }
}
}

         
    
