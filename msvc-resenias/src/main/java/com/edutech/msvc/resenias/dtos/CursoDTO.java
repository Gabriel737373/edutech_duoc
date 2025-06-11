package com.edutech.msvc.resenias.dtos;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CursoDTO {
    private String nombreCurso;
    private String descripcionCurso;
    private Integer precioCurso;
}