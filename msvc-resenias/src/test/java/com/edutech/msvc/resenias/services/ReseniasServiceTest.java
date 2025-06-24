package com.edutech.msvc.resenias.services;

import com.edutech.msvc.resenias.clients.AlumnoClientRest;
import com.edutech.msvc.resenias.clients.CursoClientRest;
import com.edutech.msvc.resenias.models.Alumno;
import com.edutech.msvc.resenias.models.Curso;
import com.edutech.msvc.resenias.models.entities.Resenia;
import com.edutech.msvc.resenias.repositories.ReseniaRepository;
import com.jonaour.msvc.inscripcionCurso.exceptions.InscripcionCursoException;
import com.jonaour.msvc.inscripcionCurso.models.entities.InscripcionCurso;
import net.datafaker.Faker;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReseniasServiceTest {

    @Mock
    private AlumnoClientRest alumnoClientRest;

    @Mock
    private CursoClientRest cursoClientRest;

    @Mock
    private ReseniaRepository reseniaRepository;

    @InjectMocks
    private ReseniaServiceImpl reseniaService;

    private Resenia reseniaPrueba;

    private List<Resenia> resenias = new ArrayList<Resenia>();

    private static final String[] COMENTARIOS={"Muy bueno","Muy malo"};
    private static final int[] VALORACIONES={1,2,3,4,5};
    private static final Random random=new Random();

    private Alumno alumnoTest;
    private Curso cursoTest;
    private Resenia reseniaTest;

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
                "Introducción a la Programación",
                "Este curso cubre los fundamentos básicos de la programación y estructuras de datos",
                300000
        );

        reseniaTest = new Resenia(
                1L,
                "Bueno",
                4,
                1L,
                1L
        );
    }

     */


    @BeforeEach
    public void setUp(){
        Faker faker = new Faker(Locale.of("es","CL"));
        for (int i=0; i<100; i++){
            Resenia resenia = new Resenia();
            resenia.setIdResenia((long) i);

            int valoraciones=VALORACIONES[random.nextInt(VALORACIONES.length)];
            resenia.setValoracionResenia(valoraciones);

            String comentarios=COMENTARIOS[random.nextInt(COMENTARIOS.length)];
            resenia.setComentarioResenia(comentarios);

            long idAlumno= (long) faker.number().numberBetween(1,20);
            resenia.setIdAlumno(idAlumno);

            long idCurso= (long) faker.number().numberBetween(1,20);
            resenia.setIdCurso(idCurso);

            this.resenias.add(resenia);
        }
        this.reseniaPrueba = new Resenia(
                1L,"Muy bueno",4,1L,1L
        );
    }

    @Test
    @DisplayName("Debe listar todas las reseñas")
    public void shouldFindAllResenias() {
        this.resenias.add(this.reseniaPrueba);
        when(reseniaRepository.findAll()).thenReturn(this.resenias);
        List<Resenia> result = reseniaService.findAll();

        assertThat(result).hasSize(101);
        assertThat(result).contains(this.reseniaPrueba);
        verify(reseniaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe encontrar una reseña por ID")
    public void shouldFindReseniaById() {
        when(reseniaRepository.findById(1L)).thenReturn(Optional.ofNullable(this.reseniaPrueba));
        Resenia result = reseniaService.findById(1L);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.reseniaTest);
        verify(reseniaRepository,times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe guardar una nueva reseña")
    public void shouldSaveResenia(){
        when(reseniaRepository.save(any(Resenia.class))).thenReturn(this.reseniaPrueba);
        Resenia result = reseniaService.save(this.reseniaPrueba);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.reseniaPrueba);
        verify(reseniaRepository, times(1)).save(any(Resenia.class));
    }

    @Test
    @DisplayName("Debe actualizar una reseña")
    public void shouldUpdateResenia() {
        Long idResenia = 1L;

        Resenia reseniaExistente = new Resenia(
                1L,
                "Bueno",
                4,
                1L,
                1L
        );
        Resenia reseniaActualizado = new Resenia(
                1L,
                "Excelente!",
                8,
                1L,
                1L
        );

        when(reseniaRepository.findById(idResenia)).thenReturn(Optional.of(reseniaExistente));
        when(reseniaRepository.save(any(Resenia.class))).thenReturn(reseniaActualizado);

        Resenia result = reseniaService.update(idResenia, reseniaActualizado);

        assertThat(result.getValoracionResenia()).isEqualTo(8);

        verify(reseniaRepository, times(1)).findById(idResenia);
        verify(reseniaRepository, times(1)).save(any(Resenia.class));
    }

    @Test
    @DisplayName("Debe eliminar una reseña por ID")
    public void shouldDeleteReseniaById() {
        Long idResenia = 1L;
        doNothing().when(reseniaRepository).deleteById(idResenia);
        reseniaService.deleteById(idResenia);

        verify(reseniaRepository, times(1)).deleteById(idResenia);
    }
}
