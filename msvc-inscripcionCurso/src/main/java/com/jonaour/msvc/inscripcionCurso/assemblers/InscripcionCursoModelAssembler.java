package com.jonaour.msvc.inscripcionCurso.assemblers;

import com.jonaour.msvc.inscripcionCurso.controllers.InscripcionCursoControllerV2;
import com.jonaour.msvc.inscripcionCurso.models.entities.InscripcionCurso;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class InscripcionCursoModelAssembler implements RepresentationModelAssembler<InscripcionCurso, EntityModel<InscripcionCurso>> {

    @Override
    public EntityModel<InscripcionCurso> toModel(InscripcionCurso entity) {
        return EntityModel.of(
                entity,
                linkTo(methodOn(InscripcionCursoControllerV2.class).findById(entity.getIdInscripcionCurso())).withSelfRel(),
                linkTo(methodOn(InscripcionCursoControllerV2.class).findAll()).withRel("inscripcionCursos"),
                Link.of("http://localhost:8081/api/v2/alumnos/"+entity.getIdAlumno()).withRel("alumnos")
        );
    }
}

//AGREGAR CURSOS - PROFE -