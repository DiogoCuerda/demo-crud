package com.api.democrud.controller;

import com.api.democrud.dto.request.LojaRequestDTO;
import com.api.democrud.dto.response.LojaResponseDTO;
import com.api.democrud.model.Loja;
import com.api.democrud.service.LojaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/loja")
@RequiredArgsConstructor
public class LojaController {

    private final LojaService lojaService;
    @PostMapping
    public ResponseEntity<LojaResponseDTO> save(@Valid @RequestBody LojaRequestDTO lojaRequestDTO){
        Loja loja = lojaService.save(lojaRequestDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(loja.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable UUID id,@Valid @RequestBody LojaRequestDTO lojaRequestDTO){
        lojaService.update(id,lojaRequestDTO);
    }

    @GetMapping("/{id}")
    public LojaResponseDTO findById(@PathVariable UUID id){
        return lojaService.findById(id);
    }
    @GetMapping
    public List<LojaResponseDTO> findAll(){
        return lojaService.findAll();
    }

    @GetMapping("/filtro1")
    public List<LojaResponseDTO> findMaisXProdutos(@RequestParam int numeroProdutos){
        return lojaService.findMaisXProdutos(numeroProdutos);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        lojaService.delete(id);
    }
}
