package org.example.Utilities;

import java.util.regex.Pattern;


public class ValidateUtil {

    public static boolean validateCredentials(String userName, String email) {
        if (userName != null && !userName.trim().isEmpty()) {
            return isValidUsername(userName);
        }
        if (email != null && !email.trim().isEmpty()) {
            return isValidEmail(email);
        }
        // If neither username nor email is provided
        return false;
    }

    // Username: only letters and numbers, 6 to 30 characters
    private static boolean isValidUsername(String username) {
        String regex = "^[A-Za-z0-9]{6,30}$";
        Pattern p = Pattern.compile(regex);
        if (username == null) return false;
        return p.matcher(username).matches();
    }

    // Email: standard email regex
    private static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern p = Pattern.compile(regex);
        if (email == null) return false;
        return p.matcher(email).matches();
    }
}
