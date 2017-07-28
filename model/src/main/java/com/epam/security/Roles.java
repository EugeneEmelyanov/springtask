package com.epam.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by yauhen_yemelyanau on 7/10/17.
 */
public enum Roles implements GrantedAuthority{

    REGISTERED_USER,
    BOOKING_MANAGER;


    @Override
    public String getAuthority() {
        return name();
    }
}
