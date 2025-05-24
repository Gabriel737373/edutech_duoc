package com.jonaour.msvc.inscripcionCurso.dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @ToString
public class InscripcionCursoDTO {
    private AlumnoDTO alumno;
    private ProfesorDTO profesor;
    private CursoDTO curso;
    private LocalDate fechaInscripcion;
    private float costoInscripcion;
}
