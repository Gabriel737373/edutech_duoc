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

import java.util.Locale;

@Profile("dev")
@Component
public class LoadDataBase implements CommandLineRunner {
    @Autowired
    private CursoRepository cursoRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoadDataBase.class);

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(Locale.of("es","CL"));
        if(cursoRepository.count() == 0) {
            for (int i = 0; i < 10000; i++) {
                Curso curso = new Curso();

                curso.setNombreCurso(faker.commerce().productName());
                curso.setDescripcionCurso(faker.lorem().paragraph().substring(20, 255));
                curso.setPrecioCurso(faker.number().numberBetween(150000, 750000));

                curso = cursoRepository.save(curso);
                logger.info("El curso creado es: {}", curso.toString());

            }
        }
    }
}
