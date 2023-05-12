package com.api.democrud.controller;


import com.api.democrud.dto.ProdutoDto;
import com.api.democrud.model.FichaMateriaPrima;
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
    public Produto saveProduto(@Valid @RequestBody ProdutoDto produtoDto) {

        return produtoService.salvar(produtoDto);
    }

 /*   @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> getAllProdutos() {

        return produtoService.consultaTodos();
    }*/

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto getOneProduto(@PathVariable UUID id) {

        return produtoService.consultaUm(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteProduto(@PathVariable UUID id) {
        return produtoService.deleteporId(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Produto updateProduto(@PathVariable UUID id, @RequestBody @Valid ProdutoDto produtoDto) {

        return produtoService.editar(id, produtoDto);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> getProdutos(@RequestParam(defaultValue = "") String descricao,
                                     @RequestParam(defaultValue = "") Boolean ativo){
        if((ativo == null)&&(descricao.equals("")))
        {
            return produtoService.consultaTodos();
        }

        return produtoService.consultaFiltro(descricao,ativo);
    }

}

