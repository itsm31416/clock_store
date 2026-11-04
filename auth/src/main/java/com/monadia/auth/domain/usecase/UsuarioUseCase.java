package com.monadia.auth.domain.usecase;

import com.monadia.auth.domain.exception.CamposIncompletosException;
import com.monadia.auth.domain.exception.PasswordIncorrectoException;
import com.monadia.auth.domain.exception.EmailDuplicadoException;
import com.monadia.auth.domain.exception.UsuarioNoEncontradoException;
import com.monadia.auth.domain.model.Usuario;
import com.monadia.auth.domain.model.gateway.EncrypterGateway;
import com.monadia.auth.domain.model.gateway.UsuarioGateway;
import com.monadia.auth.infraestructure.driver_adapters.jpa_repository.usuario.LoginDto;
import com.monadia.auth.infraestructure.driver_adapters.jpa_repository.usuario.RegistroDto;
import com.monadia.auth.infraestructure.driver_adapters.jpa_repository.usuario.UsuarioActualizarDto;
import com.monadia.auth.domain.model.enums.usuario.Role;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UsuarioUseCase {

    private final UsuarioGateway usuarioGateway;
    private final EncrypterGateway encrypterGateway;


    public Usuario buscarUsuarioPorId(Long id){
        try {
            Usuario usuario = usuarioGateway.buscarPorId(id);

            switch (usuario.getRole()) {
                case USUARIO:
                    return usuarioGateway.buscarPorId(id);
                /*case ADMIN:
                    return adoptanteGateway.buscarPorId(id);*/
                default:
                    return usuario;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Usuario();
        }
    }

    public void eliminarUsuario(Long id) {
        Usuario usuario = usuarioGateway.buscarPorId(id);

        if (usuario == null) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado con ID: " + id);
        }
        usuarioGateway.eliminarPorId(id);
    }

    public Usuario loginUsuario(LoginDto dto){

        if (dto.getEmail() == null || dto.getPassword() == null) {
            throw new CamposIncompletosException("El email y la contraseña son obligatorios");
        }

        Usuario usuario = usuarioGateway.buscarPorEmail(dto.getEmail());
        if (usuario == null) {
            throw new UsuarioNoEncontradoException("Usuario no encontrado");
        }

        boolean passwordValid = encrypterGateway.checkPassword(dto.getPassword(), usuario.getPassword());
        if (!passwordValid) {
            throw new PasswordIncorrectoException("Contraseña incorrecta");
        }

        usuarioGateway.actualizarLastLogin(usuario.getId(), LocalDateTime.now());

        return usuario; // ahora devolvemos el objeto completo
    }


    public boolean isValidUsuario(Usuario usuario) {
        return usuario.getName() != null &&
                usuario.getEmail() != null &&
                usuario.getPassword() != null &&
                usuario.getPhoneNumber() != null &&
                usuario.getCity() != null &&
                usuario.getAddress() != null &&
                usuario.getRole() != null;
    }

    public void actualizarUsuarioDto(Usuario usuario, UsuarioActualizarDto dto){
        if (dto != null) {
            if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
            if (dto.getPassword() != null) {
                String passwordEncrypt = encrypterGateway.encrypt(dto.getPassword());
                usuario.setPassword(passwordEncrypt);
            }
            if (dto.getPhoneNumber() != null) usuario.setPhoneNumber(dto.getPhoneNumber());
            if (dto.getCity() != null) usuario.setCity(dto.getCity());
            if (dto.getAddress() != null) usuario.setAddress(dto.getAddress());
        }
    }

    public void validarEmailUnico(String email, Long id) {
        Usuario usuarioExistente = usuarioGateway.buscarPorEmail(email);

        if (usuarioExistente != null) {
            if (id == null || !usuarioExistente.getId().equals(id)) {
                throw new EmailDuplicadoException("El email ya está registrado en el sistema");
            }
        }
    }

    public Usuario registrarUsuario(RegistroDto dto) {
        return registrarConRol(dto, Role.USUARIO);
    }

    public Usuario registrarConRol(RegistroDto dto, Role role) {
        validarEmailUnico(dto.getEmail(), null);

        Usuario usuario = new Usuario();
        usuario.setName(dto.getName());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(encrypterGateway.encrypt(dto.getPassword()));
        usuario.setPhoneNumber(dto.getPhoneNumber());
        usuario.setCity(dto.getCity());
        usuario.setAddress(dto.getAddress());
        usuario.setRole(role);

        return usuarioGateway.guardar(usuario);
    }
}
