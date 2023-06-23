package com.api.democrud.controller;

import com.api.democrud.dto.ProdutoDTO;
import com.api.democrud.model.Produto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/embalagem")
@RequiredArgsConstructor
public class EmbalagemController {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto saveEmbalagem(@Valid @RequestBody ProdutoDTO produtoDto) {

        return produtoService.salvar(produtoDto);
    }
}
