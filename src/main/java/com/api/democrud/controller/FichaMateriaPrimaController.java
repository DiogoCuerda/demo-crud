package com.api.democrud.controller;


import com.api.democrud.dto.FichaMateriaPrimaDto;
import com.api.democrud.model.FichaMateriaPrima;
import com.api.democrud.service.FichaMateriaPrimaService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/ficha")
@RequiredArgsConstructor
public class FichaMateriaPrimaController {


    private final FichaMateriaPrimaService fichaMateriaPrimaService;

   @GetMapping("/{id}")
   @ResponseStatus(HttpStatus.FOUND)
    public FichaMateriaPrima busca(@PathVariable UUID id){
        return fichaMateriaPrimaService.busca(id).get();
    }
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<FichaMateriaPrima> buscaTodos(){
        return fichaMateriaPrimaService.buscaTodos();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public FichaMateriaPrima editarFichaMateriaPrima(@PathVariable UUID id, @Valid @RequestBody FichaMateriaPrimaDto fichaMateriaPrimaDto){
       return fichaMateriaPrimaService.editar(id,fichaMateriaPrimaDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FichaMateriaPrima salvarFichaMateriaPrima(@Valid @RequestBody FichaMateriaPrimaDto fichaMateriaPrimaDto){
       return fichaMateriaPrimaService.salvar(fichaMateriaPrimaDto);
    }
}
