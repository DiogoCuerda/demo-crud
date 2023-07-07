package com.api.democrud.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LojaResponseDTO {
    private String nome;
    private BigDecimal credito;
    private List<ProdutoResponseDTO> produtos;
}
