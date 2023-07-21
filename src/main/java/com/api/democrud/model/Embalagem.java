package com.api.democrud.model;


import com.api.democrud.dto.request.EmbalagemRequestDTO;
import com.api.democrud.repository.ProdutoRepository;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_embalagem")
public class Embalagem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String nome;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public Embalagem(String nome, Produto produto) {
        this.nome = nome;
        this.produto = produto;
    }

    public void update(Embalagem embalagem) {
        this.nome = embalagem.getNome();
        this.produto = embalagem.getProduto();
    }
}
