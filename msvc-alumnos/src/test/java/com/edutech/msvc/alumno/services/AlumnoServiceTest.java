package com.edutech.msvc.alumno.services;

import com.edutech.msvc.alumno.exceptions.AlumnoException;
import com.edutech.msvc.alumno.models.entities.Alumno;
import com.edutech.msvc.alumno.repositories.AlumnoRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlumnoServiceTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    private Alumno alumnoPrueba;

    private List<Alumno> alumnos = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        Faker faker = new Faker(Locale.of("es", "CL"));
        for (int i = 0; i < 1000; i++) {
            Alumno alumno = new Alumno();
            alumno.setIdAlumno((long) i);
            alumno.setNombreCompleto(faker.name().fullName());
            alumno.setCorreo(faker.internet().emailAddress() + "_" + i);

            this.alumnos.add(alumno);
        }
        this.alumnoPrueba = new Alumno(
            1L, "Ariel Molina", "ari.molinam@gmail.com"
        );
    }

    @Test
    @DisplayName("Debe listar todos los Alumnos")
    public void shouldFindAllAlumnos() {
        this.alumnos.add(this.alumnoPrueba);
        when(alumnoRepository.findAll()).thenReturn(this.alumnos);
        List<Alumno> result = alumnoService.findAll();

        assertThat(result).hasSize(101);
        assertThat(result).contains(this.alumnoPrueba);
        verify(alumnoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe encontrar un Alumno por ID")
    public void shouldFindAlumnoById() {
        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(this.alumnoPrueba));
        Alumno result = alumnoService.findById(1L);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.alumnoPrueba);
        verify(alumnoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe retornar una exception cuando un Alumno ID no exista")
    public void shouldNotFindAlumnoById() {
        Long idInexistente = 999L;
        when(alumnoRepository.findById(idInexistente)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> {
            alumnoService.findById(idInexistente);
        }).isInstanceOf(AlumnoException.class)
                .hasMessageContaining("El alumno con id " + idInexistente + " no se encuentra en la base de datos");
        verify(alumnoRepository, times(1)).findById(idInexistente);
    }



    @Test
    @DisplayName("Debe guardar un nuevo Alumno")
    public void shouldSaveAlumno() {
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(this.alumnoPrueba);
        Alumno result = alumnoService.save(this.alumnoPrueba);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.alumnoPrueba);
        verify(alumnoRepository, times(1)).save(any(Alumno.class));
    }

    @Test
    @DisplayName("Debe actualizar un Alumno existente")
    public void shouldUpdateAlumno() {
        Long idAlumno = 1L;

        Alumno alumnoExistente = new Alumno(idAlumno, "Juan Pérez", "juan.perez@gmail.com");
        Alumno alumnoActualizado = new Alumno(idAlumno, "Ariel Molina", "ari.molinam@gmail.com");

        when(alumnoRepository.findById(idAlumno)).thenReturn(Optional.of(alumnoExistente));
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumnoActualizado);

        Alumno result = alumnoService.update(idAlumno, alumnoActualizado);

        assertThat(result.getNombreCompleto()).isEqualTo("Ariel Molina");
        assertThat(result.getCorreo()).isEqualTo("ari.molinam@gmail.com");

        verify(alumnoRepository, times(1)).findById(idAlumno);
        verify(alumnoRepository, times(1)).save(any(Alumno.class));
    }

    @Test
    @DisplayName("Debe eliminar un Alumno por ID")
    public void shouldDeleteAlumnoById() {
        Long idAlumno = 1L;

        // Simular que el alumno existe
        when(alumnoRepository.findById(idAlumno)).thenReturn(Optional.of(this.alumnoPrueba));

        alumnoService.deleteById(idAlumno);

        verify(alumnoRepository, times(1)).findById(idAlumno);
        verify(alumnoRepository, times(1)).deleteById(idAlumno);
    }




}
