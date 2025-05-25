package com.edutech.msvc.reporte.repositories;

import com.edutech.msvc.reporte.models.entities.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    List<Reporte> findByIdGerenteCurso(Long idGerente);
}
