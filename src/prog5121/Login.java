/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog5121;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

    class Login {       
        public boolean checkUserName(String username) {
            return username.contains("_") && username.length() <= 5;
        }
               
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
              
        public boolean checkCellPhoneNumber(String phone) {
            // Regex pattern for South African phone numbers with country code
            
            String regex = "^\\+27\\d{9}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(phone);
            
            return matcher.matches();
        }
              
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
        
        
        public String returnLoginStatus(boolean isSuccessful, String firstName, String lastName) {
            if (isSuccessful) {
                return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
            } else {
                return "Username or password incorrect, please try again.";
            }
        }
    }
