package com.api.democrud.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoLojaRequestDTO {
    private UUID produtoId;
    private UUID lojaID;
    private int estoque;

}
