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

        Faker faker = new Faker(new Locale("es", "CL"));

        // Lista de nombres de cursos personalizados
        List<String> nombresDeCursos = Arrays.asList(
                "Introducción a la Programación",
                "Fundamentos de la Ingeniería",
                "Matemáticas Avanzadas",
                "Teoría de la Relatividad",
                "Gestión de Proyectos",
                "Análisis de Datos en Ciencias Sociales",
                "Desarrollo Web con Java",
                "Ciberseguridad: Protección de Sistemas",
                "Inteligencia Artificial y Aprendizaje Automático",
                "Fundamentos de Electrónica"
        );

        // Lista de descripciones de cursos
        List<String> descripcionesDeCursos = Arrays.asList(
                "Este curso cubre los fundamentos básicos de la programación y estructuras de datos.",
                "Curso orientado a la introducción a los conceptos y aplicaciones de la ingeniería.",
                "Estudiaremos álgebra avanzada, cálculo y ecuaciones diferenciales.",
                "Exploramos la teoría y aplicaciones de la relatividad especial y general.",
                "Aprende a gestionar proyectos desde la planificación hasta la ejecución.",
                "En este curso analizaremos cómo se manejan grandes volúmenes de datos en diferentes áreas.",
                "Aprende a desarrollar aplicaciones web utilizando tecnologías modernas como Spring Boot.",
                "Este curso cubre las mejores prácticas para proteger sistemas informáticos de amenazas externas.",
                "Introducción a los conceptos de inteligencia artificial y cómo se entrenan modelos de aprendizaje automático.",
                "Curso enfocado en los principios y componentes básicos de la electrónica."
        );

        // Inicializar lista de cursos
        for (int i = 0; i < 100; i++) {
            Curso curso = new Curso();
            curso.setIdCurso((long) i);
            curso.setNombreCurso(nombresDeCursos.get(faker.random().nextInt(nombresDeCursos.size())));

            // Generar descripción aleatoria de la lista
            String descripcionCurso = descripcionesDeCursos.get(faker.random().nextInt(descripcionesDeCursos.size()));
            // Asegurarse de que la descripción no esté vacía
            if (descripcionCurso == null || descripcionCurso.trim().isEmpty()) {
                descripcionCurso = "Descripción predeterminada del curso.";
            } else if (descripcionCurso.length() > 255) {
                descripcionCurso = descripcionCurso.substring(0, 255);
            }

            curso.setDescripcionCurso(descripcionCurso);

            curso.setPrecioCurso(faker.number().numberBetween(150000, 750000));

            this.cursos.add(curso);
        }

        this.cursoPrueba = new Curso(
                1L, "Curso de JS", "Introducción a JavaScript", 150000
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
