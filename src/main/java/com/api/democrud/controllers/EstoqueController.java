package com.api.democrud.controllers;

import com.api.democrud.dtos.EstoqueDto;
import com.api.democrud.models.Produto;
import com.api.democrud.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/estoque")
public class EstoqueController {
     final ProdutoService produtoService;

    public EstoqueController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }
    @GetMapping
    public String index(){

        return "No easter eggs here.";
    }
    @PutMapping("/entrada/{id}")
    public void entradaEstoque(@PathVariable(value = "id") UUID id, @RequestBody @Valid EstoqueDto estoqueDto) {
        Optional<Produto> produtoModelOptional = produtoService.findbyId(id);
        /*if(!produtoModelOptional.isPresent()) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID solicitada não encontrada!!");
        }*/
        var estoqueModel = produtoModelOptional.get();

      //  estoqueModel.setEstoque(estoqueModel.getEstoque() + estoqueDto.getEstoque());
       // return ResponseEntity.status(HttpStatus.OK).body(produtoService.save(estoqueModel));

    }

    @PutMapping("/saida/{id}")
    public void saidaEstoque(@PathVariable(value = "id") UUID id, @RequestBody @Valid EstoqueDto estoqueDto) {
        Optional<Produto> produtoModelOptional = produtoService.findbyId(id);
        /*if(!produtoModelOptional.isPresent()) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID solicitada não encontrada!!");
        }*/
        var estoqueModel = produtoModelOptional.get();

        /*if (estoqueModel.getEstoque() - estoqueDto.getEstoque() >= 0) {
            estoqueModel.setEstoque(estoqueModel.getEstoque() - estoqueDto.getEstoque());
            return ResponseEntity.status(HttpStatus.OK).body(produtoService.save(estoqueModel));
        }*/
        //return ResponseEntity.status(HttpStatus.CONFLICT).body("Estoque não pode ser negativo!!");

    }

}



