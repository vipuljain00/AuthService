package org.example.Utilities;

import java.util.regex.Pattern;

public class ValidateUtil {

    public static boolean validateCredentials(String userName, String email){
        if(userName == null && email == null){
            return false;
        }
        return userName != null ? isValidUsername(userName) : isValidEmail(email);
    }

    private static boolean isValidUsername(String username) {
        String regex = "^[A-Za-z]\\w{5,29}$";
        Pattern p = Pattern.compile(regex);
        if (username == null) return false;
        return p.matcher(username).matches();
    }

    private static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern p = Pattern.compile(regex);
        if (email == null) return false;
        return p.matcher(email).matches();
    }
}
