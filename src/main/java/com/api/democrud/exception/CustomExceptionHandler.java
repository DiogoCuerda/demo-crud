package com.api.democrud.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String DESCRICAO_DUPLICADA = "Produto já cadastrado com esta descrição";

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ProdutoDuplicadoException.class)
    public ResponseError handleProdutoDuplicadoException(ProdutoDuplicadoException ex){

        ex.printStackTrace();
        return new ResponseError(ex.getMessage());
    }
}
