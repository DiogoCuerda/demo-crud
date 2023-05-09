package com.api.democrud.repositorie;

import com.api.democrud.model.FichaMateriaPrima;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface FichaMateriaPrimaRepository extends JpaRepository<FichaMateriaPrima,UUID> {
}
