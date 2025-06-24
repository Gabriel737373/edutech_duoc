package com.jonaour.msvc.inscripcionCurso.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API RESTful - MSCV - Inscripciones Cursos")
                        .description("API dedicada al mscv de Inscripcion Curso")
                        .version("1.0.0")
                );
    }
}
