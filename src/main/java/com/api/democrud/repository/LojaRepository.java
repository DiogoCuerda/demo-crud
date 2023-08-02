package com.api.democrud.repository;

import com.api.democrud.dto.response.LojaResponseDTO;
import com.api.democrud.model.Loja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LojaRepository extends JpaRepository<Loja, UUID> {
Optional<Loja> findByNome(String nome);


    String consultafindMaisXProdutos = "select *  from tb_loja loja\n" +
            "where loja.id in (select loja_id  from tb_produto_loja tl \n" +
            "group by loja_id \n" +
            "having count(loja_id) >= :numeroProdutos)";
    @Query(value = consultafindMaisXProdutos,nativeQuery = true)
    List<Loja> findMaisXProdutos(@Param("numeroProdutos") int numeroProdutos);
}
