package com.edutech.msvc.alumno.init;

import com.edutech.msvc.alumno.models.entities.Alumno;
import com.edutech.msvc.alumno.repositories.AlumnoRepository;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Profile("dev")
@Component
public class LoadDataBase implements CommandLineRunner {
    @Autowired
    private AlumnoRepository alumnoRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoadDataBase.class);

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(Locale.of("es", "CL"));
        if (alumnoRepository.count() == 0) {
            for (int i = 0; i < 1000; i++) {
                Alumno alumno = new Alumno();

                alumno.setNombreCompleto(faker.name().fullName());
                alumno.setCorreo(faker.internet().emailAddress() + "_" + i);

                alumno = alumnoRepository.save(alumno);
                logger.info("El alumno creado es: {}", alumno.toString());

            }
        }
    }


}
