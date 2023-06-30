package com.api.democrud.dto.response;

import com.api.democrud.enums.CategoriaProdutoEnum;
import com.api.democrud.model.Embalagem;
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
public class ProdutoResponseDTO {

    //private UUID id;
    private String nome;
    private BigDecimal preco;
    private Integer estoque;
    private Boolean ativo;
    private CategoriaProdutoEnum categoria;
    private List<EmbalagemResponseDTO> embalagem;
    //private List<UUID> embalagem;

}
