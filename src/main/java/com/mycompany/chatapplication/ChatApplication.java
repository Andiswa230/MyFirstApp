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
        Message messageManager = new Message();

        // Load JSON file elements safely into memory initialization
        messageManager.loadStoredMessagesFromJsonFile("stored.json");

        String username = "";
        String password = "";
        String phone = "";

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
                        username = scanner.nextLine();
                        if (Login.isValidUsername(username)) {
                            System.out.println("Username successfully captured.");
                            break;
                        } else {
                            System.out.println("Username is invalid. It must contain an underscore (_) and be max 5 chars.");
                        }
                    }

                    while (true) {
                        System.out.print("Enter password: ");
                        password = scanner.nextLine();
                        if (Login.isValidPassword(password)) {
                            System.out.println("Password successfully captured.");
                            break;
                        } else {
                            System.out.println("Password invalid. Requires 8+ chars, Uppercase, Number, Special character.");
                        }
                    }

                    while (true) {
                        System.out.print("Enter SA phone number (+27...): ");
                        phone = scanner.nextLine();
                        if (Login.isValidPhoneNumber(phone)) {
                            System.out.println("Cell phone number successfully added.");
                            break;
                        } else {
                            System.out.println("Invalid format. Must match '+27' followed by 9 digits.");
                        }
                    }

                    System.out.println(ln.registerUser(username, password, phone));
                    break;

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
                    }

                    if (!loggedIn) {
                        System.out.println("Login failed. Too many incorrect attempts.");
                        break;
                    }

                    System.out.println("\nWelcome to QuickChat.");

                    boolean messageMenu = true;
                    while (messageMenu) {
                        System.out.println("\n===== MESSAGE MENU =====");
                        System.out.println("1. Send / Capture Messages");
                        System.out.println("2. Logout");
                        System.out.print("Choose an option: ");

                        int option2 = scanner.nextInt();
                        scanner.nextLine();

                        if (option2 == 1) {
                            System.out.print("How many messages would you like to enter? ");
                            int messageCount = scanner.nextInt();
                            scanner.nextLine();

                            for (int i = 1; i <= messageCount; i++) {
                                System.out.println("\n--- Message Entry " + i + " ---");
                                String recipientPhone;

                                while (true) {
                                    System.out.print("Enter recipient phone number (+27...): ");
                                    recipientPhone = scanner.nextLine();
                                    if (messageManager.checkRecipientCell(recipientPhone)) {
                                        break;
                                    }
                                    System.out.println("Invalid recipient number format.");
                                }

                                String messageBody;
                                while (true) {
                                    System.out.print("Enter message description: ");
                                    messageBody = scanner.nextLine();
                                    if (messageBody.length() <= 250) {
                                        break;
                                    } else {
                                        System.out.println("Max 250 characters allowed.");
                                    }
                                }

                                Message currentMsg = new Message(recipientPhone, messageBody);

                                System.out.println("\nWhat would you like to do with this message?");
                                System.out.println("1) Send Message\n2) Disregard Message\n3) Store Message to send later");
                                System.out.print("Selection: ");
                                int deliveryChoice = scanner.nextInt();
                                scanner.nextLine();

                                String actionStatus = currentMsg.SentMessage(deliveryChoice, username);
                                System.out.println(actionStatus);
                            }
                            System.out.println("\nTotal messages tracked globally: " + messageManager.returnTotalMessagess());
                        } else if (option2 == 2) {
                            System.out.println("Logging out...");
                            messageMenu = false;
                        } else {
                            System.out.println("Invalid choice.");
                        }
                    }
                    break;

                case 3:
                    // ================= STORED MESSAGES DASHBOARD (REQ 2 SUBMENU) =================
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
    
         
    
