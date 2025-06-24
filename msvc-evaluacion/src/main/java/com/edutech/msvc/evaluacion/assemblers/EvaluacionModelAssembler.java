package com.edutech.msvc.evaluacion.assemblers;


import com.edutech.msvc.evaluacion.controllers.EvaluacionControllerV2;
import com.edutech.msvc.evaluacion.models.entities.Evaluacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EvaluacionModelAssembler implements RepresentationModelAssembler<Evaluacion, EntityModel<Evaluacion>> {
    @Override
    public EntityModel<Evaluacion> toModel(Evaluacion entity){
        return EntityModel.of(
                entity,
                linkTo(methodOn(EvaluacionControllerV2.class).findAll()).withRel("evaluaciones"),
                linkTo(methodOn(EvaluacionControllerV2.class).findById(entity.getIdEvaluacion())).withSelfRel()
        );

    }
}
