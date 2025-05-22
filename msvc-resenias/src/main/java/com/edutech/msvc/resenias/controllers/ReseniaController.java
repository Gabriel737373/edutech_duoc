package com.edutech.msvc.resenias.controllers;

import com.edutech.msvc.resenias.dtos.ReseniaDTO;
import com.edutech.msvc.resenias.models.entities.Resenia;
import com.edutech.msvc.resenias.services.ReseniaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resenias")
@Validated
public class ReseniaController {

    @Autowired
    private ReseniaService reseniaService;

    @GetMapping
    public ResponseEntity<List<ReseniaDTO>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(this.reseniaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resenia> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.reseniaService.findById(id));
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

    @DeleteMapping
    public ResponseEntity<Resenia> delete(@PathVariable Long id){
        this.reseniaService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resenia> update(@PathVariable Long id, @Valid @RequestBody ReseniaDTO reseniaDTO){
        Resenia resenia=new Resenia();
        resenia.setValoracionResenia(reseniaDTO.getValoricacionResenia());
        resenia.setComentarioResenia(reseniaDTO.getComentarioResenia());
        Resenia updated=reseniaService.update(id,resenia);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

}
