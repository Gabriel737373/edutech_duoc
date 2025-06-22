package com.edutech.msvc.gerenteCurso.models.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name="gerente_cursos")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Schema(description = "Entidad de gerente de cursos")
public class GerenteCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gerente_curso")
    @Schema(description = "Primary key Gerente de Cursos", example = "1")
    public Long idGerenteCurso;

    @Column(nullable = false)
    @NotBlank(message = "El campo nombre gerente no puede ser vacio")
    @Schema(description = "Nombre del Gerente de Cursos", example = "Francisco")
    private String nombreCompleto;

}
