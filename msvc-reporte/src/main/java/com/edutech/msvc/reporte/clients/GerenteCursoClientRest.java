package com.edutech.msvc.reporte.clients;

import com.edutech.msvc.reporte.models.GerenteCurso;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="msvc-gerenteCurso", url = "localhost:8088/api/v1/gerenteCursos/")
public interface GerenteCursoClientRest {

    @GetMapping("/{id}")
    GerenteCurso findById(@PathVariable Long id);
}
