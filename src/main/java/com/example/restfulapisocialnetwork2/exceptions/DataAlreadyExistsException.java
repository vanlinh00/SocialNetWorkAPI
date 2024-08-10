package com.example.restfulapisocialnetwork2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class DataAlreadyExistsException extends ResponseStatusException {
    public DataAlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT, message); // HttpStatus.CONFLICT tương ứng với mã lỗi 409
    }
}
