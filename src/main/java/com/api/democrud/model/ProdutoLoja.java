package com.api.democrud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_produtoloja")
public class ProdutoLoja implements Serializable {

    @EmbeddedId
    private ProdutoLojaKey id;

    @ManyToOne
    @MapsId("produtoId")
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @MapsId("lojaId")
    @JoinColumn(name = "loja_id")
    private Produto loja;

    private int estoque;

}
