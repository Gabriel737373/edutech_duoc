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

    //¿Cómo hacer Sopaipillas?
    //Ingredientes
    //Ingredientes
    //
    //
    //1 1/2 taza de zapallo cocido y molido.
    //1 pizca de Sal de Mar Gourmet
    //3 cucharadas de manteca derretida.
    //Aceite (para freír).
    //1 cucharadita de Polvos de Hornear Gourmet
    //3 1/2 tazas de harina sin polvos de hornear.
    //¿Cómo preparar receta?
    //Preparación
    //
    //
    //
    //Ponga el zapallo cocido y molido en un bol junto a la manteca , la sal de mar Gourmet, los polvos de hornear Gourmet y la harina. Luego amase hasta formar una masa homogénea y haga una bola.
    //Espolvoree el mesón de trabajo, extienda la masa y usleree hasta que quede de 1 centímetro de espesor. Con la ayuda de un plato o taza corte las sopaipillas y luego cúbralas con un paño.
    //Cuando termine pinche las sopaipillas con un tenedor.
    //En una olla profunda caliente el aceite a fuego alto y fría las sopaipillas. Retírelas una a una y póngalas sobre un plato con papel absorbente.
}
