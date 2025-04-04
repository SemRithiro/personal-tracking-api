package com.rithiro.personaltracking.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {
    @Value("${spring.application.name}")
    private String appName;

    @Value("${info.app.version}")
    private String appVersion;

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(appName)
                        .version(appVersion)
                        .description(appName + " API documentation")
                        .license(new License().name("Powered by: Sem Rithiro")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
