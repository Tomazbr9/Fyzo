package com.fyzo.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI fyzoOpenApi() {
        return new OpenAPI()
            .info(new Info()
                .title("Fyzo API")
                .description("API para gerenciamento de finanças")
                .version("v1.0")
                .contact(new Contact()
                    .name("Equipe Fyzo")
                    .email("contato@fyzo.com"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("http://springdoc.org"))
            )
            .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
            .components(new Components()
                .addSecuritySchemes("BearerAuth",
                    new SecurityScheme()
                        .name("Authorization")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                )
            );
    }
}

