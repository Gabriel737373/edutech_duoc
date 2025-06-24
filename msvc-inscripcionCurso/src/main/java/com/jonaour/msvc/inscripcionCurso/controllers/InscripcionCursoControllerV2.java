package com.jonaour.msvc.inscripcionCurso.controllers;

import com.jonaour.msvc.inscripcionCurso.assemblers.InscripcionCursoModelAssembler;
import com.jonaour.msvc.inscripcionCurso.dtos.ErrorDTO;
import com.jonaour.msvc.inscripcionCurso.models.entities.InscripcionCurso;
import com.jonaour.msvc.inscripcionCurso.services.InscripcionCursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/inscripcionesCursos")
@Validated
@Tag(name = "InscripcionesCurso-V2",description = "Metodos CRUD para inscripcion curso")
public class InscripcionCursoControllerV2 {

    @Autowired
    private InscripcionCursoService inscripcionCursoService;

    @Autowired
    private InscripcionCursoModelAssembler inscripcionCursoModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Devuelve todas las inscripciones de curso",
            description = "Este metodo retorna una lista de inscripciones curso, en caso"+
                    " de que no encuentre nada, retorna una lista vacía"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se retornaron todas las inscripciones de curso")
    })
    public ResponseEntity<CollectionModel<EntityModel<InscripcionCurso>>> findAll() {
        List<EntityModel<InscripcionCurso>> entityModels = this.inscripcionCursoService.findAll()
                .stream()
                .map(inscripcionCursoModelAssembler::toModel)
                .toList();
        CollectionModel<EntityModel<InscripcionCurso>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(InscripcionCursoControllerV2.class).findAll()).withSelfRel()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Devuelve una inscripcion curso respecto a su ID",
            description = "Este metodo retorna una inscripcion curso cuando es consultado mediante su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se retorno una inscripcion curso OK",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = InscripcionCurso.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Error - Inscripcion curso con ID no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "Este es el ID unico de una inscripcion curso", required = true)
    })
    public ResponseEntity<EntityModel<InscripcionCurso>> findById(@PathVariable Long id) {
        EntityModel<InscripcionCurso> entityModel=this.inscripcionCursoModelAssembler.toModel(
                inscripcionCursoService.findById(id)
        );
        return ResponseEntity.status(HttpStatus.OK).body(entityModel);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Endpoint que permite guardar una inscripcion curso",
            description = "Este endpoint manda un body con el formato InscripcionCurso.class "+
                    "y permite la creacion de una inscripcion curso"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
            description = "Inscripcion curso creado correctamente",
            content = @Content(
                    mediaType = MediaTypes.HAL_JSON_VALUE,
                    schema = @Schema(implementation = InscripcionCurso.class)
            ))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Este debe ser el Json con los datos de una inscripcion curso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = InscripcionCurso.class)
            )
    )
    public ResponseEntity<EntityModel<InscripcionCurso>> save(@Valid @RequestBody InscripcionCurso inscripcionCurso) {
        InscripcionCurso inscripcionCursoNew = this.inscripcionCursoService.save(inscripcionCurso);
        EntityModel<InscripcionCurso> entityModel=this.inscripcionCursoModelAssembler.toModel(inscripcionCursoNew);
        return ResponseEntity
                .created(linkTo(methodOn(InscripcionCursoControllerV2.class).findById(inscripcionCursoNew.getIdInscripcionCurso())).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una inscripcion curso por ID",
            description = "Este endpoint permite eliminar una inscripcion curso existente a través de su ID. " +
                    "Si la inscripcion curso es eliminado exitosamente, retorna un código 204 sin contenido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Inscripcion curso eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Error - Inscripcion curso con ID no encontrado",
                    content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.inscripcionCursoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Endpoint que permite actualizar una inscripcion curso existente",
            description = "Este endpoint recibe un JSON con los datos actualizados de una inscripcion curso " +
                    "y retorna la inscripcion curso modificado con los enlaces HATEOAS correspondientes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscripcion curso actualizado correctamente",
                    content = @Content(mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = InscripcionCurso.class))),
            @ApiResponse(responseCode = "404", description = "Error - Inscripcion curso con ID no encontrado",
                    content = @Content)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Debe contener los campos de la inscripcion curso a actualizar",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = InscripcionCurso.class))
    )
    public ResponseEntity<EntityModel<InscripcionCurso>> update(
            @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody InscripcionCurso inscripcionCurso) {

        InscripcionCurso updatedInscripcion = this.inscripcionCursoService.update(id, inscripcionCurso);
        EntityModel<InscripcionCurso> entityModel = this.inscripcionCursoModelAssembler.toModel(updatedInscripcion);
        return ResponseEntity.ok(entityModel);
    }

}
