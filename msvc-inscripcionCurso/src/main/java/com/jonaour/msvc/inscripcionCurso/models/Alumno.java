package com.jonaour.msvc.inscripcionCurso.models;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Alumno {
    private Long idAlumno;
    private String nombreCompleto;
    private String correo;
}
