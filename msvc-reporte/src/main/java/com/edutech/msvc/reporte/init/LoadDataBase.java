package com.edutech.msvc.reporte.init;

import com.edutech.msvc.reporte.models.entities.Reporte;
import com.edutech.msvc.reporte.repositories.ReporteRepository;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

@Profile("dev")
@Component
public class LoadDataBase implements CommandLineRunner {

    @Autowired
    private ReporteRepository reporteRepository;

    private static final String[] ESTADOS={"Pendiente","En curso","Completado"};
    private static final String[] DESCRIPCIONES={"Todo malo","Todo bueno"};
    private static final Random random=new Random();
    private static final Logger logger= LoggerFactory.getLogger(LoadDataBase.class);

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker(Locale.of("es","CL"));
        if (reporteRepository.count() == 0) {
            for (int i = 0; i < 100; i++) {
                Reporte reporte = new Reporte();

                String descripcion=DESCRIPCIONES[random.nextInt(DESCRIPCIONES.length)];
                reporte.setDescripcion(descripcion);

                String estado=ESTADOS[random.nextInt(ESTADOS.length)];
                reporte.setEstado(estado);

                long idGerente= (long) faker.number().numberBetween(1,20);
                reporte.setIdGerenteCurso(idGerente);

                reporte=reporteRepository.save(reporte);
                logger.info("El reporte creado es: {}",reporte.toString());
            }
        }
    }
}
