package com.edutech.msvc.reporte.services;

import com.edutech.msvc.reporte.models.entities.Reporte;

import java.util.List;

public interface ReporteService {
    List<Reporte> findAll();
    Reporte findById(Long id);
    List<Reporte> findByGerenteCursoId(Long gerenteCursoId);
    Reporte save(Reporte reporte);
    Reporte update(Long id, Reporte reporte);
    void deleteById(Long id);
}
