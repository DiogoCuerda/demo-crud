package com.api.democrud.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LojaResponseDTO {
    private UUID id;
    private String nome;
    private BigDecimal credito;
    private List<ProdutoResponseDTO> produtos;
}
