package com.api.democrud.controller;

import com.api.democrud.dto.EstoqueDto;
import com.api.democrud.model.Produto;
import com.api.democrud.service.EstoqueService;
import com.api.democrud.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/estoque")
@RequiredArgsConstructor
public class EstoqueController {
    final ProdutoService produtoService;
    final EstoqueService estoqueService;

    @PutMapping("/entrada/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Produto entradaEstoque(@PathVariable(value = "id") UUID id, @RequestBody @Valid EstoqueDto estoqueDto) {

        return estoqueService.entradaEstoque(id, estoqueDto);
    }

    @PutMapping("/saida/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Produto saidaEstoque(@PathVariable(value = "id") UUID id, @RequestBody @Valid EstoqueDto estoqueDto) {

        return estoqueService.saidaEstoque(id, estoqueDto);
    }

}



