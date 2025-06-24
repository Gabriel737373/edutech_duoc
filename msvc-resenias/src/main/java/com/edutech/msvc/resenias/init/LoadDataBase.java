package com.edutech.msvc.resenias.init;

import com.edutech.msvc.resenias.models.entities.Resenia;
import com.edutech.msvc.resenias.repositories.ReseniaRepository;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Profile("dev")
@Component
public class LoadDataBase {

    @Autowired
    private ReseniaRepository reseniaRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoadDataBase.class);
    private static final String[] COMENTARIOS={"Muy bueno","Muy malo"};
    private static final int[] VALORACIONES={1,2,3,4,5};
    private static final Random random=new Random();

    public void run(String... args) throws Exception {
        Faker faker = new Faker(Locale.of("es","CL"));
        if(reseniaRepository.count()==0){
            for(int i=0;i<100;i++){
                Resenia resenia = new Resenia();
                resenia.setValoracionResenia(VALORACIONES[random.nextInt(VALORACIONES.length)]);
                resenia.setComentarioResenia(COMENTARIOS[random.nextInt(COMENTARIOS.length)]);

                long idAlumno= (long) faker.number().numberBetween(1,20);
                resenia.setIdAlumno(idAlumno);

                long idCurso= (long) faker.number().numberBetween(1,20);
                resenia.setIdCurso(idCurso);

                resenia = reseniaRepository.save(resenia);
                logger.info("La reseña creada es: {}",resenia.toString());

            }
        }
    }
}
