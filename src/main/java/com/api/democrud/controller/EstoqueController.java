package com.api.democrud.controller;

import com.api.democrud.dto.EstoqueDto;
import com.api.democrud.dto.ProdutoDto;
import com.api.democrud.model.Produto;
import com.api.democrud.service.EstoqueService;
import com.api.democrud.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/estoque")
public class EstoqueController {
    final ProdutoService produtoService;
    final EstoqueService estoqueService;

    public EstoqueController(ProdutoService produtoService, EstoqueService estoqueService) {
        this.produtoService = produtoService;
        this.estoqueService = estoqueService;
    }


    @PutMapping("/entrada/{id}")
    public Produto entradaEstoque(@PathVariable(value = "id") UUID id, @RequestBody @Valid EstoqueDto estoqueDto) {

        return estoqueService.entradaEstoque(id, estoqueDto);
    }

    @PutMapping("/saida/{id}")
    public Produto saidaEstoque(@PathVariable(value = "id") UUID id, @RequestBody @Valid EstoqueDto estoqueDto) {

        return estoqueService.saidaEstoque(id, estoqueDto);
    }

}



