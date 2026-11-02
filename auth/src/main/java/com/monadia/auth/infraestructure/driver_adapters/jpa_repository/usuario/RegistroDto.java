package com.monadia.auth.infraestructure.driver_adapters.jpa_repository.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistroDto {
    @NotBlank private String name;
    @NotBlank @Email private String email;
    @NotBlank @Size(min = 8) private String password;
    @NotBlank private String phoneNumber;
    @NotBlank private String city;
    @NotBlank private String address;
}