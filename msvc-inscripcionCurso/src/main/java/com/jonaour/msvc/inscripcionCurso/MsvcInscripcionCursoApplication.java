package com.jonaour.msvc.inscripcionCurso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcInscripcionCursoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcInscripcionCursoApplication.class, args);
	}

}
