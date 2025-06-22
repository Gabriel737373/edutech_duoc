package com.edutech.msvc.curso.init;

import com.edutech.msvc.curso.models.entities.Curso;
import com.edutech.msvc.curso.repositories.CursoRepository;
import net.datafaker.Faker;
import org.hibernate.SessionFactory;
import org.hibernate.validator.constraints.br.CPF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Profile("dev")
@Component
public class LoadDataBase implements CommandLineRunner {
    @Autowired
    private CursoRepository cursoRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoadDataBase.class);

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(new Locale("es", "CL"));  // Usamos el constructor adecuado para la localización

        // Verifica si no hay cursos en la base de datos
        if (cursoRepository.count() == 0) {

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

            // Crear y guardar cursos
            for (int i = 0; i < 10000; i++) {
                Curso curso = new Curso();

                // Asigna un nombre de curso aleatorio de la lista
                curso.setNombreCurso(nombresDeCursos.get(faker.random().nextInt(nombresDeCursos.size())));

                // Asigna una descripción aleatoria de la lista
                String descripcionCurso = descripcionesDeCursos.get(faker.random().nextInt(descripcionesDeCursos.size()));
                // Asegurarse de que la descripción no esté vacía
                if (descripcionCurso == null || descripcionCurso.trim().isEmpty()) {
                    descripcionCurso = "Descripción predeterminada del curso.";  // Valor por defecto si es vacía
                } else if (descripcionCurso.length() > 255) {
                    descripcionCurso = descripcionCurso.substring(0, 255);  // Limitar la descripción si es muy larga
                }

                curso.setDescripcionCurso(descripcionCurso);

                // Asigna un precio aleatorio entre 150,000 y 750,000
                curso.setPrecioCurso(faker.number().numberBetween(150000, 750000));

                // Guardar el curso en la base de datos
                curso = cursoRepository.save(curso);
                logger.info("El curso creado es: {}", curso.toString());
            }
        }
    }

}
