package com.alefligiero.agenciabackend.services.exceptions;

public class JWTException extends RuntimeException {

    public JWTException(String msg) {
        super(msg);
    }
}
