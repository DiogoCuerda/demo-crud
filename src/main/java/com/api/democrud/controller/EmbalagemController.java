package com.api.democrud.controller;

import com.api.democrud.dto.request.EmbalagemRequestDTO;
import com.api.democrud.dto.response.EmbalagemResponseDTO;
import com.api.democrud.service.EmbalagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/embalagem")
@RequiredArgsConstructor
public class EmbalagemController {

   private final EmbalagemService embalagemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmbalagemResponseDTO save(@Valid @RequestBody EmbalagemRequestDTO embalagemRequestDTO) {
        return embalagemService.save(embalagemRequestDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable UUID id,@Valid @RequestBody EmbalagemRequestDTO embalagemRequestDTO){
        embalagemService.update(id,embalagemRequestDTO);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
       embalagemService.delete(id);
    }

    @GetMapping("/{id}")
    public EmbalagemResponseDTO findById(@PathVariable UUID id){
        return embalagemService.findById(id);
    }

    @GetMapping
    public List<EmbalagemResponseDTO> findAll(){
        return embalagemService.findAll();
    }
}
