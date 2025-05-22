package com.jonaour.msvc.inscripcionCurso.models;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Profesor {
    private Long idProfesor;
    private String nombreProfesor;
    private String apellidoProfesor;
    private String correoProfesor;
}
