package com.forums.app.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtils {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encodePassword(String password) {
        return encoder.encode(password);
    }

    public static boolean matchPassword(String password, String encodedPassword) {
        return encoder.matches(password, encodedPassword);
    }
}
