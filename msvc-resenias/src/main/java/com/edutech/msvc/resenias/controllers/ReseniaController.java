package com.edutech.msvc.resenias.controllers;

import com.edutech.msvc.resenias.dtos.ReseniaDTO;
import com.edutech.msvc.resenias.models.entities.Resenia;
import com.edutech.msvc.resenias.services.ReseniaService;
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
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resenias")
@Validated
@Tag(name = "Resenias",description = "Métodos CRUD para reseñas")
public class ReseniaController {

    @Autowired
    private ReseniaService reseniaService;

    @GetMapping
    @Operation(
            summary = "Devuelve todas las reseñas ",
            description = "Este metodo retorna una lista de  reseñas. en caso "+"de que no encuentre nada, retornara una lista vacía"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se retornaros todas las reseñas")
    })
    public ResponseEntity<List<Resenia>> findAll(){
        List<Resenia> resenias=this.reseniaService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(resenias);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Devuelve una reseña respecto a su ID",
            description = "Este método retorna una reseña cuando es consultado mediante su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se retorna la reseña encontrada"),
            @ApiResponse(responseCode = "404", description = "Error - Reseña con ID no se encuentra",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameters(value = {
            @Parameter(name = "id",description = "Este es el ID único de una reseña", required = true)
    })
    public ResponseEntity<Resenia> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.reseniaService.findById(id));
    }

    @PostMapping
    @Operation(
            summary = "Endpoint que permite guardar una reseña",
            description = "Este endpoint manda un body con el formato Reseña.class"+ "y permite la creación de una inscripcion curso"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reseña creada correctamente")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Este debe ser el json con los datos de una reseña",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Resenia.class)
            )
    )
    public ResponseEntity<Resenia> save(@RequestBody @Valid Resenia resenia){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.reseniaService.save(resenia));
    }

    //Metodo para filtrar reseñias hechas por un alumno

    @GetMapping("/alumno/{id}")
    public ResponseEntity<List<Resenia>> findByIdAlumno(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.reseniaService.findByAlumnoId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una reseña por ID",
            description = "Este endpoint permite eliminar una reseña existente a través de su ID"+"Si la reseña es eliminado exitosamente, retorna un código 204 sin contenido"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reseña eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Error - Reseña con ID no encontrado en la base de datos",
                    content = @Content)
    })
    public ResponseEntity<Resenia> delete(@PathVariable Long id){
        this.reseniaService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Endpoint que permite actualizar una reseña existente",
            description = "Este endpoint recibe un json con los datos actualizados de una reseña"+"y retorna la inscripcion curso modificada con los enlaces HATEOAS correspondientes"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reseña actualizada correctamente",
                    content = @Content(mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Resenia.class))),
            @ApiResponse(responseCode = "404", description = "Error - Reseña con ID no se encuentra en la base de datos",
                    content = @Content)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Debe contener los campos de reseña a actualizar",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Resenia.class))
    )
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
