package com.monadia.auth.infraestructure.entry_points;

import com.monadia.auth.domain.exception.CamposIncompletosException;
import com.monadia.auth.domain.exception.PasswordIncorrectoException;
import com.monadia.auth.domain.exception.UsuarioNoEncontradoException;
import com.monadia.auth.domain.model.Usuario;
import com.monadia.auth.domain.usecase.UsuarioUseCase;
import com.monadia.auth.infraestructure.driver_adapters.jpa_repository.usuario.AuthResponse;
import com.monadia.auth.infraestructure.driver_adapters.jpa_repository.usuario.LoginDto;
import com.monadia.auth.infraestructure.driver_adapters.jpa_repository.usuario.RegistroDto;
import com.monadia.auth.infraestructure.security.JwtValidation;
import com.monadia.auth.domain.exception.EmailDuplicadoException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/monadia/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioUseCase usuarioUseCase;
    private final JwtValidation jwtValidation;

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdUsuario(@PathVariable Long id) {

        Usuario usuario = usuarioUseCase.buscarUsuarioPorId(id);

        if (usuario.getId() != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        }

        return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioUseCase.eliminarUsuario(id);
            return new ResponseEntity<>("Usuario eliminado", HttpStatus.OK);

        } catch (UsuarioNoEncontradoException error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto dto) {
        // Llamamos al caso de uso


        try{
            Usuario usuario = usuarioUseCase.loginUsuario(dto);
            // Generamos token (convertimos el rol de enum a String)
            String token = jwtValidation.generateToken(usuario.getId(), usuario.getRole().name());

            // Construimos la respuesta
            AuthResponse response = new AuthResponse(
                    usuario.getId(),
                    usuario.getEmail(),
                    usuario.getRole().name(),
                    token,
                    "Bienvenido");
            return ResponseEntity.ok(response);
        }catch (UsuarioNoEncontradoException | PasswordIncorrectoException | CamposIncompletosException error) {
            return ResponseEntity.badRequest().body(error.getMessage());

        }
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegistroDto dto) {
        try {
            Usuario creado = usuarioUseCase.registrarUsuario(dto);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    Map.of("id", creado.getId(),
                            "email", creado.getEmail(),
                            "role", creado.getRole().name(),
                            "message", "Usuario registrado"));

        } catch (EmailDuplicadoException error) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error.getMessage());
        }
    }


}