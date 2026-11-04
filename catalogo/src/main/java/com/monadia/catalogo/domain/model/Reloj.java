package com.monadia.catalogo.domain.model;

import com.monadia.catalogo.domain.model.enums.clocks.Genero;
import com.monadia.catalogo.domain.model.enums.clocks.Marca;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

public class Reloj {
    private Long id;
    private Marca marca;
    private String referencia;
    private Genero genero;
    private String descripcion;
    private long precio;      // en pesos COP
    private int stock;
    private boolean activo;   // borrado lógico
}
