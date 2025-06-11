package com.edutech_duoc.msvc_profesor.controllers;

import com.edutech_duoc.msvc_profesor.dtos.ProfesorDTO;
import com.edutech_duoc.msvc_profesor.models.entities.Profesor;
import com.edutech_duoc.msvc_profesor.services.ProfesorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/profesores")
@Validated
@Tag(name = "Profesores", description = "Esta seccion contiene los cruds de los profesores")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    //LISTAR TODOS
    @GetMapping
    @Operation(summary = "Lista todos los profesores", description = "Lista todo los profesores" + " ,en caso de error no mostraria nada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listaron todos los medicos correctamente")
    })
    public ResponseEntity<List<Profesor>> findAll(){
        List<Profesor> profesores = this.profesorService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(profesores);

    }

    //LISTAR POR ID DE PROFESOR
    @GetMapping("/{id}")
    public ResponseEntity<Profesor> findById(@PathVariable Long id){
        Profesor profesor = this.profesorService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(profesor);
    }

    //GUARDAR
    @PostMapping()
    public ResponseEntity<Profesor> save(@Valid @RequestBody ProfesorDTO profesorDTO){
        Profesor profesor = new Profesor();
        profesor.setNombreProfesor(profesorDTO.getNombreProfesor());
        profesor.setApellidoProfesor(profesorDTO.getApellidoProfesor());
        profesor.setCorreoProfesor(profesorDTO.getCorreoProfesor());
        Profesor saved = profesorService.save(profesor);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    //ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<Profesor> update(@PathVariable Long id, @Valid @RequestBody ProfesorDTO profesorDTO) {
        Profesor profesor = new Profesor();
        profesor.setNombreProfesor(profesorDTO.getNombreProfesor());
        profesor.setApellidoProfesor(profesorDTO.getApellidoProfesor());
        profesor.setCorreoProfesor(profesorDTO.getCorreoProfesor());
        Profesor updated = profesorService.update(id, profesor);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    //ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Profesor> delete(@PathVariable Long id){
        this.profesorService.deleteById(id);
        //Map<String, String> response = Collections.singletonMap("Profesor eliminado correctamente","Profesor eliminado correctamente");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
