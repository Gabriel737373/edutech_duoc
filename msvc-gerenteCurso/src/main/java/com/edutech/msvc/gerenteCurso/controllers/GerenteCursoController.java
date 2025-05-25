package com.edutech.msvc.gerenteCurso.controllers;

import com.edutech.msvc.gerenteCurso.dtos.GerenteCursoDTO;
import com.edutech.msvc.gerenteCurso.models.entities.GerenteCurso;
import com.edutech.msvc.gerenteCurso.services.GerenteCursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gerenteCursos")
@Validated
public class GerenteCursoController {

    @Autowired
    private GerenteCursoService gerenteCursoService;

    @GetMapping
    public ResponseEntity<List<GerenteCurso>> findAll() {
        List<GerenteCurso> gerenteCursos = gerenteCursoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(gerenteCursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GerenteCurso> findById(@PathVariable Long id) {
        GerenteCurso gerenteCurso = this.gerenteCursoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(gerenteCurso);
    }

    @PostMapping
    public ResponseEntity<GerenteCurso> save(@RequestBody GerenteCursoDTO gerenteCursoDTO) {
        GerenteCurso gerenteCurso = new GerenteCurso();
        gerenteCurso.setNombreCompleto(gerenteCursoDTO.getNombreCompleto());
        GerenteCurso saved = gerenteCursoService.save(gerenteCurso);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GerenteCurso> update(@PathVariable Long id, @RequestBody GerenteCursoDTO gerenteCursoDTO) {
        GerenteCurso gerenteCurso = new GerenteCurso();
        gerenteCurso.setNombreCompleto(gerenteCursoDTO.getNombreCompleto());
        GerenteCurso updated = gerenteCursoService.update(id, gerenteCurso);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GerenteCurso> delete(@PathVariable Long id) {
        this.gerenteCursoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
