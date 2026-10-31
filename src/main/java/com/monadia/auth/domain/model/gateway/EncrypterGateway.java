package com.monadia.auth.domain.model.gateway;

public interface EncrypterGateway {
    String encrypt(String password);
    Boolean checkPassword(String passUser, String passBD);
}
