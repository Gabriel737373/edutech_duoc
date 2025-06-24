package com.edutech.msvc.resenias.assemblers;

import com.edutech.msvc.resenias.controllers.ReseniaControllerV2;
import com.edutech.msvc.resenias.models.entities.Resenia;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReseniasModelAssembler implements RepresentationModelAssembler<Resenia, EntityModel<Resenia>> {

    @Override
    public EntityModel<Resenia> toModel(Resenia entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(ReseniaControllerV2.class).findById(entity.getIdResenia())).withSelfRel(),
                linkTo(methodOn(ReseniaControllerV2.class).findAll()).withRel("resenias"),
                Link.of("http://localhost:8091/api/v2/alumnos/"+entity.getIdAlumno()).withRel("alumnos"),
                Link.of("http://localhost:8084/api/v2/cursos/"+entity.getIdCurso()).withRel("cursos")
        );
    }
}
