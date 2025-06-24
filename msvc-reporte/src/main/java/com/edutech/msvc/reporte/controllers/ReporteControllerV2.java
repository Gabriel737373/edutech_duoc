package com.edutech.msvc.reporte.controllers;

import com.edutech.msvc.reporte.assemblers.ReporteModelAssembler;
import com.edutech.msvc.reporte.dtos.ErrorDTO;
import com.edutech.msvc.reporte.models.entities.Reporte;
import com.edutech.msvc.reporte.services.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
@RequestMapping("/api/v2/reportes")
@Validated
@Tag(name = "Reportes-V2", description = "Metodos CRUD para reportes")
public class ReporteControllerV2 {

    @Autowired
    private ReporteService reporteService;

    @Autowired
    private ReporteModelAssembler reporteModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Devuelve todos los reportes de curso",
            description = "Este metodo retorna una lista de reportes. En caso "+
                    "de que no encuentre nada, retornara una lista vacía"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se retornaros todos los reportes")
    })
    public ResponseEntity<CollectionModel<EntityModel<Reporte>>> findAll() {
        List<EntityModel<Reporte>> entityModels = this.reporteService.findAll()
                .stream()
                .map(reporteModelAssembler::toModel)
                .toList();
        CollectionModel<EntityModel<Reporte>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(ReporteControllerV2.class).findAll()).withSelfRel()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
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
    public ResponseEntity<EntityModel<Reporte>> findById(@PathVariable Long id) {
        EntityModel<Reporte> entityModel = this.reporteModelAssembler.toModel(
                reporteService.findById(id)
        );
        return ResponseEntity.status(HttpStatus.OK).body(entityModel);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Endpoint que permite guardar un reporte",
            description = "Este endpoint manda un body con el formato Reporte.class "+
                    "y permite la creacion de un reporte"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Reporte creado correctamente",
                    content = @Content(
                            mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Reporte.class)
                    ))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Este debe ser el Json con los datos de un reporte",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Reporte.class)
            )
    )
    public ResponseEntity<EntityModel<Reporte>> save(@Valid @RequestBody Reporte reporte) {
        Reporte reporteNew = this.reporteService.save(reporte);
        EntityModel<Reporte> entityModel=this.reporteModelAssembler.toModel(reporteNew);
        return ResponseEntity
                .created(linkTo(methodOn(ReporteControllerV2.class).findById(reporteNew.getIdReporte())).toUri())
                .body(entityModel);
    }


    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un reporte por ID",
            description = "Este endpoint permite eliminar un reporte existente a través de su ID. " +
                    "Si el reporte es eliminado exitosamente, retorna un código 204 sin contenido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reporte eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Error - Reporte con ID no encontrado",
                    content = @Content)
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.reporteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(
            summary = "Endpoint que permite actualizar un reporte existente",
            description = "Este endpoint recibe un JSON con los datos actualizados de un reporte " +
                    "y retorna el reporte modificado con los enlaces HATEOAS correspondientes."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reporte actualizado correctamente",
                    content = @Content(mediaType = MediaTypes.HAL_JSON_VALUE,
                            schema = @Schema(implementation = Reporte.class))),
            @ApiResponse(responseCode = "404", description = "Error - Reporte con ID no encontrado",
                    content = @Content)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            description = "Debe contener los campos del reporte a actualizar",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Reporte.class))
    )
    public ResponseEntity<EntityModel<Reporte>> update(
            @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody Reporte reporte) {

        Reporte updatedReporte = this.reporteService.update(id, reporte);
        EntityModel<Reporte> entityModel = this.reporteModelAssembler.toModel(updatedReporte);
        return ResponseEntity.ok(entityModel);
    }



}
