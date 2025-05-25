package com.edutech.msvc.gerenteCurso.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name="gerente_cursos")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class GerenteCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gerente_curso")
    public Long idGerenteCurso;

    @Column(nullable = false)
    @NotBlank(message = "El campo nombre gerente no puede ser vacio")
    private String nombreCompleto;

}
