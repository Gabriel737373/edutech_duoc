package com.edutech.msvc.resenias.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name="resenias")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Resenia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_resenia")
    private Long idResenia;

    @Column(name = "comentario_resenia")
    private String comentarioResenia;

    @Column(name = "valoracion_resenia")
    @NotNull(message = "El campo valoración reseña no puede estar vacío")
    private Integer valoricacionResenia;
}
