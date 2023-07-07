package com.api.democrud.controller;


import com.api.democrud.dto.request.ProdutoRequestDTO;
import com.api.democrud.dto.response.ProdutoResponseDTO;
import com.api.democrud.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/produto")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProduto(@Valid @RequestBody ProdutoRequestDTO produtoRequestDto) {
        produtoService.save(produtoRequestDto);
    }

    @GetMapping("/{id}")
    public ProdutoResponseDTO findById(@PathVariable UUID id) {
        return produtoService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable UUID id) {
        produtoService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable UUID id, @RequestBody @Valid ProdutoRequestDTO produtoRequestDto) {
        produtoService.update(id, produtoRequestDto);
    }

    @GetMapping
    public List<ProdutoResponseDTO> findAll() {
        return produtoService.findAll();
    }

}

