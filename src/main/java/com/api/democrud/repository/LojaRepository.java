package com.api.democrud.repository;

import com.api.democrud.model.Loja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LojaRepository extends JpaRepository<Loja, UUID> {
Optional<Loja> findByNome(String nome);

}
