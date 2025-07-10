package com.edutech.msvc.evaluacion.models.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "evaluaciones")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluacion",nullable = false,unique = true)
    @Schema(description = "Primary key evaluacion", example = "1")
    private Long idEvaluacion;

    @Column(nullable = false)
    @NotBlank(message = "El campo no puede estar vacio")
    @Schema(description = "Nombre de la evaluacion", example = "Evaluacion Inicial")
    private String nombreEvaluacion;

    @Column(nullable = false)
    @NotBlank(message = "El campo no puede estar vacio")
    @Schema(description = "Materia de la evaluacion", example = "Gestion de proyectos")
    private String materiaEvaluacion;
}
