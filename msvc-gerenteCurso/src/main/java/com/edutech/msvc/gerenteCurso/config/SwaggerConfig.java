package com.edutech.msvc.gerenteCurso.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("API Restfull - MSVC - GerenteCurso")
                        .description("API dedicada al msvc de Gerente de Cursos")
                        .version("1.0.0")

                );
    }
}
