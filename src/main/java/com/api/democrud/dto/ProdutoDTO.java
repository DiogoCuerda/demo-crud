package com.api.democrud.dto;

import com.api.democrud.enums.TipoProdutoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

 private String nome;
 private BigDecimal preco;
 private Integer estoque;
 private Boolean ativo;
 private TipoProdutoEnum tipo;
}
