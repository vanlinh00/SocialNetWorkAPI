package com.example.restfulapisocialnetwork2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

// Custom exception class for JWT creation failure
public class JwtCreationException extends ResponseStatusException {
    public JwtCreationException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
