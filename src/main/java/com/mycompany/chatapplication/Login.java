/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapplication;
import java.util.regex.Pattern;
/**
 *
 * @author Student
 */
public class Login {
    private String registeredUsername;
    private String registeredPassword;

    public boolean CheckUsername(String Username) {
        return Username != null && Username.contains("_") && Username.length() <= 5;
    }

    public boolean CheckPassword(String Password) {
        if (Password == null || Password.length() < 8) return false;

        boolean hasUpper = Password.matches(".*[A-Z].*");
        boolean hasDigit = Password.matches(".*\\d.*");
        boolean hasSpecial = Pattern.compile("[^a-zA-Z0-9]").matcher(Password).find();

        return hasUpper && hasDigit && hasSpecial;
    }

    public boolean CheckCellPhoneNumber(String cellNumber) {
        if (cellNumber == null) return false;
        return cellNumber.matches("^\\+27\\d{9}$");
    }

    public String registerUser(String Username, String Password, String cellNumber) {
        if (!CheckUsername(Username)) {
            return "Username is not correctly formatted, please make sure that the username contains an underscore and is no more than five characters long.";
        }
        if (!CheckPassword(Password)) {
            return "Password is not correctly formatted, please make sure it contains at least eight characters, a capital letter, a number and a special character.";
        }
        if (!CheckCellPhoneNumber(cellNumber)) {
            return "Cellphone number is incorrect, please try again.";
        }

        this.registeredUsername = Username;
        this.registeredPassword = Password;
        return "User registered successfully.";
    }

    public boolean loginUser(String username, String password) {
        return registeredUsername != null
                && username.equals(this.registeredUsername)
                && password.equals(this.registeredPassword);
    }

    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + registeredUsername + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Username: must contain "_" and be <= 5 characters
    public static boolean isValidUsername(String Username) {
        return Username.contains("_") && Username.length() <= 5;
    }

    // Password: 8+ chars, capital letter, number, special character
    public static boolean isValidPassword(String password) {
        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^a-zA-Z0-9].*");
    }

    // Phone: must be +27 followed by 9 digits
    public static boolean isValidPhoneNumber(String phone) {
        return Pattern.matches("^\\+27\\d{9}$", phone);
    }
}
