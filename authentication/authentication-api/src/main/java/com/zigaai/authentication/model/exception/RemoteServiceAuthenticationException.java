package com.zigaai.authentication.model.exception;

import org.springframework.security.core.AuthenticationException;

public class RemoteServiceAuthenticationException extends AuthenticationException {

    public RemoteServiceAuthenticationException(String msg) {
        super(msg);
    }

}
