package com.bozzaccio.twitterclone.security;

import org.springframework.security.core.AuthenticationException;

public class JWTAuthException extends AuthenticationException {

    private static final long serialVersionUID = -275624746981206143L;

    public JWTAuthException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JWTAuthException(String msg) {
        super(msg);
    }
}
