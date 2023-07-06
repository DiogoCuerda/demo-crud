package com.api.democrud.controller;

import com.api.democrud.dto.request.LojaRequestDTO;
import com.api.democrud.dto.response.LojaResponseDTO;
import com.api.democrud.model.Loja;
import com.api.democrud.service.LojaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/loja")
@RequiredArgsConstructor
public class LojaController {

    private final LojaService lojaService;
    @PostMapping
    public LojaResponseDTO save(@Valid @RequestBody LojaRequestDTO lojaRequestDTO){
      return lojaService.save(lojaRequestDTO);
    }

    @GetMapping
    public List<LojaResponseDTO> findAll(){
        return lojaService.findAll();
    }
}
