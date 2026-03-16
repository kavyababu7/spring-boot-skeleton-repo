package com.nissan.template.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {
    private final String code;
    private final HttpStatus status;

    public BusinessException(String message, String code, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public BusinessException(String message, HttpStatus status) {
        this(message, "BUSINESS_ERROR", status);
    }
}
