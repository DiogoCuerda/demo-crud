package com.api.democrud.model;


import com.api.democrud.enums.TipoProdutoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_produto")
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;

    private BigDecimal preco;

    private Integer estoque;

    private Boolean ativo;

    @Enumerated(EnumType.STRING)
    private TipoProdutoEnum tipo;

    @Column(name = "data_registro")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataRegistro;

    @PrePersist
    public void prePersist(){
        this.dataRegistro = LocalDateTime.now();
    }


}
