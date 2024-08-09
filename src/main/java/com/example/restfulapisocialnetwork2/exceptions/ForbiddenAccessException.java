package com.example.restfulapisocialnetwork2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

// Custom exception class for handling forbidden access
public class ForbiddenAccessException extends ResponseStatusException {
    public ForbiddenAccessException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
