package com.api.democrud.exception;

public class EstoqueNegativoExcepion extends RuntimeException{
    public EstoqueNegativoExcepion (String message) {
        super(message);
    }
}
