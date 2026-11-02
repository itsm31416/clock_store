package com.monadia.auth.infraestructure.driver_adapters.jpa_repository.usuario;

import com.monadia.auth.domain.model.Usuario;
import com.monadia.auth.domain.model.gateway.UsuarioGateway;
import com.monadia.auth.infraestructure.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class UsuarioGatewayImpl implements UsuarioGateway {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioDataJpaRepository repository;

    @Override
    public Usuario buscarPorId(Long id) {
        return repository.findById(id)
                .map(usuarioMapper::toUsuario)
                .orElse(null);
    }
    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .map(usuarioMapper::toUsuario)
                .orElse(null);
    }

    @Override
    public void actualizarLastLogin(Long id, LocalDateTime fecha) {
        UsuarioData usuarioData = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        usuarioData.setLastLogin(fecha);
        repository.save(usuarioData);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        UsuarioData data = usuarioMapper.toData(usuario);
        UsuarioData guardado = repository.save(data);
        return usuarioMapper.toUsuario(guardado);
    }

}