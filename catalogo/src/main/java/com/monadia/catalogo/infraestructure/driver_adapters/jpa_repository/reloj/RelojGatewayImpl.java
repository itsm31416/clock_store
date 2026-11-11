package com.monadia.catalogo.infraestructure.driver_adapters.jpa_repository.reloj;

import com.monadia.catalogo.domain.model.Reloj;
import com.monadia.catalogo.domain.model.gateway.RelojGateway;
import com.monadia.catalogo.infraestructure.mapper.RelojMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RelojGatewayImpl implements RelojGateway {

    private final RelojDataJpaRepository repository;
    private final RelojMapper mapper;

    @Override
    public Reloj guardar(Reloj reloj) {
        return mapper.toReloj(repository.save(mapper.toData(reloj)));
    }

    @Override
    public Reloj buscarPorId(Long id) {
        return repository.findByIdAndActivoTrue(id).map(mapper::toReloj).orElse(null);
    }

    @Override
    public List<Reloj> listarActivos() {
        return repository.findByActivoTrue().stream().map(mapper::toReloj).toList();
    }

    @Override
    public boolean existeReferencia(String referencia) {
        return repository.existsByReferencia(referencia);
    }
}