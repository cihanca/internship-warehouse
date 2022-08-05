package com.kafein.intern.warehouse.exception;

import com.kafein.intern.warehouse.enums.ErrorType;
import lombok.Data;

@Data
public class GenericServiceException extends RuntimeException{

    private ErrorType errorType;

    private String description;

    public GenericServiceException(String description, ErrorType errorType) {
        this.description = description;
        this.errorType = errorType;

    }
}
