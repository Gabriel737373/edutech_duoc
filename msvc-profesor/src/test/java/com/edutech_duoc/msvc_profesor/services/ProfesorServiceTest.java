package com.edutech_duoc.msvc_profesor.services;

import com.edutech_duoc.msvc_profesor.exceptions.ProfesorException;
import com.edutech_duoc.msvc_profesor.models.entities.Profesor;
import com.edutech_duoc.msvc_profesor.repositories.ProfesorRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfesorServiceTest {

    @Mock
    private ProfesorRepository profesorRepository;

    @InjectMocks
    private ProfesorServiceImpl profesorService;

    private Profesor profesorPrueba;

    private List<Profesor> profesores = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        Faker faker = new Faker(Locale.of("es","cl"));
        for (int i = 0; i < 100; i++){
            Profesor profesor =new Profesor();
            profesor.setIdProfesor((long) i);
            profesor.setNombreProfesor(faker.name().fullName());
            profesor.setCorreoProfesor(faker.internet().emailAddress() + "_" + i);

            this.profesores.add(profesor);
        }
        this.profesorPrueba = new Profesor(
                1L, "Gab", "Velasquez", "ga.velasquezm@duocuc.cl"
        );
    }

    @Test
    @DisplayName("Debe listar todos los profesores")
    public void shouldFindAllProfesores() {
        this.profesores.add(this.profesorPrueba);
        when(profesorRepository.findAll()).thenReturn(this.profesores);
        List<Profesor> result = profesorService.findAll();

        assertThat(result).hasSize(101);
        assertThat(result).contains(this.profesorPrueba);
        verify(profesorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe de listar a un profesor mediante ID")
    public void shoulFindProfesorById(){
        when(profesorRepository.findById(1L)).thenReturn(Optional.of(this.profesorPrueba));
        Profesor result = profesorService.findById(1L);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.profesorPrueba);

        verify(profesorRepository, times( 1)).findById(1L);
    }

    @Test
    @DisplayName("Debe de retornar la excepcion en caso de que el profesor no exista")
    public void shoulNotFindProfesorById(){
        Long idInexistente = 99L;
        when(profesorRepository.findById(idInexistente)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> {
            profesorService.findById(idInexistente);
        }).isInstanceOf(ProfesorException.class)
                .hasMessageContaining("Profesor con id " + idInexistente + " no encontrado");
        verify(profesorRepository, times(1)).findById(idInexistente);

    }



    @Test
    @DisplayName("Debe guardar los nuevos profesores")
    public void shoulSaveProfesor(){
        when(profesorRepository.save(any(Profesor.class))).thenReturn(this.profesorPrueba);
        Profesor result = profesorService.save(this.profesorPrueba);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.profesorPrueba);
        verify(profesorRepository, times(1)).save(any(Profesor.class));
    }

    @Test
    @DisplayName("Debe actualizar a un profesor ya existente")
    public void shouldUpdateProfesor(){
        Long idProfesor = 1L;

        Profesor profesorExistente = new Profesor(idProfesor, "Benito", "Camelo","benitocamelo@gmail.com");
        Profesor profesorActualizado = new Profesor(idProfesor, "Gabriel", "Velasquez", "ga.velasquezm@duocuc.cl");

        when(profesorRepository.findById(idProfesor)).thenReturn(Optional.of(profesorExistente));
        when(profesorRepository.save(any(Profesor.class))).thenReturn(profesorActualizado);

        Profesor result = profesorService.update(idProfesor, profesorActualizado);

        assertThat(result.getNombreProfesor()).isEqualTo("Gabriel");
        assertThat(result.getApellidoProfesor()).isEqualTo("Velasquez");
        assertThat(result.getCorreoProfesor()).isEqualTo("ga.velasquezm@duocuc.cl");

        verify(profesorRepository, times(1)).findById(idProfesor);
        verify(profesorRepository, times(1)).save(any(Profesor.class));

    }

    @Test
    @DisplayName("Diebe eliminar a un Profesor por ID")
    public void shouldDeleteProfesorById(){

        Long idProfesor = 1L;

        doNothing().when(profesorRepository).deleteById(idProfesor);
        profesorService.deleteById(idProfesor);

        verify(profesorRepository, times(1 )).deleteById(idProfesor);
    }


}
