package com.edutech.msvc.evaluacion.services;

import com.edutech.msvc.evaluacion.exceptions.EvaluacionException;
import com.edutech.msvc.evaluacion.models.entities.Evaluacion;
import com.edutech.msvc.evaluacion.repositories.EvaluacionRepository;
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
public class EvaluacionServiceTest {

    @Mock
    private EvaluacionRepository evaluacionRepository;

    @InjectMocks
    private EvaluacionServiceImpl evaluacionService;

    private Evaluacion evaluacionPrueba;

    private List<Evaluacion> evaluaciones = new ArrayList<>();

    @BeforeEach
    public void setUp(){

        Faker faker = new Faker(Locale.of("es","cl"));

        //Lista de materias para las evaluaciones
        List<String> materiaEvaluaciones = Arrays.asList(
                "Introducción a la Programación",
                "Fundamentos de la Ingeniería",
                "Matemáticas Avanzadas",
                "Teoría de la Relatividad",
                "Gestión de Proyectos",
                "Análisis de Datos en Ciencias Sociales",
                "Desarrollo Web con Java",
                "Ciberseguridad: Protección de Sistemas",
                "Inteligencia Artificial y Aprendizaje Automático",
                "Fundamentos de Electrónica",
                "Desarrollo fullstack"
        );

        //Lista auxiliar para nombre de la evaluacion
        List<String> nombreEvaluacion = Arrays.asList(
                "Evaluacion Inicial",
                "Evaluacion Intermedia",
                "Evaluacion Avanzada",
                "Examen final"
        );


        for (int i = 0; i < 100; i++){
            Evaluacion evaluacion = new Evaluacion();
            evaluacion.setIdEvaluacion((long) i);
            evaluacion.setNombreEvaluacion(nombreEvaluacion.get(faker.random().nextInt(nombreEvaluacion.size())));
            evaluacion.setMateriaEvaluacion(materiaEvaluaciones.get(faker.random().nextInt(materiaEvaluaciones.size())));

            this.evaluaciones.add(evaluacion);
        }
        this.evaluacionPrueba = new Evaluacion(
                1L, "Examen final", "Desarrollo fullstack"
        );
    }

    @Test
    @DisplayName("Debe de listar todos las evaluaciones")
    public void shouldFindAllEvaluaciones(){
        this.evaluaciones.add(this.evaluacionPrueba);
        when(evaluacionRepository.findAll()).thenReturn(this.evaluaciones);
        List<Evaluacion> result = evaluacionService.findAll();

        assertThat(result).hasSize(101);
        assertThat(result).contains(this.evaluacionPrueba);
        verify(evaluacionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe entregar una exception cuando evaluacion id no exista")
    public void evaluacionById() {
        Long idInexistente = 999L;
        when(evaluacionRepository.findById(idInexistente)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> {
            evaluacionService.findById(idInexistente);
        }).isInstanceOf(EvaluacionException.class)
                .hasMessageContaining("El evaluacion con id " + idInexistente
                        + "no se encuentra en la base de datos");
        verify(evaluacionRepository, times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("Debe guardar un nuevo Evaluacion")
    public void shouldSaveCurso(){
        when(evaluacionRepository.save(any(Evaluacion.class))).thenReturn(this.evaluacionPrueba);
        Evaluacion result = evaluacionService.save(this.evaluacionPrueba);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.evaluacionPrueba);
        verify(evaluacionRepository, times(1)).save(any(Evaluacion.class));
    }

}
