package com.edutech_duoc.msvc_profesor.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApo(){
        return new OpenAPI()
                .info(new Info()
                        .title("Api restfull - MSVC profesor")
                        .description(" descripcion de la api de profesor")
                        .version("1.0.0")
                );
    }
}
