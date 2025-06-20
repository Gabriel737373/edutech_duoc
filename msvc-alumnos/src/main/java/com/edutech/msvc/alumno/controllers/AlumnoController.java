package com.edutech.msvc.alumno.controllers;



import com.edutech.msvc.alumno.dtos.AlumnoDTO;
import com.edutech.msvc.alumno.dtos.ErrorDTO;
import com.edutech.msvc.alumno.models.entities.Alumno;
import com.edutech.msvc.alumno.services.AlumnoService;
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
@RequestMapping("/api/v1/alumnos")
@Validated
@Tag(name = "Alumno", description = "Metodos CRUD para Alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping
    @Operation(
            summary = "Devuelve todos los Alumnos",
            description = "Este metodo retorna una lista de Alumnos, en caso "+
                    "de que no encuentre nada, retorta una lista vacia"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Se retornaron todos los Alumnos OK")
    })
    public ResponseEntity<List<Alumno>> findAll(){
        List<Alumno> alumnos = this.alumnoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(alumnos);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Devuelve un Alumno respecto a su ID",
            description = "Este metodo retorna un Alumno cuando es consultado mediante su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se retorna el Alumno encontrado"),
            @ApiResponse(responseCode = "404", description = "Error - Alumno con ID no se encuentra en la base de datos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "Este es el ID unico de un Alumno", required = true)
    })
    public ResponseEntity<Alumno> findById(@PathVariable Long id){
        Alumno alumno = this.alumnoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(alumno);
    }

    @PostMapping
    @Operation(
            summary = "Endpoint que permite guardar un Alumno",
            description = "Este endpoint manda un body con el formato Alumno.class "+
                    "y permite la creacion de un Alumno"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alumno creado correctamente")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Este debe ser el Json con los datos de un Alumno",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Alumno.class)
            )
    )
    public ResponseEntity<Alumno> save(@Valid @RequestBody AlumnoDTO alumnoDTO){
        Alumno alumno = new Alumno();
        alumno.setNombreCompleto(alumnoDTO.getNombreCompleto());
        alumno.setCorreo(alumnoDTO.getCorreo());
        Alumno saved = alumnoService.save(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Endpoint que permite actualizar un Alumno existente",
            description = "Este endpoint recibe un JSON con los datos actualizados de Alumno " +
                    "y retorna el Alumno modificado con los enlaces HATEOAS correspondientes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alumno actualizado correctamente",
                    content = @Content(mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Alumno.class))),
            @ApiResponse(responseCode = "404", description = "Error - Alumno con ID no se encuentra en la base de datos",
                    content = @Content)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Debe contener los campos del Alumno a actualizar",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Alumno.class))
    )
    public ResponseEntity<Alumno> update(@PathVariable Long id, @Valid @RequestBody AlumnoDTO alumnoDTO){
        Alumno alumno = new Alumno();
        alumno.setNombreCompleto(alumnoDTO.getNombreCompleto());
        alumno.setCorreo(alumnoDTO.getCorreo());
        Alumno updated = alumnoService.update(id, alumno);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un Alumno por ID",
            description = "Este endpoint permite eliminar un Alumno existente a través de su ID. " +
                    "Si el Alumno es eliminado exitosamente, retorna un código 204 sin contenido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alumno eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Error - Alumno con ID no encontrado",
                    content = @Content)
    })
    public ResponseEntity<Alumno> delete(@PathVariable Long id){
        this.alumnoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
