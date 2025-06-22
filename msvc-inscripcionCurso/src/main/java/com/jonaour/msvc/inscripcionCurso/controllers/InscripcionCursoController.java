package com.jonaour.msvc.inscripcionCurso.controllers;

import com.jonaour.msvc.inscripcionCurso.dtos.ErrorDTO;
import com.jonaour.msvc.inscripcionCurso.dtos.InscripcionCursoDTO;
import com.jonaour.msvc.inscripcionCurso.models.entities.InscripcionCurso;
import com.jonaour.msvc.inscripcionCurso.services.InscripcionCursoService;
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
@RequestMapping("/api/v1/inscripcionCursos")
@Validated
@Tag(name="InscripcionCurso", description = "Métodos CRUD para Inscripcion cursos")
public class InscripcionCursoController {

    @Autowired
    private InscripcionCursoService inscripcionCursoService;

    //VERIFICAR DTO
    @GetMapping
    public ResponseEntity<List<InscripcionCursoDTO>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.inscripcionCursoService.findAll());
    }

    //Metodo find by id inscripcion curso
    @GetMapping("/{id}")
    @Operation(
            summary = "Devuelve una inscripcion curso respecto a su ID",
            description = "Este método retorna una inscripcion curso cuando es consultado mediante su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se retorna la inscripcion curso encontrada"),
            @ApiResponse(responseCode = "404", description = "Error - inscripcion curso con ID no se encuentra",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameters(value = {
            @Parameter(name = "id",description = "Este es el ID único de una inscripcion curso", required = true)
    })
    public ResponseEntity<InscripcionCurso> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.inscripcionCursoService.findById(id));
    }

    //Metodo guardar inscripcion curso
    @PostMapping
    @Operation(
            summary = "Endpoint que permite guardar una inscripcion curso",
            description = "Este endpoint manda un body con el formato InscripcionCurso.class"+ "y permite la creación de una inscripcion curso"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inscripcion curso creado correctamente")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Este debe ser el json con los datos de una inscripcion curso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = InscripcionCurso.class)
            )
    )
    public ResponseEntity<InscripcionCurso> save(@RequestBody @Valid InscripcionCurso inscripcionCurso){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.inscripcionCursoService.save(inscripcionCurso));
    }

    //Metodo filtrar inscripciones para un alumno
    @GetMapping("/alumno/{id}")
    public ResponseEntity<List<InscripcionCurso>> findByIdAlumno(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.inscripcionCursoService.findbyAlumnoId(id));
    }

    //Metodo filtrar inscripciones para un profesor
    @GetMapping("/profesor/{id}")
    public ResponseEntity<List<InscripcionCurso>> findbyIdProfesor(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.inscripcionCursoService.findbyProfesorId(id));
    }

    //Metodo filtrar inscripcion de un curso
    @GetMapping("/curso/{id}")
    public ResponseEntity<List<InscripcionCurso>> findbyIdCurso(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.inscripcionCursoService.findbyCursoId(id));
    }

    //Metodo filtrar inscripcion por gerente curso
    @GetMapping("/gerenteCursos/{id}")
    public ResponseEntity<List<InscripcionCurso>> findbyIdGerenteCurso(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.inscripcionCursoService.findByGerenteCursoId(id));
    }

    //Metodo para eliminar una inscripcion
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una inscripcion curso por ID",
            description = "Este endpoint permite eliminar una inscripcion curso existente a través de su ID"+"Si la inscripcion curso es eliminado exitosamente, retorna un código 204 sin contenido"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Inscripcion curso eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Error - Inscripcion curso con ID no encontrado en la base de datos",
                content = @Content)
    })
    public ResponseEntity<InscripcionCurso> delete(@PathVariable Long id){
        this.inscripcionCursoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //Metodo para editar una inscripcion
    @PutMapping("/{id}")
    @Operation(
            summary = "Endpoint que permite actualizar una inscripcion curso existente",
            description = "Este endpoint recibe un json con los datos actualizados de una inscripcion curso"+"y retorna la inscripcion curso modificada con los enlaces HATEOAS correspondientes"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscripcion curso actualizada correctamente",
                content = @Content(mediaType = MediaTypes.HAL_JSON_VALUE,
                schema = @Schema(implementation = InscripcionCurso.class))),
            @ApiResponse(responseCode = "404", description = "Error - Inscripcion curso con ID no se encuentra en la base de datos",
                content = @Content)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Debe contener los campos de inscripcion curso a actualizar",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = InscripcionCurso.class))
    )
    public ResponseEntity<InscripcionCurso> update(@PathVariable Long id, @Valid @RequestBody InscripcionCurso inscripcionCursoNew){
        InscripcionCurso inscripcionCurso=new InscripcionCurso();
        inscripcionCurso.setCostoInscripcion(inscripcionCursoNew.getCostoInscripcion());
        inscripcionCurso.setFechaInscripcion(inscripcionCursoNew.getFechaInscripcion());
        inscripcionCurso.setIdAlumno(inscripcionCursoNew.getIdAlumno());
        inscripcionCurso.setIdProfesor(inscripcionCursoNew.getIdProfesor());
        inscripcionCurso.setIdCurso(inscripcionCursoNew.getIdCurso());
        inscripcionCurso.setIdGerenteCurso(inscripcionCursoNew.getIdGerenteCurso());
        InscripcionCurso updated= inscripcionCursoService.update(id, inscripcionCurso);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
}
