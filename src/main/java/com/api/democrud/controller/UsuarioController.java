package com.api.democrud.controller;

import com.api.democrud.dto.request.UsuarioRequestDTO;
import com.api.democrud.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO){
       usuarioService.save(usuarioRequestDTO);
    }

}
