package com.monadia.catalogo.domain.model.gateway;

import com.monadia.catalogo.domain.model.Reloj;
import java.util.*;

public interface RelojGateway {
    Reloj guardar(Reloj reloj);
    Reloj buscarPorId(Long id);                 // null si no existe o está inactivo
    List<Reloj> listarActivos();
    boolean existeReferencia(String referencia);
}
