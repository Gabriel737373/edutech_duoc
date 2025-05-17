package com.edutech.msvc.evaluacion.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class EvaluacionDTO {

    @NotBlank(message = "El campo nombre debe estar completo")
    private String nombreEvaluacion;

    @NotBlank(message = "El campo materia debe estar completo")
    private String materiaEvaluacion;
}
