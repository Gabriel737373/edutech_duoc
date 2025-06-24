package com.edutech_duoc.msvc_profesor.init;

import com.edutech_duoc.msvc_profesor.models.entities.Profesor;
import com.edutech_duoc.msvc_profesor.repositories.ProfesorRepository;
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
    private ProfesorRepository profesorRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoadDataBase.class);

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(Locale.of("es","CL"));
        if (profesorRepository.count()== 0){
            for (int i = 0; i < 100; i++){
                Profesor profesor = new Profesor();

                profesor.setNombreProfesor(faker.name().firstName());
                profesor.setApellidoProfesor(faker.name().lastName());
                profesor.setCorreoProfesor(faker.internet().emailAddress() + "_" + i);

                profesor = profesorRepository.save(profesor);
                logger.info("El profesor creado es: {}", profesor.toString());
            }
        }
    }

}
