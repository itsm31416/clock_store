package com.monadia.catalogo.application.config;

import com.monadia.catalogo.domain.model.gateway.RelojGateway;
import com.monadia.catalogo.domain.usecase.RelojUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public RelojUseCase relojUseCase(RelojGateway relojGateway) {
        return new RelojUseCase(relojGateway);
    }
}