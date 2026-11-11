package com.monadia.catalogo.infraestructure.entry_points;
import com.monadia.catalogo.domain.model.enums.clocks.Genero;
import com.monadia.catalogo.domain.model.enums.clocks.Marca;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RelojDto {
    @NotNull private Marca marca;
    @NotBlank @Size(max = 60) private String referencia;
    @NotNull private Genero genero;
    @Size(max = 1000) private String descripcion;
    @Positive private long precio;
    @PositiveOrZero private int stock;
}