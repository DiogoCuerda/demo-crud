package com.api.democrud.dto.request;

import com.api.democrud.enums.CategoriaProdutoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequestDTO {

 private String nome;
 private BigDecimal preco;
 private Integer estoque;
 private Boolean ativo;
 private CategoriaProdutoEnum categoria;
}
