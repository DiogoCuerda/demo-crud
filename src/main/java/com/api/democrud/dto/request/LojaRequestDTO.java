package com.api.democrud.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LojaRequestDTO {

    private String nome;
    private BigDecimal credito;
    private List<UUID> produtos;

}
