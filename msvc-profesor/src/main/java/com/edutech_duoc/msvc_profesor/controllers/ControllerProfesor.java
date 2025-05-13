package com.edutech_duoc.msvc_profesor.controllers;



import com.edutech_duoc.msvc_profesor.dtos.ProfesorDTO;
import com.edutech_duoc.msvc_profesor.models.Profesor;
import com.edutech_duoc.msvc_profesor.services.ProfesorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profesores")
@Validated
public class ControllerProfesor {

    @Autowired
    private ProfesorService profesorService;

    @GetMapping
    public ResponseEntity<List<Profesor>> findAll(){
        List<Profesor> profesores = this.profesorService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(profesores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesor> save(@Valid @RequestBody ProfesorDTO profesorDTO){
        Profesor profesor = new Profesor();
        profesor.setNombreProfesor(profesorDTO.getNombreProfesor());
        profesor.setApellidoProfesor(profesorDTO.getApellidoProfesor());
        profesor.setCorreoProfesor(profesorDTO.getCorreoProfesor());
        Profesor saved = profesorService.save(profesor);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);

    }

    @PostMapping("/{id}")
    public ResponseEntity<Profesor> update(@PathVariable Long id, @Valid @RequestBody ProfesorDTO profesorDTO) {
        Profesor profesor = new Profesor();
        profesor.setNombreProfesor(profesorDTO.getNombreProfesor());
        profesor.setApellidoProfesor(profesorDTO.getApellidoProfesor());
        profesor.setCorreoProfesor(profesorDTO.getCorreoProfesor());
        Profesor updated = profesorService.update(id, profesor);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Profesor> delete(@PathVariable Long id){
        this.profesorService.deleteByID(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
