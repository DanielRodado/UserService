package com.example.userservice.utils;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;

public class AuthUtil {

    public static String getFirstAuthority(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .findFirst()
                .get()
                .toString();
    }

}
