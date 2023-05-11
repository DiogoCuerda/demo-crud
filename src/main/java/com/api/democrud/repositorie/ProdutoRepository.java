package com.api.democrud.repositorie;

import com.api.democrud.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.UUID;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, UUID> {
    boolean existsByDescricao(String Descricao);
    boolean existsById(UUID id);

    @Query("SELECT p from Produto p where p.descricao like %:descricao% and p.ativo = :ativo")
    List<Produto> searchByProdutoDescricaoAtivo(@Param("descricao") String descricao, @Param("ativo") Boolean ativo);

}
