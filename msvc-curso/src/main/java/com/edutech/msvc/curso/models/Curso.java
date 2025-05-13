package com.edutech.msvc.curso.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "cursos")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Long idCurso;

    @Column(nullable = false)
    @NotBlank(message = "El campo nombre curso no puede ser vacio")
    private String nombreCurso;

    @Column(nullable = false)
    @NotBlank(message = "El campo descripcion curso no puede ser vacio")
    private String descripcionCurso;

    @Column(nullable = false)
    @NotNull(message = "El campo precio curso no puede ser vacio")
    private Integer precioCurso;


}
