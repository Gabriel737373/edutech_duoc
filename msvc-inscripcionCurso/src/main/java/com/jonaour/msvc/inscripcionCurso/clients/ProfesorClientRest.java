package com.jonaour.msvc.inscripcionCurso.clients;

import com.jonaour.msvc.inscripcionCurso.models.Profesor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="msvc-profesor", url="localhost:8081/api/v1/profesores/")
public interface ProfesorClientRest {

    @GetMapping("/{id}")
    Profesor findById(@PathVariable Long id);

}