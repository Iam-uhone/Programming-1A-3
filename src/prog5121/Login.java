/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog5121;

// Modified Login class with access to stored credentials

import java.util.regex.Matcher;
import java.util.regex.Pattern;

    class Login {
        /**
         * Checks if username meets requirements: contains underscore and is no more than 5 characters
         */
        public boolean checkUserName(String username) {
            return username.contains("_") && username.length() <= 5;
        }
        
        /**
         * Checks if password meets complexity requirements:
         * - At least 8 characters long
         * - Contains a capital letter
         * - Contains a number
         * - Contains a special character
         */
        public boolean checkPasswordComplexity(String password) {
            if (password.length() < 8) return false;
            
            boolean hasCapital = false;
            boolean hasNumber = false;
            boolean hasSpecial = false;
            
            for (char c : password.toCharArray()) {
                if (Character.isUpperCase(c)) hasCapital = true;
                if (Character.isDigit(c)) hasNumber = true;
                if (!Character.isLetterOrDigit(c)) hasSpecial = true;
            }
            
            return hasCapital && hasNumber && hasSpecial;
        }
        
        /**
         * Checks if cell phone number meets requirements:
         * - Contains international country code for South Africa (+27)
         * - Number is no more than 10 digits long (excluding country code)
         * 
         * This regular expression was created with assistance from ChatGPT (OpenAI, 2023)
         * Reference: OpenAI. (2023). ChatGPT (September 25 version) [Large language model]. 
         * https://chat.openai.com/chat
         */
        public boolean checkCellPhoneNumber(String phone) {
            // Regex pattern for South African phone numbers with country code
            // Pattern: +27 followed by 9 digits (total 12 characters including +)
            String regex = "^\\+27\\d{9}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(phone);
            
            return matcher.matches();
        }
        
        /**
         * Registers a user after validating all inputs
         * Returns appropriate message based on validation results
         */
        public String registerUser(String username, String password, String phone) {
            boolean usernameValid = checkUserName(username);
            boolean passwordValid = checkPasswordComplexity(password);
            boolean phoneValid = checkCellPhoneNumber(phone);
            
            if (!usernameValid) {
                return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
            }
            
            if (!passwordValid) {
                return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
            }
            
            if (!phoneValid) {
                return "Cell phone number incorrectly formatted or does not contain international code.";
            }
            
            return "Registration successful!";
        }
        
        /**
         * Returns appropriate login status message
         */
        public String returnLoginStatus(boolean isSuccessful, String firstName, String lastName) {
            if (isSuccessful) {
                return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
            } else {
                return "Username or password incorrect, please try again.";
            }
        }
    }
