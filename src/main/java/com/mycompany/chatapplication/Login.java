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
    private String Firstname;
    private String Lastname;
    
    
    public boolean CheckUsername(String Username){
        return Username != null && Username.contains("_")&& Username.length()<= 5;
    
    }
    
    public boolean CheckPassword(String Password){
       if (Password == null || Password.length() < 8)return false;
       
       boolean hasUpper = !Password.equals(Password.toLowerCase());
       boolean hasDigit = Password.matches(".*\\d.*");
       boolean hasSpecial = Pattern.compile("^a-zA-Z0-9").matcher(Password).find();
       
       return hasUpper && hasDigit && hasSpecial;
    
    }
    public boolean chechCellPhoneNumber(String cellNumber){
        if (cellNumber == null) return false;
        return cellNumber.matches("^\\+27\\d{9}$");
    }
    
    
}
