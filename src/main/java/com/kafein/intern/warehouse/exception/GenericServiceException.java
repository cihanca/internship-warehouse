package com.kafein.intern.warehouse.exception;

import lombok.Data;

@Data
public class GenericServiceException extends RuntimeException{

    private String description;

    public GenericServiceException(String message) {
        super(message);
        this.description = description;
    }
}
