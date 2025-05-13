package ru.kata.spring.boot_security.demo.securities;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.stream.Collectors;

public class TokenDetails {
    private TokenDetails() {

    }

    public static String getAuthoritiesFromSecurityToken(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    }
}
