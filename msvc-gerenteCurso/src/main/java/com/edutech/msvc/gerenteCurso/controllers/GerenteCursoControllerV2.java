package com.edutech.msvc.gerenteCurso.controllers;

import com.edutech.msvc.gerenteCurso.assemblers.GerenteCursoModelAssembler;
import com.edutech.msvc.gerenteCurso.dtos.ErrorDTO;
import com.edutech.msvc.gerenteCurso.dtos.GerenteCursoDTO;
import com.edutech.msvc.gerenteCurso.models.entities.GerenteCurso;
import com.edutech.msvc.gerenteCurso.services.GerenteCursoService;
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
@RequestMapping("/api/v2/gerenteCursos")
@Validated
@Tag(name = "Gerente Cursos - V2", description = "Esta sección contiene los CRUD de gerentes")
public class GerenteCursoControllerV2 {

    @Autowired
    private GerenteCursoService gerenteCursoService;

    @Autowired
    private GerenteCursoModelAssembler gerenteCursoModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Retorna todos los gerentes",
            description = "Este metodo debe retornar un List de Gerente Cursos, en caso "+
                    "de que no encuentre nada retorna una List vacia"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se retornaron todos los Gerentes Ok",
            content = @Content(
                    mediaType = MediaTypes.HAL_JSON_VALUE,
                    schema = @Schema(implementation = GerenteCurso.class)
            ))
    })
    public ResponseEntity<CollectionModel<EntityModel<GerenteCurso>>>findAll() {
        List<EntityModel<GerenteCurso>> entityModels = this.gerenteCursoService.findAll()
                .stream()
                .map(gerenteCursoModelAssembler::toModel)
                .toList();
        CollectionModel<EntityModel<GerenteCurso>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(GerenteCursoControllerV2.class).findAll()).withSelfRel()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Retorna un gerente con respecto a su id",
            description = "Este metodo debe retornar un Gerente cuando es consultado "+
                    "mediante su id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se retorno un gerente OK",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = GerenteCurso.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Gerente no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameters(value = {
            @Parameter(name = "id",
                    description = "Este es el id unico de un gerente", required = true)
    })
    public ResponseEntity<EntityModel<GerenteCurso>> findById(@PathVariable Long id){
        EntityModel<GerenteCurso> entityModel = this.gerenteCursoModelAssembler.toModel(
                gerenteCursoService.findById(id)
        );
        return ResponseEntity.status(HttpStatus.OK).body(entityModel);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Endpoint que permite guardar un GerenteCurso",
            description = "Este endpoint manda un body con el formato GerenteCurso.class "+
                    "y permite la creacion de GerenteCurso"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                        description = "GerenteCurso creado correctamente",
                        content = @Content(
                                mediaType = MediaTypes.HAL_JSON_VALUE,
                                schema =  @Schema(implementation = GerenteCurso.class)
                        ))
    })

    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Este debe ser el Json con los datos de GerenteCurso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GerenteCurso.class)
            )
    )
    public ResponseEntity<EntityModel<GerenteCurso>> create (@Valid @RequestBody GerenteCurso gerenteCurso) {
        GerenteCurso gerenteNew = this.gerenteCursoService.save(gerenteCurso);
        EntityModel<GerenteCurso> entityModel = this.gerenteCursoModelAssembler.toModel(gerenteNew);
        return ResponseEntity
                .created(linkTo(methodOn(GerenteCursoControllerV2.class).findById(gerenteNew.getIdGerenteCurso())).toUri())
                .body(entityModel);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Endpoint que permite actualizar un GerenteCurso existente",
            description = "Este endpoint recibe un JSON con los datos actualizados del GerenteCurso " +
                    "y retorna el Gerente modificado con los enlaces HATEOAS correspondientes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gerente actualizado correctamente",
                    content = @Content(mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = GerenteCurso.class))),
            @ApiResponse(responseCode = "404", description = "Gerente no encontrado",
                    content = @Content)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Debe contener los campos del GerenteCurso a actualizar",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = GerenteCurso.class))
    )
    public ResponseEntity<EntityModel<GerenteCurso>> update(
            @PathVariable Long id,
            @Valid @RequestBody GerenteCurso gerenteCurso) {

        GerenteCurso updatedCurso = this.gerenteCursoService.update(id, gerenteCurso);
        EntityModel<GerenteCurso> entityModel = this.gerenteCursoModelAssembler.toModel(updatedCurso);
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un GerenteCurso por ID",
            description = "Este Endpoint permite eliminar un GerenteCurso existente a través de su ID. " +
                    "Si el gerente es eliminado exitosamente, retorna un código 204 sin contenido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Gerente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Gerente no encontrado",
                    content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.gerenteCursoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
