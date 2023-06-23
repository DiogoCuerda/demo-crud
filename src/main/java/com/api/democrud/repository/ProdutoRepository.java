package com.api.democrud.repository;

import com.api.democrud.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.UUID;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    boolean existsByNome(String nome);
    boolean existsById(UUID id);

    List<Produto>findAllByNomeContainingAndAtivo(String text, Boolean ativo);
}
