package com.edutech.msvc.evaluacion.models.entities;

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
    @Column(name = "id_evaluacion")
    private Long idEvaluacion;

    @Column(nullable = false)
    @NotBlank(message = "El campo no puede estar vacio")
    private String nombreEvaluacion;

    @Column(nullable = false)
    @NotBlank(message = "El campo no puede estar vacio")
    private String materiaEvaluacion;
}
