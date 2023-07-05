package com.api.democrud.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class ProdutoLojaKey implements Serializable {

    private UUID produtoId;

    private UUID lojaId;

}
