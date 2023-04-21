package com.api.democrud.controllers;


import com.api.democrud.dtos.ProdutoDto;
import com.api.democrud.models.Produto;
import com.api.democrud.services.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
    public ResponseEntity<List<Produto>> getAllProdutos() {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.getAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneProduto(@PathVariable(value = "id") UUID id) {
        Optional<Produto> produtoModelOptional = produtoService.findbyId(id);
        if (!produtoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID solicitada não encontrada!!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(produtoModelOptional.get());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduto(@PathVariable(value = "id") UUID id) {
        Optional<Produto> produtoModelOptional = produtoService.findbyId(id);
        if (!produtoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID solicitada não encontrada!!");
        }
        produtoService.deletebyId(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deletou esse id: " + id);
    }

    @PutMapping("/{id}")
    public void updateProduto(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProdutoDto produtoDto) {
        Optional<Produto> produtoModelOptional = produtoService.findbyId(id);
        if (!produtoModelOptional.isPresent()) {
          //  return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID solicitada não encontrada!!");
        }
        var produtoModel = produtoModelOptional.get();
        if (produtoService.existsByDescricao(produtoDto.getDescricao())
                && !Arrays.equals(produtoModel.getDescricao().getBytes(), produtoDto.getDescricao().getBytes())) {
          //  return ResponseEntity.status(HttpStatus.CONFLICT).body("Não é possível atualizar, já existe produto com a descrição solicitada");
        }
        // if(produtoDto.getDescricao() != produtoModel.getDescricao())
        if (produtoDto.getDescricao().getBytes()[0] != 45) {
            produtoModel.setDescricao(produtoDto.getDescricao());
        }

        if (produtoDto.getAtivo() != produtoModel.getAtivo()) {
            produtoModel.setAtivo(produtoDto.getAtivo());
        }
        if (produtoDto.getPreco() != 0) {
            produtoModel.setPreco(produtoDto.getPreco());
        }
       // return ResponseEntity.status(HttpStatus.FOUND).body(produtoService.save(produtoModel));
    }


}

