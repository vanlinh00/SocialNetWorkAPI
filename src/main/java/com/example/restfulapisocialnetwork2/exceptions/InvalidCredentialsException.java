package com.example.restfulapisocialnetwork2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

// Custom exception class for authentication failure
public class InvalidCredentialsException extends ResponseStatusException {
    public InvalidCredentialsException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
