package com.edutech.msvc.resenias.clients;

import com.jonaour.msvc.inscripcionCurso.models.Curso;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="msvc-curso", url="localhost:8084/api/v2/cursos")
public interface CursoClientRest {

    @GetMapping("/{id}")
    Curso findById(@PathVariable Long id);

}