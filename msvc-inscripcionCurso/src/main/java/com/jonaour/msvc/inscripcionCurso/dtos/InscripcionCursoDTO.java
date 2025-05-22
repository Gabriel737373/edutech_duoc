package com.jonaour.msvc.inscripcionCurso.dtos;

import lombok.*;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @ToString
public class InscripcionCursoDTO {
    private AlumnoDTO alumno;
    private ProfesorDTO profesor;
    private Date fechaInscripcion;
    private float costoInscripcion;
}
