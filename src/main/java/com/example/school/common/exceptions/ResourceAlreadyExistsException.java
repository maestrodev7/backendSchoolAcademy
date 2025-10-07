package com.example.school.common.exceptions;

public class ResourceAlreadyExistsException extends BusinessException {
    public ResourceAlreadyExistsException(String message) {
        super(message, 409);
    }
}