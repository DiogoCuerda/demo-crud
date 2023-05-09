package com.api.democrud.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "FICHA_MATERIA_PRIMA")

public class FichaMateriaPrima implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="id_produto")
    private Produto id_produto;

    @ManyToOne
    @JoinColumn(name="id_materia")
    private Produto id_materia;


}
