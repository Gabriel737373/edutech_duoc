package com.edutech.msvc.alumno.models.entities;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Primary key Alumno", example = "1")
    private Long idAlumno;

    @Column(nullable = false)
    @NotBlank(message = "El campo nombre alumno no puede ser vacio")
    @Schema(description = "Nombre del Alumno", example = "Ariel Molina ")
    private String nombreCompleto;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El campo correo alumno no puede ser vacio")
    @Schema(description = "Correo del Alumno", example = "ari.molinam@gmail.com")
    private String correo;

}
