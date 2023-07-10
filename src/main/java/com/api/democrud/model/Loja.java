package com.api.democrud.model;

import com.api.democrud.dto.request.LojaRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_loja")
public class Loja implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;
    private BigDecimal credito;

//    @ManyToMany(mappedBy = "loja",  cascade = { CascadeType.ALL })

    @ManyToMany(cascade = {
            CascadeType.ALL
    })
    @JoinTable(
            name = "tb_produtoloja",
            joinColumns = @JoinColumn(name = "loja_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id"))
    private List<Produto> produto;

    @Column(name = "data_registro")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataRegistro;

    public Loja(String nome, BigDecimal credito) {
        this.nome = nome;
        this.credito = credito;
    }

    public void update(Loja loja){
        this.nome = loja.getNome();
        this.credito = loja.getCredito();
        this.produto = loja.getProduto();
    }

    public void update(String nome, BigDecimal credito, List<Produto> produto){
        this.nome = nome;
        this.credito = credito;
        this.produto = produto;
    }

    @PrePersist
    public void prePersist(){
        this.dataRegistro = LocalDateTime.now();
    }
}
