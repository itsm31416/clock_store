package com.monadia.auth.application.config;

import com.monadia.auth.domain.model.enums.usuario.Role;
import com.monadia.auth.domain.model.gateway.UsuarioGateway;
import com.monadia.auth.domain.usecase.UsuarioUseCase;
import com.monadia.auth.infraestructure.driver_adapters.jpa_repository.usuario.RegistroDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {

    private final UsuarioUseCase usuarioUseCase;
    private final UsuarioGateway usuarioGateway;

    @Value("${admin.password:}")
    private String adminPassword;

    @Value("${admin.emails:}")
    private List<String> adminEmails;

    @Value("${admin.nombres:}")
    private List<String> adminNombres;

    @Value("${admin.telefonos:}")
    private List<String> adminTelefonos;

    @Override
    public void run(String... args) {
        if (adminPassword.isBlank() || adminEmails.isEmpty()
                || (adminEmails.size() == 1 && adminEmails.get(0).isBlank())) {
            System.out.println("Variables de admin no configuradas: no se crean administradores");
            return;
        }

        if (adminNombres.size() != adminEmails.size()
                || adminTelefonos.size() != adminEmails.size()) {
            System.out.println("ADMIN_EMAILS, ADMIN_NOMBRES y ADMIN_TELEFONOS deben tener la misma cantidad de valores");
            return;
        }

        for (int i = 0; i < adminEmails.size(); i++) {
            String email = adminEmails.get(i).trim();
            String nombre = adminNombres.get(i).trim();
            String telefono = adminTelefonos.get(i).trim();

            if (usuarioGateway.buscarPorEmail(email) == null) {
                usuarioUseCase.registrarConRol(crear(nombre, email, telefono), Role.ADMIN);
                System.out.println("Administrador creado: " + email);
            }
        }
    }

    private RegistroDto crear(String name, String email, String phone) {
        RegistroDto dto = new RegistroDto();
        dto.setName(name);
        dto.setEmail(email);
        dto.setPassword(adminPassword);
        dto.setPhoneNumber(phone);
        dto.setCity("Bogota");
        dto.setAddress("Sede principal");
        return dto;
    }
}
