package com.edutech.msvc.curso.controllers;

import com.edutech.msvc.curso.dtos.CursoDTO;
import com.edutech.msvc.curso.dtos.ErrorDTO;
import com.edutech.msvc.curso.models.entities.Curso;
import com.edutech.msvc.curso.services.CursoService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v3/cursos")
@Validated
@Tag(name = "Cursos", description = "Metodos CRUD para cursos")
public class CursoControllerV2 {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    @Operation(summary = "Retorna todos los Curso",
               description = "Devuelve un Listado de 'Cursos' en caso "+
                              "de no encontrar retorna una lista vacia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se retornaron todos los cursos OK")
    })
    public ResponseEntity<List<Curso>> findAll() {
        List<Curso> cursos = this.cursoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(cursos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna un Curso por su ID",
               description = "Devuelve un 'Curso' en caso "+
                             "de no encontrar retorna una lista vacia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se retorno un curso OK"),
            @ApiResponse(responseCode = "400", description = "Error - Curso con ID no encontrado",
                         content = @Content(mediaType = "application/json",
                          schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameters(value = {
            @Parameter(name = "id",
                       description = "Este es el id unico de un curso", required = true)
    })
    public ResponseEntity<Curso> findById(@PathVariable Long id){
        Curso curso = this.cursoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(curso);
    }

    @PostMapping
    @Operation(
            summary = "Endpoint que permite guardar un Curso",
            description = "Este endpoint manda un body con el formato Curso.class "+
                    "y permite la creacion de Curso"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Curso creaco correctamente")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Este debe ser el Json con los datos de Curso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Curso.class)
            )
    )
    public ResponseEntity<Curso> save(@Valid @RequestBody CursoDTO cursoDTO){
        Curso curso = new Curso();
        curso.setNombreCurso(cursoDTO.getNombreCurso());
        curso.setDescripcionCurso(cursoDTO.getDescripcionCurso());
        curso.setPrecioCurso(cursoDTO.getPrecioCurso());
        Curso saved = cursoService.save(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> update(@PathVariable Long id, @Valid @RequestBody CursoDTO cursoDTO){
        Curso curso = new Curso();
        curso.setNombreCurso(cursoDTO.getNombreCurso());
        curso.setDescripcionCurso(cursoDTO.getDescripcionCurso());
        curso.setPrecioCurso(cursoDTO.getPrecioCurso());
        Curso updated = cursoService.update(id, curso);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Curso> delete(@PathVariable Long id){
        this.cursoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }





}
