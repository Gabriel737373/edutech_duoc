package com.edutech_duoc.msvc_profesor.services;

import com.edutech_duoc.msvc_profesor.models.entities.Profesor;
import com.edutech_duoc.msvc_profesor.repositories.ProfesorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfesorServiceTest {

    @Mock
    private ProfesorRepository profesorRepository;

    @InjectMocks
    private ProfesorServiceImpl profesorService;

    private Profesor profesorPrueba;

    private List<Profesor> profesores = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        this.profesorPrueba = new Profesor(1L, "11111111-1", "Emanuel perez", "Emanuel@gmail.com"
        );
    }

    @Test
    @DisplayName("Debe listar todos los profesores")
    public void shouldFindAllProfesores(){
        Profesor otroProfesor = new Profesor(2L, "22222222-2", "Gonzalo perez", "Gonzalo@gmail.com"
        );

        List<Profesor> listadoProfesores = Arrays.asList(this.profesorPrueba, otroProfesor);

        when(profesorRepository.findAll()).thenReturn(listadoProfesores);

        List<Profesor> result = profesorService.findAll();
    }
}
