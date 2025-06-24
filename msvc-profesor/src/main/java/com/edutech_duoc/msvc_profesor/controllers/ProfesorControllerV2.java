package com.edutech_duoc.msvc_profesor.controllers;

import com.edutech_duoc.msvc_profesor.assemblers.ProfesorModelAssembler;
import com.edutech_duoc.msvc_profesor.dtos.ErrorDTO;
import com.edutech_duoc.msvc_profesor.models.entities.Profesor;
import com.edutech_duoc.msvc_profesor.services.ProfesorService;
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
import org.springframework.http.HttpStatus;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/profesores")
@Validated
@Tag(name = "Profesores V2", description = "Esta seccion contiene los cruds de los profesores")
public class ProfesorControllerV2 {

    @Autowired
    private ProfesorService profesorService;

    @Autowired
    private ProfesorModelAssembler profesorModelAssembler;

    //LISTAR TODOS
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Lista todos los profesores",
            description = "Lista todo los profesores" + " ,en caso de error no mostraria nada"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se listaron todos los profesores correctamente",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Profesor.class)
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
    public ResponseEntity<CollectionModel<EntityModel<Profesor>>>findAll(){
        List<EntityModel<Profesor>> entityModels =this.profesorService.findAll()
                .stream()
                .map(profesorModelAssembler::toModel)
                .toList();
        CollectionModel<EntityModel<Profesor>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(ProfesorControllerV2.class).findAll()).withSelfRel()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    //LISTAR POR ID DE PROFESOR
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Listar a 1 profesor mediante su id",
            description = "Lista unicamente a un profesor para conocer sus datos" + " ,en caso de error no mostraria nada"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se listo el profesor correctamente",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Profesor.class)
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
    public ResponseEntity<EntityModel<Profesor>> findById(@PathVariable Long id){
        EntityModel<Profesor> entityModel =this.profesorModelAssembler.toModel(
                profesorService.findById(id)
        );
        return ResponseEntity.status(HttpStatus.OK).body(entityModel);
    }

    //GUARDAR
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Guarda a un nuevo profesor",
            description = "Guarda a un nuevo profesor con toda su informacion correspondiente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Se guardo el Profesor correctamente",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Profesor.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Hubo algun error en el prompt a la hora de ingresarlo",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El usuario ya existe",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            )
    } )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Json con los datos del profesor",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Profesor.class)
            )
    )
    public ResponseEntity<EntityModel<Profesor>> create (@Valid @RequestBody Profesor profesor){
        Profesor profesorNew = this.profesorService.save(profesor);
        EntityModel<Profesor> entityModel =this.profesorModelAssembler.toModel(profesorNew);
        return ResponseEntity
                .created(linkTo(methodOn(ProfesorControllerV2.class).findById(profesorNew.getIdProfesor())).toUri())
                .body(entityModel);
    }

    //ACTUALIZAR
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Actualiza a un profesor",
            description = "Acctualiza la informacion de un profesor mediante el id de este mismo")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "profesores actualizado correctamente",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Profesor.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Hubo algun error en el prompt a la hora de ingresarlo",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)
                    )
            ),

    } )
    public ResponseEntity<EntityModel<Profesor>> update(
            @PathVariable Long id,
            @Valid @RequestBody Profesor profesor) {
        Profesor profesor1 = this.profesorService.update(id,profesor);
        EntityModel<Profesor> entityModel = this.profesorModelAssembler.toModel(profesor1);
        return ResponseEntity.ok(entityModel);
    }

    //ELIMINAR
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Elimina a un profesor",
            description = "Mediante el id elimina a un profesor de la base de datos")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "profesor eliminado correctamente"

            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Hubo algun error en el prompt a la hora de ingresarlo",
                    content = @Content
            )

    } )
    public ResponseEntity<Profesor> delete(@PathVariable Long id){
        this.profesorService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
