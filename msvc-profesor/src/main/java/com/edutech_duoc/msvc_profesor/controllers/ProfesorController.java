package com.edutech_duoc.msvc_profesor.controllers;

import com.edutech_duoc.msvc_profesor.dtos.ErrorDTO;
import com.edutech_duoc.msvc_profesor.dtos.ProfesorDTO;
import com.edutech_duoc.msvc_profesor.models.entities.Profesor;
import com.edutech_duoc.msvc_profesor.services.ProfesorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profesores")
@Validated
@Tag(name = "Profesores V1", description = "esta seccion tiene los crud de profesor")
public class ProfesorController {
    @Autowired
    private ProfesorService profesorService;

    @GetMapping
    @Operation(
            summary = "Devuelve todos los Profesores",
            description = "Este metodo retorna una lista de Profesores, en caso "+
                    "de que no encuentre nada, retorta una lista vacia"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Se retornaron todos los Profesores OK")
    })
    public ResponseEntity<List<Profesor>> findAll(){
        List<Profesor> profesores = this.profesorService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(profesores);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Devuelve un Profesor respecto a su ID",
            description = "Este metodo retorna un Profesor cuando es consultado mediante su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se retorna el Profesor encontrado"),
            @ApiResponse(responseCode = "404", description = "Error - Profesor con ID no se encuentra en la base de datos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "Este es el ID unico de un Profesor", required = true)
    })
    public ResponseEntity<Profesor> findById(@PathVariable Long id){
        Profesor profesor = this.profesorService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(profesor);
    }

    @PostMapping
    @Operation(
            summary = "Endpoint que permite guardar un Profesor",
            description = "Este endpoint manda un body con el formato Profesor.class "+
                    "y permite la creacion de un Profesor"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Profesor creado correctamente"),
            @ApiResponse(
                    responseCode = "409",
                    description = "El profesor ya existe",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Este debe ser el Json con los datos de un Profesor",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Profesor.class)
            )
    )
    public ResponseEntity<Profesor> save(@Valid @RequestBody ProfesorDTO profesorDTO){
        Profesor profesor = new Profesor();
        profesor.setNombreProfesor(profesorDTO.getNombreProfesor());
        profesor.setApellidoProfesor(profesorDTO.getApellidoProfesor());
        profesor.setCorreoProfesor(profesorDTO.getCorreoProfesor());
        Profesor saved = profesorService.save(profesor);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Endpoint que permite actualizar un Profesor existente",
            description = "Este endpoint recibe un JSON con los datos actualizados de Profesor " +
                    "y retorna el Profesor modificado con los enlaces HATEOAS correspondientes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profesor actualizado correctamente",
                    content = @Content(mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Profesor.class))),
            @ApiResponse(responseCode = "404", description = "Error - Profesor con ID no se encuentra en la base de datos",
                    content = @Content)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Debe contener los campos del Profesor a actualizar",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Profesor.class))
    )
    public ResponseEntity<Profesor> update(@PathVariable Long id, @Valid @RequestBody ProfesorDTO profesorDTO){
        Profesor profesor = new Profesor();
        profesor.setNombreProfesor(profesorDTO.getNombreProfesor());
        profesor.setApellidoProfesor(profesorDTO.getApellidoProfesor());
        profesor.setCorreoProfesor(profesorDTO.getCorreoProfesor());
        Profesor updated = profesorService.update(id, profesor);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un Profesor por ID",
            description = "Este endpoint permite eliminar un Profesor existente a través de su ID. " +
                    "Si el Profesor es eliminado exitosamente, retorna un código 204 sin contenido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profesor eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Error - Profesor con ID no encontrado",
                    content = @Content)
    })
    public ResponseEntity<Profesor> delete(@PathVariable Long id){
        this.profesorService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
