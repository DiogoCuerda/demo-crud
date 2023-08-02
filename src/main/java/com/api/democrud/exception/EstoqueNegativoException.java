package com.api.democrud.exception;

public class EstoqueNegativoException extends RuntimeException{
    public EstoqueNegativoException(String message) {
        super(message);
    }
}
