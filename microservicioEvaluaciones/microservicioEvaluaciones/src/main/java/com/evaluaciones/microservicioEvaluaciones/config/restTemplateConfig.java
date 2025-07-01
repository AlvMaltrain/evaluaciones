package com.evaluaciones.microservicioEvaluaciones.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class restTemplateConfig {
@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
