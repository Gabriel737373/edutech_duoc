package com.edutech_duoc.msvc_profesor.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class ProfesorDTO {

    @NotBlank(message = "EL campo nombre debe estar completo")
    private String nombreProfesor;

    @NotBlank(message = "El campo de apellido no puede estar vacio")
    private String apellidoProfesor;

    @NotBlank(message = "El correo no puede estar vacio")
    private String correoProfesor;
}
