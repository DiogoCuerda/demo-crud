package com.api.democrud.repositories;

import com.api.democrud.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    boolean existsByDescricao(String Descricao);

}
