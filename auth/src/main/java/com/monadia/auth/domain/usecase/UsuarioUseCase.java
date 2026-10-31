package com.monadia.auth.domain.usecase;

import com.monadia.auth.domain.exception.CamposIncompletosException;
import com.monadia.auth.domain.exception.PasswordIncorrectoException;
import com.monadia.auth.domain.exception.EmailDuplicadoException;
import com.monadia.auth.domain.exception.UsuarioNoEncontradoException;
import com.monadia.auth.domain.model.Usuario;
import com.monadia.auth.domain.model.gateway.EncrypterGateway;
import com.monadia.auth.domain.model.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UsuarioUseCase {

    private final UsuarioGateway usuarioGateway;
    private final EncrypterGateway encrypterGateway;

    public Usuario buscarUsuarioPorId(Long id){
        try {
            Usuario usuario = usuarioGateway.buscarPorId(id);
        } catch (UsuarioNoEncontradoException e){
            System.out.println(e.getMessage());

        }
        return new Usuario();
    }

    public void eliminarUsuario(Long id){
        Usuario usuario = usuarioGateway.buscarPorId(id);

        if (usuario == null){
            throw new UsuarioNoEncontradoException("Usuario no encontrado con ID: "+id);
        }
        usuarioGateway.eliminarPorId(id);
    }



}
