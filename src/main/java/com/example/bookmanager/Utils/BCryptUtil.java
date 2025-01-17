package com.example.bookmanager.Utils;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtil {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
