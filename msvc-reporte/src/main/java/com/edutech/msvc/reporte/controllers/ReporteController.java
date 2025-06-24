package com.edutech.msvc.reporte.controllers;

import com.edutech.msvc.reporte.dtos.ErrorDTO;
import com.edutech.msvc.reporte.dtos.ReporteDTO;
import com.edutech.msvc.reporte.models.entities.Reporte;
import com.edutech.msvc.reporte.services.ReporteService;
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
@RequestMapping("/api/v1/reportes")
@Validated
@Tag(name="Reporte", description = "Metodos CRUD para Reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    @Operation(
            summary = "Devuelve todos los reportes de curso",
            description = "Este metodo retorna una lista de reportes. En caso "+
                    "de que no encuentre nada, retornara una lista vacía"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se retornaros todos los reportes")
    })
    public ResponseEntity<List<Reporte>> findAll() {
        List<Reporte> reportes = this.reporteService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(reportes);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Devuelve un reporte respecto a su ID",
            description = "Este método retorna un reporte cuando es consultado mediante su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se retorna el reporte encontrado"),
            @ApiResponse(responseCode = "404", description = "Error - Reporte con ID no se encuentra",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class)))
    })
    @Parameters(value = {
            @Parameter(name = "id",description = "Este es el ID único de un reporte", required = true)
    })
    public ResponseEntity<Reporte> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(reporteService.findById(id));
    }

    @GetMapping("/gerenteCursos/{id}")
    public ResponseEntity<List<Reporte>> findGerenteById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.reporteService.findByGerenteCursoId(id));
    }

    @PostMapping
    @Operation(
            summary = "Endpoint que permite guardar un reporte",
            description = "Este endpoint manda un body con el formato Reporte.class"+
                    "y permite la creación de un reporte"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reporte creado correctamente")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Este debe ser el json con los datos de una inscripcion curso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Reporte.class)
            )
    )
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
    @Operation(
            summary = "Eliminar un reporte por ID",
            description = "Este endpoint permite eliminar un reporte existente a través de su ID"+
                    "Si el reporte es eliminado exitosamente, retorna un código 204 sin contenido"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reporte eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Error - Reporte con ID no encontrado en la base de datos",
                    content = @Content)
    })
    public ResponseEntity<Reporte> delete(@PathVariable Long id) {
        this.reporteService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
