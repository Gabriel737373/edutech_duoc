package com.jonaour.msvc.inscripcionCurso.services;


import com.jonaour.msvc.inscripcionCurso.clients.AlumnoClientRest;
import com.jonaour.msvc.inscripcionCurso.clients.CursoClientRest;
import com.jonaour.msvc.inscripcionCurso.clients.GerenteCursoClienteRest;
import com.jonaour.msvc.inscripcionCurso.clients.ProfesorClientRest;
import com.jonaour.msvc.inscripcionCurso.dtos.InscripcionCursoDTO;
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


    //Test para uno de cada uno
    /*
    @BeforeEach
    public void setUp() {
        alumnoTest = new Alumno(
                1L,
                "José Naour",
                "jo.naour@duocuc.cl"
        );
        cursoTest = new Curso(
                1L,
                "Python",
                "Aprende python desde 0",
                15000
        );
        gerenteCursoTest = new GerenteCurso(
                1L,
                "Darwin Nuñez"
        );
        profesorTest = new Profesor(
                1L,
                "Cesar",
                "Carrasco",
                "ce.carrasco@profesor.duocuc.cl"
        );
        inscripcionCursoTest = new InscripcionCurso(
                1L,
                LocalDate.now(),
                18000,
                1L,
                1L,
                1L,
                1L
        );
    }
    */

    @BeforeEach
    public void setUp() {
        Faker faker = new Faker(Locale.of("es", "CL"));
        for (int i = 0; i < 20; i++) {
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
        List<InscripcionCursoDTO> result=inscripcionCursoService.findAll(); //verificar DTO

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
        Long idInexistente=999L;
        when(inscripcionCursoRepository.findById(idInexistente)).thenReturn(Optional.empty());
        assertThatThrownBy(() ->{
            inscripcionCursoService.findById(idInexistente);
        }).isInstanceOf(InscripcionCursoException.class)
                .hasMessageContaining("La inscripcion curso con ID "+idInexistente+" no existe");
        verify(inscripcionCursoRepository,times(1)).findById(idInexistente);
    }

}
