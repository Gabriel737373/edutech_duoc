package com.edutech.msvc.evaluacion.controllers;

import com.edutech.msvc.evaluacion.dtos.EvaluacionDTO;
import com.edutech.msvc.evaluacion.models.entities.Evaluacion;
import com.edutech.msvc.evaluacion.services.EvaluacionService;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/evaluaciones")
@Validated
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @GetMapping
    public ResponseEntity<List<Evaluacion>> findAll(){
        List<Evaluacion> evaluaciones = this.evaluacionService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(evaluaciones);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Evaluacion> findById(@PathVariable Long id){
        Evaluacion evaluacion = this.evaluacionService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(evaluacion);
    }

    @PostMapping()
    public ResponseEntity<Evaluacion> save(@Valid @RequestBody EvaluacionDTO evaluacionDTO){
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setNombreEvaluacion(evaluacionDTO.getNombreEvaluacion());
        evaluacion.setMateriaEvaluacion(evaluacionDTO.getMateriaEvaluacion());
        Evaluacion saved = evaluacionService.save(evaluacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Evaluacion> update(@PathVariable Long id, @Valid @RequestBody EvaluacionDTO evaluacionDTO){
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setNombreEvaluacion(evaluacionDTO.getNombreEvaluacion());
        evaluacion.setMateriaEvaluacion(evaluacionDTO.getMateriaEvaluacion());
        Evaluacion updated = evaluacionService.update(id, evaluacion);
        return ResponseEntity.status(HttpStatus.OK).body(updated);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Evaluacion> delete(@PathVariable Long id){
        this.evaluacionService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
