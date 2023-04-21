package com.api.democrud.exception;

public class ProdutoNencontradoException extends RuntimeException{
    public ProdutoNencontradoException(String message){
        super(message);
    }
}
