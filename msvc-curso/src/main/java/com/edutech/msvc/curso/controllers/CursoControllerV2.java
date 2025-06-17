package com.edutech.msvc.curso.controllers;

import com.edutech.msvc.curso.assemblers.CursoModelAssembler;
import com.edutech.msvc.curso.dtos.CursoDTO;
import com.edutech.msvc.curso.dtos.ErrorDTO;
import com.edutech.msvc.curso.models.entities.Curso;
import com.edutech.msvc.curso.repositories.CursoRepository;
import com.edutech.msvc.curso.services.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.hateoas.MediaTypes;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v3/cursos")
@Validated
@Tag(name = "Cursos", description = "Metodos CRUD para cursos")
public class CursoControllerV2 {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private CursoModelAssembler cursoModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Retorna todos los Curso",
               description = "Devuelve un Listado de 'Cursos' en caso "+
                              "de no encontrar retorna una lista vacia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se retornaron todos los cursos OK",
            content = @Content(
                      mediaType = MediaTypes.HAL_JSON_VALUE,
                      schema = @Schema(implementation = Curso.class)
            ))
    })
    public ResponseEntity<CollectionModel<EntityModel<Curso>>>findAll() {
        List<EntityModel<Curso>> entityModels = this.cursoService.findAll()
                .stream()
                .map(cursoModelAssembler::toModel)
                .toList();
        CollectionModel<EntityModel<Curso>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(CursoControllerV2.class).findAll()).withSelfRel()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Retorna un Curso respecto a su ID",
               description = "Devuelve un 'Curso' en caso "+
                             "de no encontrar retorna una lista vacia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "Se retorno un curso OK",
                         content = @Content(
                                   mediaType = MediaTypes.HAL_JSON_VALUE,
                                   schema = @Schema(implementation = Curso.class)
                         )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Error - Curso con ID no encontrado",
                         content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameters(value = {
            @Parameter(name = "id",
                       description = "Este es el id unico de un curso", required = true)
    })
    public ResponseEntity<EntityModel<Curso>> findById(@PathVariable Long id){
        EntityModel<Curso> entityModel = this.cursoModelAssembler.toModel(
                cursoService.findById(id)
        );
        return ResponseEntity.status(HttpStatus.OK).body(entityModel);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Endpoint que permite guardar un Curso",
            description = "Este endpoint manda un body con el formato Curso.class "+
                    "y permite la creacion de Curso"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                         description = "Curso creaco correctamente",
                         content = @Content(
                                 mediaType = MediaTypes.HAL_JSON_VALUE,
                                 schema =  @Schema(implementation = Curso.class)
                         )
            )
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Este debe ser el Json con los datos de Curso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Curso.class)
            )
    )
    public ResponseEntity<EntityModel<Curso>> create (@Valid @RequestBody Curso curso){
        Curso cursoNew = this.cursoService.save(curso);
        EntityModel<Curso> entityModel = this.cursoModelAssembler.toModel(cursoNew);
        return ResponseEntity
                .created(linkTo(methodOn(CursoControllerV2.class).findById(cursoNew.getIdCurso())).toUri())
                .body(entityModel);
    }

    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Endpoint que permite actualizar un Curso existente",
            description = "Este endpoint recibe un JSON con los datos actualizados del Curso " +
                    "y retorna el Curso modificado con los enlaces HATEOAS correspondientes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Curso actualizado correctamente",
                    content = @Content(mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Curso.class))),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado",
                    content = @Content)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Debe contener los campos del Curso a actualizar",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Curso.class))
    )
    public ResponseEntity<EntityModel<Curso>> update(
            @PathVariable Long id,
            @Valid @RequestBody Curso curso) {

        Curso updatedCurso = this.cursoService.update(id, curso);
        EntityModel<Curso> entityModel = this.cursoModelAssembler.toModel(updatedCurso);
        return ResponseEntity.ok(entityModel);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Curso> delete(@PathVariable Long id){
        this.cursoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }





}
