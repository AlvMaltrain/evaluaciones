package com.evaluaciones.microservicioEvaluaciones.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservicio de Evaluaciones - EduTech")
                        .version("1.0")
                        .description("API REST para la gestión de evaluaciones en cursos de EduTech."));
    }
}

//URL Swagger: http://localhost:8080/swagger-ui/index.html#/