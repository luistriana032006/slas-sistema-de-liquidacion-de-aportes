package com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean

    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info().title("SLAS - Sistema de liquidación de aportes a seguridad social")
                        .version("1.0.0")
                        .description(
                                "API REST para calcular aportes a seguridad social de trabajadores independientes en colombia")
                        .contact(new Contact()

                                .name("Luis miguel triana rueda")
                                .email("luistriana617@gmail.com")
                                .url("https://github.com/luistriana032006")

                        )
                        .license(new License()
                                .name(" MIT license")
                                .url("https://opensource.org/licenses/MIT"))

                );

    }
}
