package com.api.democrud.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LojaResponseDTO {
    private String nome;
    private Double credito;
    private List<ProdutoResponseDTO> produtos;
}
