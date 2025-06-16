package com.edutech.msvc.alumno.controllers;



import com.edutech.msvc.alumno.dtos.AlumnoDTO;
import com.edutech.msvc.alumno.models.entities.Alumno;
import com.edutech.msvc.alumno.services.AlumnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alumnos")
@Validated
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping
    @Operation(
            summary = "Devuelve todos los Alumnos",
            description = "Este metodo retorna una lista de Alumnos, en caso "+
                    "de que no encuentre nada, retorta una lista vacia"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Se retornaron todos los Alumnos OK")
    })
    public ResponseEntity<List<Alumno>> findAll(){
        List<Alumno> alumnos = this.alumnoService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(alumnos);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Devuelve un Alumno respecto a su ID",
            description = "Este metodo retorna un Alumno cuando es consultado mediante su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se retorna el Alumno encontrado"),
            @ApiResponse(responseCode = "400", description = "Error - Alumno con ID no existe")
    })
    @Parameters(value = {
            @Parameter(name = "id", description = "Este es el ID unico de un Alumno", required = true)
    })
    public ResponseEntity<Alumno> findById(@PathVariable Long id){
        Alumno alumno = this.alumnoService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(alumno);
    }

    @PostMapping
    public ResponseEntity<Alumno> save(@Valid @RequestBody AlumnoDTO alumnoDTO){
        Alumno alumno = new Alumno();
        alumno.setNombreCompleto(alumnoDTO.getNombreCompleto());
        alumno.setCorreo(alumnoDTO.getCorreo());
        Alumno saved = alumnoService.save(alumno);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alumno> update(@PathVariable Long id, @Valid @RequestBody AlumnoDTO alumnoDTO){
        Alumno alumno = new Alumno();
        alumno.setNombreCompleto(alumnoDTO.getNombreCompleto());
        alumno.setCorreo(alumnoDTO.getCorreo());
        Alumno updated = alumnoService.update(id, alumno);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Alumno> delete(@PathVariable Long id){
        this.alumnoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
