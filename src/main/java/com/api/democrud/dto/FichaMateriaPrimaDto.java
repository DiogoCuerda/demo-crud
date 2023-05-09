package com.api.democrud.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.api.democrud.model.Produto;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FichaMateriaPrimaDto {

    private UUID id;
    private Produto id_produto;
    private Produto id_materia;
}
