package com.edutech.msvc.gerenteCurso.assemblers;

import com.edutech.msvc.gerenteCurso.controllers.GerenteCursoControllerV2;
import com.edutech.msvc.gerenteCurso.models.entities.GerenteCurso;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class GerenteCursoModelAssembler implements RepresentationModelAssembler <GerenteCurso, EntityModel<GerenteCurso>> {
    @Override
    public EntityModel<GerenteCurso> toModel(GerenteCurso entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(GerenteCursoControllerV2.class).findAll()).withRel("gerenteCursos"),
                linkTo(methodOn(GerenteCursoControllerV2.class).findById(entity.getIdGerenteCurso())).withSelfRel()
        );
    }
}
