package com.api.democrud.model;

import com.api.democrud.dto.request.LojaRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_loja")
public class Loja implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String nome;

    private BigDecimal credito;

    @ManyToMany(cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST

            },fetch=FetchType.EAGER)
    @JoinTable(
            name = "tb_produto_loja",
            joinColumns = @JoinColumn(name = "loja_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<Produto> produto;

    @Column(name = "data_registro")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataRegistro;

    public Loja(String nome, BigDecimal credito, List<Produto> produto) {
        this.nome = nome;
        this.credito = credito;
        this.produto = produto;
    }

    public void update(Loja loja){
        this.nome = loja.getNome();
        this.credito = loja.getCredito();
        this.produto = loja.getProduto();
    }

    @PrePersist
    public void prePersist(){
        this.dataRegistro = LocalDateTime.now();
    }
}
