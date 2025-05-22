package com.jonaour.msvc.inscripcionCurso.clients;

import com.jonaour.msvc.inscripcionCurso.models.Alumno;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="msvc-alumnos", url="localhost:8081/api/v1/alumnos/")
public interface AlumnoClientRest {

    @GetMapping("/{id}")
    Alumno findById(@PathVariable Long id);

}