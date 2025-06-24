package com.edutech.msvc.reporte.assemblers;

import com.edutech.msvc.reporte.controllers.ReporteControllerV2;
import com.edutech.msvc.reporte.models.entities.Reporte;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReporteModelAssembler implements RepresentationModelAssembler<Reporte, EntityModel<Reporte>> {

    @Override
    public EntityModel<Reporte> toModel(Reporte entity){
        return EntityModel.of(
            entity,
                linkTo(methodOn(ReporteControllerV2.class).findById(entity.getIdReporte())).withSelfRel(),
                linkTo(methodOn(ReporteControllerV2.class).findAll()).withRel("reportes"),
                Link.of("http://localhost:8088/api/v1/gerenteCursos"+entity.getIdGerenteCurso()).withRel("gerente-curso")
        );
    }

}
