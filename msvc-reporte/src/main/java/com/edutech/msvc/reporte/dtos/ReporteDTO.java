package com.edutech.msvc.reporte.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class ReporteDTO {

    @NotBlank(message = "El campo despcripcion no puede ser vacio")
    private String descripcion;

    @NotBlank(message = "El campo estado no puede ser vacio")
    private String estado;
}
