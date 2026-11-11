package com.monadia.catalogo.infraestructure.driver_adapters.jpa_repository.reloj;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RelojDataJpaRepository extends JpaRepository<RelojData, Long> {
    Optional<RelojData> findByIdAndActivoTrue(Long id);
    List<RelojData> findByActivoTrue();
    boolean existsByReferencia(String referencia);
}
