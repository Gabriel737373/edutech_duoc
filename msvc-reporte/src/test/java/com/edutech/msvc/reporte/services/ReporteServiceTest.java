package com.edutech.msvc.reporte.services;

import com.edutech.msvc.reporte.exceptions.ReporteException;
import com.edutech.msvc.reporte.models.entities.Reporte;
import com.edutech.msvc.reporte.repositories.ReporteRepository;
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
public class ReporteServiceTest {

    @Mock
    private ReporteRepository reporteRepository;

    @InjectMocks
    private ReporteServiceImpl reporteService;

    private Reporte reportePrueba;

    private List<Reporte> reportes=new ArrayList<>();

    private static final String[] ESTADOS={"Pendiente","En curso","Completado"};
    private static final String[] DESCRIPCIONES={"Todo malo","Todo bueno"};
    private static final Random random=new Random();

    @BeforeEach
    public void setUp() {
        Faker faker = new Faker(Locale.of("es","CL"));
        for (int i = 0; i < 100; i++) {
            Reporte reporte = new Reporte();
            reporte.setIdReporte((long) i);

            String descripcion=DESCRIPCIONES[random.nextInt(DESCRIPCIONES.length)];
            reporte.setDescripcion(descripcion);

            String estado=ESTADOS[random.nextInt(ESTADOS.length)];
            reporte.setEstado(estado);

            long idGerente= (long) faker.number().numberBetween(1,20);
            reporte.setIdGerenteCurso(idGerente);

            this.reportes.add(reporte);
        }
        this.reportePrueba=new Reporte(
                1L,"En curso","Todo malo",1L
        );
    }

    @Test
    @DisplayName("Debe listar todos los reportes")
    public void shouldFindAllReportes() {
        this.reportes.add(this.reportePrueba);
        when(reporteRepository.findAll()).thenReturn(this.reportes);
        List<Reporte> result=reporteService.findAll();

        assertThat(result).hasSize(101);
        assertThat(result).contains(this.reportePrueba);
        verify(reporteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe encontrar un reporte por ID")
    public void shouldFindReporteById() {
        when(reporteRepository.findById(1L)).thenReturn(Optional.of(this.reportePrueba));
        Reporte result=reporteService.findById(1L);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.reportePrueba);
        verify(reporteRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debe retornar una exception cuando un report por ID no exista")
    public void shouldNotfindReporteById() {
        Long idInexistente=999L;
        when(reporteRepository.findById(idInexistente)).thenReturn(Optional.empty());
        assertThatThrownBy(()->{
            reporteService.findById(idInexistente);
        }).isInstanceOf(ReporteException.class)
                .hasMessageContaining("El reporte con id "+idInexistente+" no existe");
        verify(reporteRepository, times(1)).findById(idInexistente);
    }

    @Test
    @DisplayName("Debe guardar un nuevo Reporte")
    public void shouldSaveReporte() {
        when(reporteRepository.save(any(Reporte.class))).thenReturn(this.reportePrueba);
        Reporte result=reporteService.save(this.reportePrueba);
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.reportePrueba);
        verify(reporteRepository, times(1)).save(any(Reporte.class));
    }
}
