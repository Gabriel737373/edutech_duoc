package com.edutech.msvc.resenias.controllers;

import com.edutech.msvc.resenias.assemblers.ReseniasModelAssembler;
import com.edutech.msvc.resenias.models.entities.Resenia;
import com.edutech.msvc.resenias.services.ReseniaService;
import com.jonaour.msvc.inscripcionCurso.controllers.InscripcionCursoControllerV2;
import com.jonaour.msvc.inscripcionCurso.dtos.ErrorDTO;
import com.jonaour.msvc.inscripcionCurso.models.entities.InscripcionCurso;
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
@RequestMapping("/api/v2/resenias")
@Validated
@Tag(name = "Resenias - V2", description = "Metodos CRUD para reseñas")
public class ReseniaControllerV2 {

    @Autowired
    private ReseniaService reseniaService;

    @Autowired
    private ReseniasModelAssembler reseniasModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Devuelve todas las reseñas",
            description = "Este metodo retorna una lista de reseñas, en caso"+
                    " de que no encuentre nada, retorna una lista vacía"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se retornaron todas las reseñas")
    })
    public ResponseEntity<CollectionModel<EntityModel<Resenia>>> findAll() {
        List<EntityModel<Resenia>> entityModels = this.reseniaService.findAll()
                .stream()
                .map(reseniasModelAssembler::toModel)
                .toList();
        CollectionModel<EntityModel<Resenia>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(ReseniaControllerV2.class).findAll()).withSelfRel()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Devuelve una reseña respecto a su ID",
            description = "Este metodo retorna una reseña cuando es consultado mediante su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se retorno una reseña OK",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Resenia.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Error - Reseña con ID no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "Este es el ID unico de una reseña", required = true)
    })
    public ResponseEntity<EntityModel<Resenia>> findById(@PathVariable Long id) {
        EntityModel<Resenia> entityModel=this.reseniasModelAssembler.toModel(
                reseniaService.findById(id)
        );
        return ResponseEntity.status(HttpStatus.OK).body(entityModel);
    }

    @PostMapping
    public ResponseEntity<Resenia> save(@RequestBody @Valid Resenia resenia){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.reseniaService.save(resenia));
    }

    //Metodo para filtrar reseñias hechas por un alumno

    @GetMapping("/alumno/{id}")
    public ResponseEntity<List<Resenia>> findByIdAlumno(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.reseniaService.findByAlumnoId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Resenia> delete(@PathVariable Long id){
        this.reseniaService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resenia> update(@PathVariable Long id, @Valid @RequestBody Resenia reseniaNEW){
        Resenia resenia=new Resenia();
        resenia.setValoracionResenia(reseniaNEW.getValoracionResenia());
        resenia.setComentarioResenia(reseniaNEW.getComentarioResenia());
        resenia.setIdAlumno(reseniaNEW.getIdAlumno());
        resenia.setIdCurso(reseniaNEW.getIdCurso());
        Resenia updated=reseniaService.update(id,resenia);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

}
