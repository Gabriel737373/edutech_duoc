package com.edutech.msvc.curso.controllers;

import com.edutech.msvc.curso.dtos.CursoDTO;
import com.edutech.msvc.curso.models.Curso;
import com.edutech.msvc.curso.services.CursoService;
import com.edutech.msvc.curso.services.CursoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/cursos")
@Validated
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<Curso>> findAll() {
        List<Curso> cursos = this.cursoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(cursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> findById(@PathVariable Long id){
        Curso curso = this.cursoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(curso);
    }

    @PostMapping
    public ResponseEntity<Curso> save(@Valid @RequestBody CursoDTO cursoDTO){
        Curso curso = new Curso();
        curso.setNombreCurso(cursoDTO.getNombreCurso());
        curso.setDescripcionCurso(cursoDTO.getDescripcionCurso());
        curso.setPrecioCurso(cursoDTO.getPrecioCurso());
        Curso saved = cursoService.save(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> update(@PathVariable Long id, @Valid @RequestBody CursoDTO cursoDTO){
        Curso curso = new Curso();
        curso.setNombreCurso(cursoDTO.getNombreCurso());
        curso.setDescripcionCurso(cursoDTO.getDescripcionCurso());
        curso.setPrecioCurso(cursoDTO.getPrecioCurso());
        Curso updated = cursoService.update(id, curso);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Curso> delete(@PathVariable Long id){
        this.cursoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }





}
