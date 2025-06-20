package com.edutech.msvc.reporte.controllers;

import com.edutech.msvc.reporte.dtos.ReporteDTO;
import com.edutech.msvc.reporte.models.entities.Reporte;
import com.edutech.msvc.reporte.services.ReporteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
@Validated
@Tag(name="Reporte", description = "Metodos CRUD para Reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public ResponseEntity<List<Reporte>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(reporteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reporte> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(reporteService.findById(id));
    }

    @GetMapping("/gerenteCursos/{id}")
    public ResponseEntity<List<Reporte>> findGerenteById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.reporteService.findByGerenteCursoId(id));
    }

    @PostMapping
    public ResponseEntity<Reporte> save(@Valid @RequestBody Reporte reporte) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteService.save(reporte));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reporte> update(@PathVariable Long id, @Valid @RequestBody ReporteDTO reporteDTO) {
        Reporte reporte = new Reporte();
        reporte.setEstado(reporteDTO.getEstado());
        Reporte updated = reporteService.update(id, reporte);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reporte> delete(@PathVariable Long id) {
        this.reporteService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
