package com.monadia.catalogo.infraestructure.driver_adapters.jpa_repository.reloj;

import com.monadia.catalogo.domain.model.enums.clocks.Genero;
import com.monadia.catalogo.domain.model.enums.clocks.Marca;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "relojes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelojData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Marca marca;

    @Column(nullable = false, unique = true, length = 60)
    private String referencia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genero genero;

    @Column(length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private long precio;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private boolean activo = true;
}