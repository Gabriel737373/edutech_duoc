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
    public void shouldFindAllGerenteCursos() {
        this.gerenteCursos.add(this.gerenteCursoPrueba);
        when(gerenteCursoRepository.findAll()).thenReturn(this.gerenteCursos);
        List<GerenteCurso> result = gerenteCursoService.findAll();

        assertThat(result).hasSize(101);
        assertThat(result).contains(this.gerenteCursoPrueba);
        verify(gerenteCursoRepository, times((1))).findAll();
    }

    @Test
    @DisplayName("Debe guardar un nuevo gerente")
    public void shouldSaveGerenteCurso() {
        when(gerenteCursoRepository.save(any(GerenteCurso.class))).thenReturn(this.gerenteCursoPrueba);
        GerenteCurso result = gerenteCursoService.save(this.gerenteCursoPrueba);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.gerenteCursoPrueba);
        verify(gerenteCursoRepository, times(1)).save(any(GerenteCurso.class));
    }

    @Test
    @DisplayName("Debe actualizar un gerente existente")
    public void shouldUpdateGerenteCurso() {
        Long idGerenteCurso = 1L;

        GerenteCurso GerenteCursoActualizado = new GerenteCurso(
                idGerenteCurso, "José Antonio Pérez Ruiz"
        );

        when(gerenteCursoRepository.findById(idGerenteCurso)).thenReturn(Optional.of(this.gerenteCursoPrueba));
        when(gerenteCursoRepository.save(any(GerenteCurso.class))).thenReturn(GerenteCursoActualizado);

        GerenteCurso result = gerenteCursoService.update(idGerenteCurso, GerenteCursoActualizado);

        assertThat(result).isNotNull();
        assertThat(result.getNombreCompleto()).isEqualTo("José Antonio Pérez Ruiz");

        verify(gerenteCursoRepository).findById(idGerenteCurso);
        verify(gerenteCursoRepository).save(any(GerenteCurso.class));
    }

    @Test
    @DisplayName("Debe encontrar gerentes por id")
    public void shouldFindGerenteCursoById() {
        when(gerenteCursoRepository.findById(1L)).thenReturn(Optional.of(this.gerenteCursoPrueba));
        GerenteCurso result = gerenteCursoService.findById(1L);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.gerenteCursoPrueba);
        verify(gerenteCursoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe entregar una excepcion cuando gerente id no exista")
    public void shouldNotFindGerenteCursoById() {}

    @Test
    @DisplayName("Debe eliminar un curso por ID")
    public void shouldDeleteGerenteCursoById() {
        Long idGerenteCurso = 1L;

        doNothing().when(gerenteCursoRepository).deleteById(idGerenteCurso);
        gerenteCursoService.deleteById(idGerenteCurso);

        verify(gerenteCursoRepository, times(1)).deleteById(idGerenteCurso);
    }
}
