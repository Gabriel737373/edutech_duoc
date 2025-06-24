package com.jonaour.msvc.inscripcionCurso.services;


import com.jonaour.msvc.inscripcionCurso.clients.AlumnoClientRest;
import com.jonaour.msvc.inscripcionCurso.clients.CursoClientRest;
import com.jonaour.msvc.inscripcionCurso.clients.GerenteCursoClienteRest;
import com.jonaour.msvc.inscripcionCurso.clients.ProfesorClientRest;
import com.jonaour.msvc.inscripcionCurso.exceptions.InscripcionCursoException;
import com.jonaour.msvc.inscripcionCurso.models.Alumno;
import com.jonaour.msvc.inscripcionCurso.models.Curso;
import com.jonaour.msvc.inscripcionCurso.models.GerenteCurso;
import com.jonaour.msvc.inscripcionCurso.models.Profesor;
import com.jonaour.msvc.inscripcionCurso.models.entities.InscripcionCurso;
import com.jonaour.msvc.inscripcionCurso.repositories.InscripcionCursoRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InscripcionCursoServiceTest {

    @Mock
    private AlumnoClientRest alumnoClientRest;

    @Mock
    private CursoClientRest cursoClientRest;

    @Mock
    private GerenteCursoClienteRest gerenteCursoClienteRest;

    @Mock
    private ProfesorClientRest profesorClientRest;

    @Mock
    private InscripcionCursoRepository inscripcionCursoRepository;

    @InjectMocks
    private InscripcionCursoServiceImpl inscripcionCursoService;

    private InscripcionCurso inscripcionCursoTest;
    private Alumno alumnoTest;
    private Curso cursoTest;
    private GerenteCurso gerenteCursoTest;
    private Profesor profesorTest;

    private List<InscripcionCurso> inscripcionCursos = new ArrayList<>();


    @BeforeEach
    public void setUp() {
        Faker faker = new Faker(Locale.of("es", "CL"));
        for (int i = 0; i < 100; i++) {
            InscripcionCurso inscripcionCurso = new InscripcionCurso();
            inscripcionCurso.setIdInscripcionCurso((long) i);
            inscripcionCurso.setFechaInscripcion(LocalDate.now());
            inscripcionCurso.setCostoInscripcion(faker.number().numberBetween(1000, 20000));
            inscripcionCurso.setIdAlumno((long) faker.number().numberBetween(1,20));
            inscripcionCurso.setIdGerenteCurso((long) faker.number().numberBetween(1,20));
            inscripcionCurso.setIdProfesor((long) faker.number().numberBetween(1,20));
            inscripcionCurso.setIdCurso((long) faker.number().numberBetween(1,20));
            this.inscripcionCursos.add(inscripcionCurso);
        }
        this.inscripcionCursoTest = new InscripcionCurso(
                1L,
                LocalDate.now(),
                18000,
                1L,
                1L,
                1L,
                1L
        );
    }

    @Test
    @DisplayName("Debe crear una inscripcion curso")
    public void shouldCreateInscripcionCurso() {
        when(alumnoClientRest.findById(1L)).thenReturn(this.alumnoTest);
        when(cursoClientRest.findById(1L)).thenReturn(this.cursoTest);
        when(gerenteCursoClienteRest.findById(1L)).thenReturn(this.gerenteCursoTest);
        when(profesorClientRest.findById(1L)).thenReturn(this.profesorTest);
        when(inscripcionCursoRepository.save(any(InscripcionCurso.class))).thenReturn(this.inscripcionCursoTest);

        InscripcionCurso result = inscripcionCursoService.save(this.inscripcionCursoTest);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.inscripcionCursoTest);

        verify(alumnoClientRest,times(1)).findById(1L);
        verify(cursoClientRest,times(1)).findById(1L);
        verify(gerenteCursoClienteRest,times(1)).findById(1L);
        verify(profesorClientRest,times(1)).findById(1L);
        verify(inscripcionCursoRepository,times(1)).save(any(InscripcionCurso.class));
    }

    @Test
    @DisplayName("Debe listar todas las inscripciones curso")
    public void shouldFindAllInscripcionCurso() {
        this.inscripcionCursos.add(this.inscripcionCursoTest);
        when(inscripcionCursoRepository.findAll()).thenReturn(this.inscripcionCursos);
        List<InscripcionCurso> result=inscripcionCursoService.findAll();

        assertThat(result).hasSize(101);
        assertThat(result).contains(this.inscripcionCursoTest); //verificar DTO
        verify(inscripcionCursoRepository,times(1)).findAll();
    }

    @Test
    @DisplayName("Debe encontrar una inscripcion curso por ID")
    public void shouldFindInscripcionCursoById() {
        when(inscripcionCursoRepository.findById(1L)).thenReturn(Optional.of(this.inscripcionCursoTest));
        InscripcionCurso result = inscripcionCursoService.findById(1L);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.inscripcionCursoTest);
        verify(inscripcionCursoRepository,times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe retornar una exception cuando una inscripcion curso ID no exista")
    public void shouldReturnAnExceptionInFindInscripcionCursoById() {
        Long idInexistente=99L;
        when(inscripcionCursoRepository.findById(idInexistente)).thenReturn(Optional.empty());
        assertThatThrownBy(() ->{
            inscripcionCursoService.findById(idInexistente);
        }).isInstanceOf(InscripcionCursoException.class)
                .hasMessageContaining("La inscripcion con ID: "+idInexistente+" no existen en la base de datos");
        verify(inscripcionCursoRepository,times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("Debe guardar una nueva Inscripcion curso")
    public void shouldSaveInscripcionCurso() {
        when(inscripcionCursoRepository.save(any(InscripcionCurso.class))).thenReturn(this.inscripcionCursoTest);
        InscripcionCurso result = inscripcionCursoService.save(this.inscripcionCursoTest);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.inscripcionCursoTest);
        verify(inscripcionCursoRepository, times(1)).save(any(InscripcionCurso.class));
    }

    @Test
    @DisplayName("Debe actualizar una inscripcion curso")
    public void shouldUpdateInscripcionCurso() {
        Long idInscripcion = 1L;

        InscripcionCurso inscripcionCursoExistente = new InscripcionCurso(
                1L,
                LocalDate.now(),
                18000,
                1L,
                1L,
                1L,
                1L
        );
        InscripcionCurso inscripcionCursoActualizado = new InscripcionCurso(
                1L,
                LocalDate.now(),
                40000,
                1L,
                1L,
                1L,
                1L
        );

        when(inscripcionCursoRepository.findById(idInscripcion)).thenReturn(Optional.of(inscripcionCursoExistente));
        when(inscripcionCursoRepository.save(any(InscripcionCurso.class))).thenReturn(inscripcionCursoActualizado);

        InscripcionCurso result = inscripcionCursoService.update(idInscripcion, inscripcionCursoActualizado);

        assertThat(result.getCostoInscripcion()).isEqualTo(40000);

        verify(inscripcionCursoRepository, times(1)).findById(idInscripcion);
        verify(inscripcionCursoRepository, times(1)).save(any(InscripcionCurso.class));
    }

    @Test
    @DisplayName("Debe eliminar una inscripcion curso por ID")
    public void shouldDeleteInscripcionCursoById() {
        Long idInscripcion = 1L;
        doNothing().when(inscripcionCursoRepository).deleteById(idInscripcion);
        inscripcionCursoService.deleteById(idInscripcion);

        verify(inscripcionCursoRepository, times(1)).deleteById(idInscripcion);
    }


}
