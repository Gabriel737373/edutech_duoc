package com.edutech.msvc.alumno.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name="alumnos")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alumno")
    private Long idAlumno;

    @Column(nullable = false)
    @NotBlank(message = "El campo nombre alumno no puede ser vacio")
    private String nombreCompleto;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El campo correo alumno no puede ser vacio")
    private String correo;

}
