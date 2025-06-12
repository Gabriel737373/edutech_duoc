package com.edutech.msvc.curso.models.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "cursos")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "Entidad de Cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    @Schema(description = "Primary key Curso", example = "1")
    private Long idCurso;

    @Column(nullable = false)
    @NotBlank(message = "El campo nombre curso no puede ser vacio")
    @Schema(description = "Nombre del curso", example = "Desarrollo Fullstack II")
    private String nombreCurso;

    @Column(nullable = false)
    @NotBlank(message = "El campo descripcion curso no puede ser vacio")
    @Schema(description = "Descripcion del curso", example = "Introduccion al backend")
    private String descripcionCurso;

    @Column(nullable = false)
    @NotNull(message = "El campo precio curso no puede ser vacio")
    @Schema(description = "Precio del curso",example = "750000")
    private Integer precioCurso;


}
