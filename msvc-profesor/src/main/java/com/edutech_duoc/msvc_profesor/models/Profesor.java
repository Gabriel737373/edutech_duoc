package com.edutech_duoc.msvc_profesor.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name ="profesores")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesor")
    private Long idProfesor;

    @Column(nullable = false)
    @NotBlank(message = "El campo no puede estar vacio")
    private String nombreProfesor;

    @Column(nullable = false)
    @NotBlank(message = "El campo no puede estar vacio")
    private String apellidoProfesor;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El campo no puede estar vacio")
    private String correoProfesor;

}
