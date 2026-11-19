/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapplicaion;

import com.mycompany.chatapplication.Login;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author Student
 */
public class LoginTest {
     private final Login login = new Login();

    // Username Tests
    @Test
    public void testValidUsername() {
        assertTrue(login.CheckUsername("kyl_1"));
    }

    @Test
    public void testInvalidUsername_NoUnderscore() {
        assertFalse(login.CheckUsername("kyle"));
    }

    @Test
    public void testInvalidUsername_TooLong() {
        assertFalse(login.CheckUsername("kyl_123"));
    }

    // Password Tests
    @Test
    public void testValidPassword() {
        assertTrue(login.CheckPassword("Password1!"));
    }

    @Test
    public void testInvalidPassword_NoCapital() {
        assertFalse(login.CheckPassword("password1!"));
    }

    @Test
    public void testInvalidPassword_NoNumber() {
        assertFalse(login.CheckPassword("Password!"));
    }

    @Test
    public void testInvalidPassword_NoSpecialCharacter() {
        assertFalse(login.CheckPassword("Password1"));
    }

    @Test
    public void testInvalidPassword_TooShort() {
        assertFalse(login.CheckPassword("Pass1!"));
    }

    // Cellphone Tests
    @Test
    public void testValidCellphoneNumber() {
        assertTrue(login.CheckCellPhoneNumber("+27831234567"));
    }

    @Test
    public void testInvalidCellphoneNumber() {
        assertFalse(login.CheckCellPhoneNumber("0831234567"));
    }

    // Registration Tests
    @Test
    public void testSuccessfulRegistration() {
        String result = login.registerUser(
                "abc_1",
                "Password1!",
                "+27831234567"
        );

        assertEquals("User registered successfully.", result);
    }

    @Test
    public void testRegistrationInvalidUsername() {
        String result = login.registerUser(
                "abcdef",
                "Password1!",
                "+27831234567"
        );

        assertEquals(
            "Username is not correctly formatted, please make sure that the username contains an underscore and is no more than five characters long.",
            result
        );
    }

    @Test
    public void testRegistrationInvalidPassword() {
        String result = login.registerUser(
                "abc_1",
                "pass",
                "+27831234567"
        );

        assertEquals(
            "Password is not correctly formatted, please make sure it contains at least eight characters, a capital letter, a number and a special character.",
            result
        );
    }

    @Test
    public void testRegistrationInvalidPhoneNumber() {
        String result = login.registerUser(
                "abc_1",
                "Password1!",
                "0831234567"
        );

        assertEquals(
            "Cellphone number is incorrect, please try again.",
            result
        );
    }

    // Login Tests
    @Test
    public void testSuccessfulLogin() {
        login.registerUser(
                "abc_1",
                "Password1!",
                "+27831234567"
        );

        assertTrue(login.loginUser("abc_1", "Password1!"));
    }

    @Test
    public void testFailedLogin() {
        login.registerUser(
                "abc_1",
                "Password1!",
                "+27831234567"
        );

        assertFalse(login.loginUser("abc_1", "WrongPassword"));
    }

    // Login Status Tests
    @Test
    public void testReturnLoginStatusSuccess() {
        login.registerUser(
                "abc_1",
                "Password1!",
                "+27831234567"
        );

        assertEquals(
                "Welcome abc_1, it is great to see you again.",
                login.returnLoginStatus(true)
        );
    }

    @Test
    public void testReturnLoginStatusFailure() {
        assertEquals(
                "Username or password incorrect, please try again.",
                login.returnLoginStatus(false)
        );
    }
}

