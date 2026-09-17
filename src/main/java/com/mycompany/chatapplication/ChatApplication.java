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
}
         
    }
}