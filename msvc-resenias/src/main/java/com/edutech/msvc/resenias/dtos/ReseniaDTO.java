package com.edutech.msvc.resenias.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @ToString
public class ReseniaDTO {

    private AlumnoDTO alumnoDTO;
    private String comentarioResenia;
    private Integer valoricacionResenia;

}
