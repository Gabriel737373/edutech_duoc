package com.edutech.msvc.gerenteCurso.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class GerenteCursoDTO {

    @NotBlank(message = "El campo nombre completo no puede ser vacio")
    private String nombreCompleto;
}
