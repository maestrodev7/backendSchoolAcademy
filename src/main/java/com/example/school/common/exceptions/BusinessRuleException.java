package com.example.school.common.exceptions;

public class BusinessRuleException extends BusinessException {
    public BusinessRuleException(String message) {
        super(message, 400);
    }
}

