package com.edutech.msvc.evaluacion.init;

import com.edutech.msvc.evaluacion.models.entities.Evaluacion;
import com.edutech.msvc.evaluacion.repositories.EvaluacionRepository;
import net.datafaker.Faker;
import org.hibernate.validator.constraints.CodePointLength;
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
    private EvaluacionRepository evaluacionRepository; // Cambiado de ProfesorRepository

    private static final Logger logger = LoggerFactory.getLogger(LoadDataBase.class);

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


    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(Locale.of("es","CL"));
        if (evaluacionRepository.count()== 0){
            for (int i = 0; i < 100; i++){

                Evaluacion evaluacion = new Evaluacion();

                evaluacion.setNombreEvaluacion(nombreEvaluacion.get(faker.random().nextInt(nombreEvaluacion.size())));
                evaluacion.setMateriaEvaluacion(materiaEvaluaciones.get(faker.random().nextInt(materiaEvaluaciones.size())));

                evaluacion = evaluacionRepository.save(evaluacion);
                logger.info("La evaluación creada es: {}", evaluacion.toString());
            }
        }
    }
}
