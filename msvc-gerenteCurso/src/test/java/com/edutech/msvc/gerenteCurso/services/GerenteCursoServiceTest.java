package com.edutech.msvc.gerenteCurso.services;

import com.edutech.msvc.gerenteCurso.models.entities.GerenteCurso;
import com.edutech.msvc.gerenteCurso.repositories.GerenteCursoRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GerenteCursoServiceTest {

    @Mock
    private GerenteCursoRepository gerenteCursoRepository;

    @InjectMocks
    private GerenteCursoServiceImpl gerenteCursoService;

    private GerenteCurso gerenteCursoPrueba;

    private List<GerenteCurso> gerenteCursos = new ArrayList<>();

    @BeforeEach
    public void setUp() {

        Faker faker = new Faker(Locale.of("es", "CL"));
        for (int i = 0; i < 100; i++) {
            GerenteCurso gerenteCurso = new GerenteCurso();
            gerenteCurso.setIdGerenteCurso((long) i);
            gerenteCurso.setNombreCompleto(faker.name().fullName());
            this.gerenteCursos.add(gerenteCurso);
        }
        this.gerenteCursoPrueba = new GerenteCurso(
            1L,"José Antonio Pérez Ruiz"
        );
    }

    @Test
    @DisplayName("Debe listar todos los gerentes")
    public void shouldFindAllGerentes() {
        this.gerenteCursos.add(this.gerenteCursoPrueba);
        when(gerenteCursoRepository.findAll()).thenReturn(this.gerenteCursos);
        List<GerenteCurso> result = gerenteCursoService.findAll();

        assertThat(result).hasSize(101);
        assertThat(result).contains(this.gerenteCursoPrueba);
        verify(gerenteCursoRepository, times((1))).findAll();
    }

    @Test
    @DisplayName("Debe encontrar gerentes por id")
    public void shouldFindGerenteById() {
        when(gerenteCursoRepository.findById(1L)).thenReturn(Optional.of(this.gerenteCursoPrueba));
        GerenteCurso result = gerenteCursoService.findById(1L);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.gerenteCursoPrueba);
        verify(gerenteCursoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe entregar una excepcion cuando gerente id no exista")
    public void shouldNotFindGerenteById() {}
}
