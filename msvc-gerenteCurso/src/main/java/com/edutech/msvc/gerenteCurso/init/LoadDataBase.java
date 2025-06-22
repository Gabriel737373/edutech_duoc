package com.edutech.msvc.gerenteCurso.init;

import com.edutech.msvc.gerenteCurso.models.entities.GerenteCurso;
import com.edutech.msvc.gerenteCurso.repositories.GerenteCursoRepository;
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
    private GerenteCursoRepository gerenteCursoRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoadDataBase.class);

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(Locale.of("es","CL"));
        if(gerenteCursoRepository.count() == 0) {
            for(int i = 0; i < 10000; i++){
                GerenteCurso gerenteCurso = new GerenteCurso();
                gerenteCurso.setNombreCompleto(faker.name().fullName());
                gerenteCurso = gerenteCursoRepository.save(gerenteCurso);
                logger.info("El nombre del gerente es: {}", gerenteCurso.toString());
            }
        }
    }
}
