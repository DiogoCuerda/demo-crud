package com.api.democrud.repository;

import com.api.democrud.model.Embalagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface EmbalagemRepository extends JpaRepository<Embalagem, UUID> {

    Optional<Embalagem> findByNome(String nome);
}
