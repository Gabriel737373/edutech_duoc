package com.jonaour.msvc.inscripcionCurso.init;

import com.jonaour.msvc.inscripcionCurso.models.entities.InscripcionCurso;
import com.jonaour.msvc.inscripcionCurso.repositories.InscripcionCursoRepository;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Profile("dev")
@Component
public class LoadDataBase implements CommandLineRunner {

    @Autowired
    private InscripcionCursoRepository inscripcionCursoRepository;
    private static final Logger logger = LoggerFactory.getLogger(LoadDataBase.class);

    @Override
    public void run(String... args) throws Exception{
        Faker faker = new Faker(Locale.of("es","CL"));
        if(inscripcionCursoRepository.count() == 0) {
            for (int i = 0; i < 100; i++) {
                InscripcionCurso inscripcionCurso = new InscripcionCurso();
                inscripcionCurso.setCostoInscripcion(faker.number().numberBetween(1, 100));

                String fecha = faker.timeAndDate().birthday(1, 99, "yyyy/MM/dd");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate fechaLocal = LocalDate.parse(fecha, formatter);
                inscripcionCurso.setFechaInscripcion(fechaLocal);

                long idAlumno= (long) faker.number().numberBetween(1,100);
                inscripcionCurso.setIdAlumno(idAlumno);

                long idCurso= (long) faker.number().numberBetween(1,20);
                inscripcionCurso.setIdCurso(idCurso);

                long idProfesor= (long) faker.number().numberBetween(1,20);
                inscripcionCurso.setIdProfesor(idProfesor);

                long idGerente= (long) faker.number().numberBetween(1,20);
                inscripcionCurso.setIdGerenteCurso(idGerente);

                inscripcionCurso=inscripcionCursoRepository.save(inscripcionCurso);
                logger.info("La inscripcion curso creada es: {}",inscripcionCurso.toString());
            }
        }
    }
}
