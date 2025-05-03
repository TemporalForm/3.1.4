package ru.kata.spring.boot_security.demo.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.stream.Collectors;

public class ControllerUtil {
    private ControllerUtil() {

    }

    public static String getAuthoritiesFromSecurityToken(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    }
}
