package com.api.democrud.controller;

import com.api.democrud.dto.request.LojaRequestDTO;
import com.api.democrud.model.Loja;
import com.api.democrud.service.LojaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/loja")
@RequiredArgsConstructor
public class LojaController {

    private final LojaService lojaService;
    @PostMapping
    public void save(@Valid @RequestBody LojaRequestDTO lojaRequestDTO){
      lojaService.save(lojaRequestDTO);

    }

}
