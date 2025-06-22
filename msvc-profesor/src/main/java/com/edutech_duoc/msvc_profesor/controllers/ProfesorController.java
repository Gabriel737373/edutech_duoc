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
            @ApiResponse(responseCode = "200", description = "Se listaron todos los medicos correctamente"),
            @ApiResponse(responseCode = "404", description = "Hubo algun error en el prompt a la hora de ingresarlo")
    } )
    public ResponseEntity<List<Profesor>> findAll(){
        List<Profesor> profesores = this.profesorService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(profesores);

    }

    //LISTAR POR ID DE PROFESOR
    @GetMapping("/{id}")
    @Operation(summary = "Listar a 1 profesor mediante su id", description = "Lista unicamente a un profesor para conocer sus datos" + " ,en caso de error no mostraria nada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se listo al profesor correctamente"),
            @ApiResponse(responseCode = "404", description = "Hubo algun error en el prompt a la hora de buscar al profesor")
    })
    public ResponseEntity<Profesor> findById(@PathVariable Long id){
        Profesor profesor = this.profesorService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(profesor);
    }

    //GUARDAR
    @PostMapping()
    @Operation(summary = "Guarda a un nuevo profesor", description = "Guarda a un nuevo profesor con toda su informacion correspondiente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Peticion realizada con exito"),
            @ApiResponse(responseCode = "404", description = "Hubo algun error en el prompt a la hora de buscar al profesor")
    })
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
    @Operation(summary = "Actualiza a un profesor", description = "Acctualiza la informacion de un profesor mediante el id de este mismo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Peticion realizada con exito"),
            @ApiResponse(responseCode = "404", description = "Hubo algun error en el prompt a la hora de buscar al profesor")
    })
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
    @Operation(summary = "Elimina a un profesor", description = "Mediante el id elimina a un profesor de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Peticion realizada con exito"),
            @ApiResponse(responseCode = "404", description = "Hubo algun error en el prompt a la hora de buscar al profesor")
    })
    public ResponseEntity<Profesor> delete(@PathVariable Long id){
        this.profesorService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
