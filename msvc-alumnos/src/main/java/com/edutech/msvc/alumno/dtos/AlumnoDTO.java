package com.edutech.msvc.alumno.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class AlumnoDTO {

    @NotBlank(message = "El campo nombre completo no puede ser vacio")
    private String nombreCompleto;

    @NotBlank(message = "El campo correo no puede ser vacio")
    private String correo;
}
