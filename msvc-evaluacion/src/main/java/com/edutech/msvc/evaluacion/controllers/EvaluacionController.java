package com.edutech.msvc.evaluacion.controllers;

import com.edutech.msvc.evaluacion.dtos.ErrorDTO;
import com.edutech.msvc.evaluacion.dtos.EvaluacionDTO;
import com.edutech.msvc.evaluacion.models.entities.Evaluacion;
import com.edutech.msvc.evaluacion.services.EvaluacionService;
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
@RequestMapping("/api/v1/evaluaciones")
@Validated
@Tag(name = "Evaluaciones", description = "esta seccion va a mostrar los crud de evaluaciones")
public class EvaluacionController {
    @Autowired
    private EvaluacionService evaluacionService;

    @GetMapping
    @Operation(
            summary = "Devuelve todas las Evaluaciones",
            description = "Este metodo retorna una lista de Evaluaciones, en caso "+
                    "de que no encuentre nada, retorta una lista vacia"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Se retornaron todas las Evaluaciones OK")
    })
    public ResponseEntity<List<Evaluacion>> findAll(){
        List<Evaluacion> evaluaciones = this.evaluacionService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(evaluaciones);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Devuelve una Evaluacion respecto a su ID",
            description = "Este metodo retorna una Evaluacion cuando es consultado mediante su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se retorna la Evaluacion encontrada"),
            @ApiResponse(responseCode = "404", description = "Error - Evaluacion con ID no se encuentra en la base de datos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "Este es el ID unico de una Evaluacion", required = true)
    })
    public ResponseEntity<Evaluacion> findById(@PathVariable Long id){
        Evaluacion evaluacion = this.evaluacionService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(evaluacion);
    }

    @PostMapping
    @Operation(
            summary = "Endpoint que permite guardar una Evaluacion",
            description = "Este endpoint manda un body con el formato Evaluacion.class "+
                    "y permite la creacion de una Evaluacion"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evaluacion creada correctamente")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Este debe ser el Json con los datos de una Evaluacion",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Evaluacion.class)
            )
    )
    public ResponseEntity<Evaluacion> save(@Valid @RequestBody EvaluacionDTO evaluacionDTO){
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setMateriaEvaluacion(evaluacionDTO.getMateriaEvaluacion());
        evaluacion.setNombreEvaluacion(evaluacionDTO.getMateriaEvaluacion());
        Evaluacion saved = evaluacionService.save(evaluacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Endpoint que permite actualizar una Evaluacion existente",
            description = "Este endpoint recibe un JSON con los datos actualizados de Evaluacion " +
                    "y retorna la Evaluacion modificada con los enlaces HATEOAS correspondientes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evaluacion actualizada correctamente",
                    content = @Content(mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Evaluacion.class))),
            @ApiResponse(responseCode = "404", description = "Error - Evaluacion con ID no se encuentra en la base de datos",
                    content = @Content)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Debe contener los campos de la Evaluacion a actualizar",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Evaluacion.class))
    )
    public ResponseEntity<Evaluacion> update(@PathVariable Long id, @Valid @RequestBody EvaluacionDTO evaluacionDTO){
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setMateriaEvaluacion(evaluacionDTO.getMateriaEvaluacion());
        evaluacion.setNombreEvaluacion(evaluacionDTO.getMateriaEvaluacion());
        Evaluacion updated = evaluacionService.update(id, evaluacion);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una Evaluacion por ID",
            description = "Este endpoint permite eliminar una Evaluacion existente a través de su ID. " +
                    "Si la Evaluacion es eliminada exitosamente, retorna un código 204 sin contenido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Evaluacion eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Error - Evaluacion con ID no encontrado",
                    content = @Content)
    })
    public ResponseEntity<Evaluacion> delete(@PathVariable Long id){
        this.evaluacionService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
