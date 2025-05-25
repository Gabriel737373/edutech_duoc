package com.edutech.msvc.reporte.models.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "reporte")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reporte")
    private Long idReporte;

    @Column(nullable = false, name = "descripcion")
    @NotBlank(message = "El campo descripcion no puede estar vacio")
    private String descripcion;

    @Column(nullable = false, name = "estado")
    @NotBlank(message = "El campo estado no puede estar vacio")
    private String estado;

    @Column(nullable = false, name = "id_gerenteCurso")
    private Long idGerenteCurso;
}
