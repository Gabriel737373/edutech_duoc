package com.edutech_duoc.msvc_profesor.assemblers;

import com.edutech_duoc.msvc_profesor.controllers.ProfesorControllerV2;
import com.edutech_duoc.msvc_profesor.models.entities.Profesor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProfesorModelAssembler implements RepresentationModelAssembler<Profesor, EntityModel<Profesor>> {
    @Override
    public EntityModel<Profesor> toModel(Profesor entity){
        return EntityModel.of(
                entity,
                linkTo(methodOn(ProfesorControllerV2.class).findAll()).withRel("profesores"),
                linkTo(methodOn(ProfesorControllerV2.class).findById(entity.getIdProfesor())).withSelfRel()
        );
    }

}
