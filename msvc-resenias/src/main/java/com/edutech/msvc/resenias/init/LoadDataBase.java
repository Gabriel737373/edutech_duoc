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

@Profile("dev")
@Component
public class LoadDataBase {

    @Autowired
    private ReseniaRepository reseniaRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoadDataBase.class);

    public void run(String... args) throws Exception {
        Faker faker = new Faker(Locale.of("es","CL"));
        if(reseniaRepository.count()==0){

            List<String> nombresDeComentario = Arrays.asList(
                    "Muy Malo",
                    "Malo",
                    "Bueno",
                    "Muy bueno",
                    "Excelente"
            );

            for(int i=0;i<1000;i++){
                Resenia resenia = new Resenia();
                String comentario = nombresDeComentario.get(faker.random().nextInt(nombresDeComentario.size()));
                resenia.setComentarioResenia(comentario);
                resenia.setValoracionResenia(faker.number().numberBetween(1, 10));
                resenia = reseniaRepository.save(resenia);
                logger.info("La reseña creada es: {}",resenia.toString());

            }
        }
    }
}
