package com.monadia.catalogo.domain.usecase;

import com.monadia.catalogo.domain.exception.ReferenciaDuplicadaException;
import com.monadia.catalogo.domain.exception.RelojNoEncontradoException;
import com.monadia.catalogo.domain.model.Reloj;
import com.monadia.catalogo.domain.model.gateway.RelojGateway;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RelojUseCase {

    private final RelojGateway relojGateway;

    public Reloj crear(Reloj reloj) {
        if (relojGateway.existeReferencia(reloj.getReferencia())) {
            throw new ReferenciaDuplicadaException("Ya existe un reloj con esa referencia");
        }
        reloj.setId(null);
        reloj.setActivo(true);
        return relojGateway.guardar(reloj);
    }

    public Reloj actualizar(Long id, Reloj datos) {
        Reloj existente = obtener(id);
        if (!existente.getReferencia().equals(datos.getReferencia())
                && relojGateway.existeReferencia(datos.getReferencia())) {
            throw new ReferenciaDuplicadaException("Ya existe un reloj con esa referencia");
        }
        existente.setMarca(datos.getMarca());
        existente.setReferencia(datos.getReferencia());
        existente.setGenero(datos.getGenero());
        existente.setDescripcion(datos.getDescripcion());
        existente.setPrecio(datos.getPrecio());
        existente.setStock(datos.getStock());
        return relojGateway.guardar(existente);
    }

    public Reloj obtener(Long id) {
        Reloj reloj = relojGateway.buscarPorId(id);
        if (reloj == null) throw new RelojNoEncontradoException("Reloj no encontrado con ID: " + id);
        return reloj;
    }

    public List<Reloj> listar() {
        return relojGateway.listarActivos();
    }

    public void eliminar(Long id) {
        Reloj reloj = obtener(id);
        reloj.setActivo(false);          // borrado lógico: no rompe pedidos futuros
        relojGateway.guardar(reloj);
    }
}
