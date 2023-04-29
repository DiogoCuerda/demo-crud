package com.api.democrud.dto;

import com.api.democrud.enums.TipoProdutoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDto {

 private String descricao;
 private Float preco;
 private Float estoque;
 private Boolean ativo;
 private TipoProdutoEnum tipo;
}
