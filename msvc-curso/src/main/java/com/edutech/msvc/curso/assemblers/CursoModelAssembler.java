package com.edutech.msvc.curso.assemblers;

import com.edutech.msvc.curso.controllers.CursoControllerV2;
import com.edutech.msvc.curso.models.entities.Curso;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CursoModelAssembler implements RepresentationModelAssembler<Curso, EntityModel<Curso>> {
    @Override
    public EntityModel<Curso> toModel(Curso entity){
        return EntityModel.of(
                entity,
                // Los puse por orden: 1. findAll, 2. findById
                linkTo(methodOn(CursoControllerV2.class).findAll()).withRel("cursos"),
                linkTo(methodOn(CursoControllerV2.class).findById(entity.getIdCurso())).withSelfRel()
        );

    }
}
