package com.edutech.msvc.alumno.controllers;



import com.edutech.msvc.alumno.dtos.AlumnoDTO;
import com.edutech.msvc.alumno.models.Alumno;
import com.edutech.msvc.alumno.services.AlumnoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alumnos")
@Validated
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping
    public ResponseEntity<List<Alumno>> findAll(){
        List<Alumno> alumnos = this.alumnoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(alumnos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> findById(@PathVariable Long id){
        Alumno alumno = this.alumnoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(alumno);
    }

    @PostMapping
    public ResponseEntity<Alumno> save(@Valid @RequestBody AlumnoDTO alumnoDTO){
        Alumno alumno = new Alumno();
        alumno.setNombreCompleto(alumnoDTO.getNombreCompleto());
        alumno.setCorreo(alumnoDTO.getCorreo());
        Alumno saved = alumnoService.save(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alumno> update(@PathVariable Long id, @Valid @RequestBody AlumnoDTO alumnoDTO){
        Alumno alumno = new Alumno();
        alumno.setNombreCompleto(alumnoDTO.getNombreCompleto());
        alumno.setCorreo(alumnoDTO.getCorreo());
        Alumno updated = alumnoService.update(id, alumno);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Alumno> delete(@PathVariable Long id){
        this.alumnoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
