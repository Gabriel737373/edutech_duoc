package com.edutech.msvc.curso.services;

import com.edutech.msvc.curso.models.entities.Curso;
import com.edutech.msvc.curso.repositories.CursoRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CursoServiceTest {

    @Mock
    private CursoRepository cursoRepository;

    @InjectMocks
    private CursoServiceImpl cursoService;

    private Curso cursoPrueba;

    private List<Curso> cursos = new ArrayList<>();

    @BeforeEach
    public void setUp(){

        Faker faker = new Faker(Locale.of("es","CL"));
        for(int i=0;i<100;i++){
            Curso curso = new Curso();
            curso.setIdCurso((long) i);
            curso.setNombreCurso(faker.commerce().productName());
            curso.setDescripcionCurso(faker.lorem().paragraph());
            curso.setPrecioCurso(faker.number().numberBetween(150000, 750000));



            this.cursos.add(curso);
        }


    }





}
