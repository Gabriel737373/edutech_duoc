package com.jonaour.msvc.inscripcionCurso.controllers;

import com.jonaour.msvc.inscripcionCurso.dtos.InscripcionCursoDTO;
import com.jonaour.msvc.inscripcionCurso.models.entities.InscripcionCurso;
import com.jonaour.msvc.inscripcionCurso.services.InscripcionCursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inscripcionCursos")
@Validated
public class InscripcionCursoController {

    @Autowired
    private InscripcionCursoService inscripcionCursoService;

    //Metodo findAll todas las inscripcion
    @GetMapping
    public ResponseEntity<List<InscripcionCursoDTO>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.inscripcionCursoService.findAll());
    }

    //Metodo find by id inscripcion curso
    @GetMapping("/{id}")
    public ResponseEntity<InscripcionCurso> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.inscripcionCursoService.findById(id));
    }

    //Metodo guardar inscripcion curso
    @PostMapping
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

    //Metodo para eliminar una inscripcion
    @DeleteMapping("/{id}")
    public ResponseEntity<InscripcionCurso> delete(@PathVariable Long id){
        this.inscripcionCursoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //Metodo para editar una inscripcion
    @PutMapping("/{id}")
    public ResponseEntity<InscripcionCurso> update(@PathVariable Long id, @Valid @RequestBody InscripcionCursoDTO inscripcionCursoDTO){
        InscripcionCurso inscripcionCurso=new InscripcionCurso();
        inscripcionCurso.setCostoInscripcion(inscripcionCursoDTO.getCostoInscripcion());
        inscripcionCurso.setFechaInscripcion(inscripcionCursoDTO.getFechaInscripcion());
        InscripcionCurso updated= inscripcionCursoService.update(id, inscripcionCurso);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
}
