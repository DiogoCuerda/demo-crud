package com.api.democrud.controller;

import com.api.democrud.dto.request.EmbalagemRequestDTO;
import com.api.democrud.dto.response.EmbalagemResponseDTO;
import com.api.democrud.model.Embalagem;
import com.api.democrud.service.EmbalagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/embalagem")
@RequiredArgsConstructor
public class EmbalagemController {

   private final EmbalagemService embalagemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmbalagemResponseDTO saveEmbalagem(@Valid @RequestBody EmbalagemRequestDTO embalagemRequestDTO) {

        return embalagemService.save(embalagemRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmbalagem(@PathVariable UUID id){
       embalagemService.delete(id);
    }
}
