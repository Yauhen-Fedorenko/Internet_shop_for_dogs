package com.spring.shop.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, REDACTOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
