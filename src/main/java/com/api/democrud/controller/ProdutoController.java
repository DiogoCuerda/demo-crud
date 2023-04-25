package com.api.democrud.controller;


import com.api.democrud.dto.ProdutoDto;
import com.api.democrud.model.Produto;
import com.api.democrud.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/produto")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto saveProduto(@Valid @RequestBody ProdutoDto produtoDto) {

        return produtoService.save(produtoDto);
    }

    @GetMapping //Mapeia o GET do /produto para este metodo
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> getAllProdutos() {

        return produtoService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto getOneProduto(@PathVariable(value = "id") UUID id) {

        return produtoService.consultaUm(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String deleteProduto(@PathVariable(value = "id") UUID id) {

        return produtoService.deletebyId(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) //Qual é a resposta http correta para um put bem sucedido
    public Produto updateProduto(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProdutoDto produtoDto) {

        return produtoService.edit(id, produtoDto);
    }

}

