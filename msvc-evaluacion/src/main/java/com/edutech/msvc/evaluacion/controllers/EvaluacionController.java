package com.edutech.msvc.evaluacion.controllers;

import com.edutech.msvc.evaluacion.assemblers.EvaluacionModelAssembler;
import com.edutech.msvc.evaluacion.dtos.ErrorDTO;
import com.edutech.msvc.evaluacion.models.entities.Evaluacion;
import com.edutech.msvc.evaluacion.services.EvaluacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/evaluaciones")
@Validated
@Tag(name = "Evaluaciones", description = "Esta seccion contiene los cruds de las evaluaciones")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @Autowired
    private EvaluacionModelAssembler evaluacionModelAssembler;

    //BUSCAR TODAS LAS EVALUACIONES
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Buscar todas las evaluaciones",
            description = "Lista todas las evaluaciones existentes"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se listaron todas las evaluaciones correctamente",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Evaluacion.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Hubo algun error en el prompt a la hora de ingresarlo",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    } )
    public ResponseEntity<CollectionModel<EntityModel<Evaluacion>>>findAll(){
        List<EntityModel<Evaluacion>> entityModels =this.evaluacionService.findAll()
                .stream()
                .map(evaluacionModelAssembler::toModel)
                .toList();
        CollectionModel<EntityModel<Evaluacion>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(EvaluacionController.class).findAll()).withSelfRel()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    //BUSCAR UNA EVALUACION POR ID
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar una evaluacion mediante su id", description = "Lista una evaluacion mediante su id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se listaron todas las evaluaciones correctamente",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Evaluacion.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Hubo algun error en el prompt a la hora de ingresarlo",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    } )
    public ResponseEntity<EntityModel<Evaluacion>> findById(@PathVariable Long id){
        EntityModel<Evaluacion> entityModel =this.evaluacionModelAssembler.toModel(
                evaluacionService.findById(id)
        );
        return ResponseEntity.status(HttpStatus.OK).body(entityModel);
    }

    //GUARDAR UNA NUEVA EVALUACION
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Guardar evaluaciones", description = "Guarda nuevas evaluaciones con todas sus descripciones")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Se guardo la evaluacion correctamente",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Evaluacion.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Hubo algun error en el prompt a la hora de ingresarlo",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    } )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Json con los daros del profesor",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Evaluacion.class)
            )
    )
    public ResponseEntity<EntityModel<Evaluacion>> create (@Valid @RequestBody Evaluacion evaluacion){
        Evaluacion evaluacion1= this.evaluacionService.save(evaluacion);
        EntityModel<Evaluacion> evaluacionEntityModel=this.evaluacionModelAssembler.toModel(evaluacion1);
        return ResponseEntity
                .created(linkTo(methodOn(EvaluacionController.class).findById(evaluacion1.getIdEvaluacion())).toUri())
                .body(evaluacionEntityModel);
    }

    //ACTUALIZAR EVALUACION POR ID
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualiza una evaluacion",description = "Mediante el id actualiza una evaluacion para cambiar su contenido")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se listaron todas las evaluaciones correctamente",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Evaluacion.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Hubo algun error en el prompt a la hora de ingresarlo",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    } )
    public ResponseEntity<EntityModel<Evaluacion>> update(
            @PathVariable Long id,
            @Valid @RequestBody Evaluacion evaluacion) {
        Evaluacion evaluacion1 = this.evaluacionService.update(id,evaluacion);
        EntityModel<Evaluacion> entityModel = this.evaluacionModelAssembler.toModel(evaluacion1);
        return ResponseEntity.ok(entityModel);
    }

    //ELIMINAR EVALUACION POR ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una evaluacion", description = "mediante el id de la evaluacion elimina esta misma")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Se elimino correctamente"

            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Hubo algun error en el prompt a la hora de ingresarlo",
                    content = @Content
            )
    } )
    public ResponseEntity<Evaluacion> delete(@PathVariable Long id){
        this.evaluacionService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
