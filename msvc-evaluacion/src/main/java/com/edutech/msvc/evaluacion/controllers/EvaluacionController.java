package com.edutech.msvc.evaluacion.controllers;

import com.edutech.msvc.evaluacion.dtos.EvaluacionDTO;
import com.edutech.msvc.evaluacion.models.entities.Evaluacion;
import com.edutech.msvc.evaluacion.services.EvaluacionService;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/evaluaciones")
@Validated
@Tag(name = "Evaluaciones", description = "Esta seccion contiene los cruds de las evaluaciones")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    //BUSCAR TODAS LAS EVALUACIONES
    @GetMapping
    @Operation(summary = "Buscar todas las evaluaciones", description = "Lista todas las evaluaciones existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Peticion realizada con exito"),
            @ApiResponse(responseCode = "404", description = "Hubo algun error en el prompt a la hora de buscar al profesor")
    })
    public ResponseEntity<List<Evaluacion>> findAll(){
        List<Evaluacion> evaluaciones = this.evaluacionService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(evaluaciones);

    }

    //BUSCAR UNA EVALUACION POR ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar una evaluacion mediante su id", description = "Lista una evaluacion mediante su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Peticion realizada con exito"),
            @ApiResponse(responseCode = "404", description = "Hubo algun error en el prompt a la hora de buscar al profesor")
    })
    public ResponseEntity<Evaluacion> findById(@PathVariable Long id){
        Evaluacion evaluacion = this.evaluacionService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(evaluacion);
    }

    //GUARDAR UNA NUEVA EVALUACION
    @PostMapping()
    @Operation(summary = "Guardar evaluaciones", description = "Guarda nuevas evaluaciones con todas sus descripciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Peticion realizada con exito"),
            @ApiResponse(responseCode = "404", description = "Hubo algun error en el prompt a la hora de buscar al profesor")
    })
    public ResponseEntity<Evaluacion> save(@Valid @RequestBody EvaluacionDTO evaluacionDTO){
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setNombreEvaluacion(evaluacionDTO.getNombreEvaluacion());
        evaluacion.setMateriaEvaluacion(evaluacionDTO.getMateriaEvaluacion());
        Evaluacion saved = evaluacionService.save(evaluacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);

    }

    //ACTUALIZAR EVALUACION POR ID
    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una evaluacion",description = "Mediante el id actualiza una evaluacion para cambiar su contenido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Peticion realizada con exito"),
            @ApiResponse(responseCode = "404", description = "Hubo algun error en el prompt a la hora de buscar al profesor")
    })
    public ResponseEntity<Evaluacion> update(@PathVariable Long id, @Valid @RequestBody EvaluacionDTO evaluacionDTO){
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setNombreEvaluacion(evaluacionDTO.getNombreEvaluacion());
        evaluacion.setMateriaEvaluacion(evaluacionDTO.getMateriaEvaluacion());
        Evaluacion updated = evaluacionService.update(id, evaluacion);
        return ResponseEntity.status(HttpStatus.OK).body(updated);

    }

    //ELIMINAR EVALUACION POR ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una evaluacion", description = "mediante el id de la evaluacion elimina esta misma")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Peticion realizada con exito"),
            @ApiResponse(responseCode = "404", description = "Hubo algun error en el prompt a la hora de buscar al profesor")
    })
    public ResponseEntity<Evaluacion> delete(@PathVariable Long id){
        this.evaluacionService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
