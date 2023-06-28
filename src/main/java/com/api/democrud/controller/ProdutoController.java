package com.api.democrud.controller;


import com.api.democrud.dto.request.ProdutoRequestDTO;
import com.api.democrud.dto.response.ProdutoResponseDTO;
import com.api.democrud.model.Produto;
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
    public Produto saveProduto(@Valid @RequestBody ProdutoRequestDTO produtoRequestDto) {

        return produtoService.salvar(produtoRequestDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProdutoResponseDTO getOneProduto(@PathVariable UUID id) {

        return produtoService.consultaUm(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteProduto(@PathVariable UUID id) {
        return produtoService.deleteporId(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Produto updateProduto(@PathVariable UUID id, @RequestBody @Valid ProdutoRequestDTO produtoRequestDto) {

        return produtoService.editar(id, produtoRequestDto);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> getProdutos(@RequestParam(defaultValue = "") String descricao,
                                     @RequestParam(defaultValue = "") Boolean ativo){
        if((ativo == null)&&(descricao.equals("")))
        {
         //   return produtoService.consultaTodos();
        }

        return produtoService.consultaFiltro(descricao,ativo);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoResponseDTO> getAllProdutos(){
        return produtoService.consultaTodos();
    }

}

