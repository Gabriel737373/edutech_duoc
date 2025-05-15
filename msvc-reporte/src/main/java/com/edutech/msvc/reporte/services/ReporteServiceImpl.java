package com.edutech.msvc.reporte.services;

import com.edutech.msvc.reporte.exceptions.ReporteException;
import com.edutech.msvc.reporte.models.entities.Reporte;
import com.edutech.msvc.reporte.repositories.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteServiceImpl implements ReporteService{

    @Autowired
    private ReporteRepository reporteRepository;

    @Override
    public List<Reporte> findAll() { return this.reporteRepository.findAll(); }

    @Override
    public Reporte findById(Long id) {
        return this.reporteRepository.findById(id).orElseThrow(
                () -> new ReporteException("El reporte con id " + id + " no se encuentra en la base de datos")
        );
    }

    // Falta implementar exception (try / catch ?)
    @Override
    public Reporte save(Reporte reporte) {
        return this.reporteRepository.save(reporte);
    }

    @Override
    public Reporte update(Reporte reporte) {
        return this.reporteRepository.save(reporte);
    }

}
