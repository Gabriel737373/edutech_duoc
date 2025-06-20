package com.edutech.msvc.alumno.assemblers;

import com.edutech.msvc.alumno.controllers.AlumnoControllerV2;
import com.edutech.msvc.alumno.models.entities.Alumno;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AlumnoModelAssembler implements RepresentationModelAssembler<Alumno, EntityModel<Alumno>> {
     @Override
     public EntityModel<Alumno> toModel(Alumno entity) {
         return EntityModel.of(
                 entity,
                 linkTo(methodOn(AlumnoControllerV2.class).findAll()).withRel("alumnos"),
                 linkTo(methodOn(AlumnoControllerV2.class).findById(entity.getIdAlumno())).withSelfRel()
         );
     }
}
