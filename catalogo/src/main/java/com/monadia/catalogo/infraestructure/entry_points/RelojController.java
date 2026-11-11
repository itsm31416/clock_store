package com.monadia.catalogo.infraestructure.entry_points;

import com.monadia.catalogo.domain.model.Reloj;
import com.monadia.catalogo.domain.usecase.RelojUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/monadia/relojes")
@RequiredArgsConstructor
public class RelojController {

    private final RelojUseCase relojUseCase;

    @GetMapping
    public List<Reloj> listar() {
        return relojUseCase.listar();
    }

    @GetMapping("/{id}")
    public Reloj obtener(@PathVariable Long id) {
        return relojUseCase.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reloj crear(@Valid @RequestBody RelojDto dto) {
        return relojUseCase.crear(aDominio(dto));
    }

    @PutMapping("/{id}")
    public Reloj actualizar(@PathVariable Long id, @Valid @RequestBody RelojDto dto) {
        return relojUseCase.actualizar(id, aDominio(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        relojUseCase.eliminar(id);
        return ResponseEntity.ok("Reloj eliminado");
    }

    private Reloj aDominio(RelojDto dto) {
        Reloj r = new Reloj();
        r.setMarca(dto.getMarca());
        r.setReferencia(dto.getReferencia());
        r.setGenero(dto.getGenero());
        r.setDescripcion(dto.getDescripcion());
        r.setPrecio(dto.getPrecio());
        r.setStock(dto.getStock());
        return r;
    }
}