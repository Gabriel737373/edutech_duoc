package com.jonaour.msvc.inscripcionCurso.clients;

import com.jonaour.msvc.inscripcionCurso.models.GerenteCurso;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="msvc-gerenteCurso", url="localhost:8088/api/v1/gerenteCursos")
public interface GerenteCursoClienteRest {
    @GetMapping("/{id}")
    GerenteCurso findById(@PathVariable Long id);
}
