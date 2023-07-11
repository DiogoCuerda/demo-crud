package com.api.democrud.model;


import com.api.democrud.dto.response.EmbalagemResponseDTO;
import com.api.democrud.enums.CategoriaProdutoEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;
import org.springframework.data.util.QTypeContributor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_produto")
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;
    private Integer estoque;
    private Boolean ativo;
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    private CategoriaProdutoEnum categoria;

    @OneToMany(mappedBy = "produto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Embalagem> embalagem;

    @ManyToMany(cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            })
    @JoinTable(
            name = "tb_produtoloja",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "loja_id"))
    private List<Loja> loja;

    @Column(name = "data_registro")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataRegistro;

    public Produto(String nome, Integer estoque, Boolean ativo, BigDecimal preco, CategoriaProdutoEnum categoria) {
        this.nome = nome;
        this.estoque = estoque;
        this.ativo = ativo;
        this.preco = preco;
        this.categoria = categoria;
    }

    public void update(Produto produto) {
        this.nome = produto.getNome();
        this.estoque = produto.getEstoque();
        this.ativo = produto.getAtivo();
        this.preco = produto.getPreco();
        this.categoria = produto.getCategoria();
    }

    @PrePersist
    public void prePersist() {
        this.dataRegistro = LocalDateTime.now();
    }

}
