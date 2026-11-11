package com.monadia.catalogo.infraestructure.mapper;

import com.monadia.catalogo.domain.model.Reloj;
import com.monadia.catalogo.infraestructure.driver_adapters.jpa_repository.reloj.RelojData;
import org.springframework.stereotype.Component;

@Component
public class RelojMapper {

    public Reloj toReloj(RelojData d) {
        if (d == null) return null;
        return new Reloj(d.getId(), d.getMarca(), d.getReferencia(), d.getGenero(),
                d.getDescripcion(), d.getPrecio(), d.getStock(), d.isActivo());
    }

    public RelojData toData(Reloj r) {
        if (r == null) return null;
        return new RelojData(r.getId(), r.getMarca(), r.getReferencia(), r.getGenero(),
                r.getDescripcion(), r.getPrecio(), r.getStock(), r.isActivo());
    }
}