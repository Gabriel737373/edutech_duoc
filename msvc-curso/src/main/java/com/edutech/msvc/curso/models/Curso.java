package com.edutech.msvc.curso.models;

import jakarta.persistence.*;
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

}
