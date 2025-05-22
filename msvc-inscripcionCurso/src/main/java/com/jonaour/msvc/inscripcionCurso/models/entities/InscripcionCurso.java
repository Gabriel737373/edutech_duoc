package com.jonaour.msvc.inscripcionCurso.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "inscripcionCursos")
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class InscripcionCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscripcionCurso")
    private Long idInscripcionCurso;

    @Column(name = "fecha_inscripcion")
    private Date fechaInscripcion;   //VERIFICAR DSP EN POSTMAN SI FUNCIONA BIEN

    @Column(name = "costo_inscripcion")
    private float costoInscripcion;

    @Column(name = "id_alumno")
    @NotNull(message = "El campo id_alumno no puede estar vacío")
    private Long idAlumno;

    @Column(name = "id_profesor")
    @NotNull(message = "El campo id_profesor no puede estar vacío")
    private Long idProfesor;

}
