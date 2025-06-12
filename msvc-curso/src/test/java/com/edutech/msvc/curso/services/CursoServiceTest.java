package com.edutech.msvc.curso.services;

import com.edutech.msvc.curso.exceptions.CursoException;
import com.edutech.msvc.curso.models.entities.Curso;
import com.edutech.msvc.curso.repositories.CursoRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private CursoServiceImpl cursoService;

    private Curso cursoPrueba;

    private List<Curso> cursos = new ArrayList<>();

    @BeforeEach
    public void setUp() {

        Faker faker = new Faker(Locale.of("es", "CL"));
        for (int i = 0; i < 100; i++) {
            Curso curso = new Curso();
            curso.setIdCurso((long) i);
            curso.setNombreCurso(faker.commerce().productName());
            curso.setDescripcionCurso(faker.lorem().paragraph().substring(20, 255));
            curso.setPrecioCurso(faker.number().numberBetween(150000, 750000));

            this.cursos.add(curso);
        }
        this.cursoPrueba = new Curso(
                 1L, "Curso de JS", "Introduccion a Java Script", 150000
         );

    }

    @Test
    @DisplayName("Debe listar todos los cursos")
    public void shouldFindAllCursos() {
        this.cursos.add(this.cursoPrueba);
        when(cursoRepository.findAll()).thenReturn(this.cursos);
        List<Curso> result = cursoService.findAll();

        assertThat(result).hasSize(101);
        assertThat(result).contains(this.cursoPrueba);
        verify(cursoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe entregar una exception cuando curso id no exista")
    public void shouldNotFindCursoById() {
        Long idInexistente = 999L;
        when(cursoRepository.findById(idInexistente)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> {
            cursoService.findById(idInexistente);
        }).isInstanceOf(CursoException.class)
                .hasMessageContaining("El curso con id " + idInexistente
                        + "no se encuentra en la base de datos");
        verify(cursoRepository, times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("Debe guardar un nuevo curso")
    public void shouldSaveCurso(){
        when(cursoRepository.save(any(Curso.class))).thenReturn(this.cursoPrueba);
        Curso result = cursoService.save(this.cursoPrueba);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.cursoPrueba);
        verify(cursoRepository, times(1)).save(any(Curso.class));
    }

}
