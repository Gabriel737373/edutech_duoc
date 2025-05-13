package com.edutech.msvc.curso.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class CursoDTO {

    @NotBlank(message = "El campo nombre curso no puede ser vacio")
    private String nombreCurso;

    @NotBlank(message = "El campo descripcion curso no puede ser vacio")
    private String descripcionCurso;

    @NotNull(message = "El campo precio curso no puede ser vacio")
    private Integer precioCurso;


}
