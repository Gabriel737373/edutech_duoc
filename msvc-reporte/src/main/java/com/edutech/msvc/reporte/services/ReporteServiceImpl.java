package com.edutech.msvc.reporte.services;

import com.edutech.msvc.reporte.clients.GerenteCursoClientRest;
import com.edutech.msvc.reporte.exceptions.ReporteException;
import com.edutech.msvc.reporte.models.entities.Reporte;
import com.edutech.msvc.reporte.repositories.ReporteRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteServiceImpl implements ReporteService{

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private GerenteCursoClientRest gerenteCursoClientRest;

    @Override
    public List<Reporte> findAll() {
        return this.reporteRepository.findAll();
    }

    @Override
    public Reporte findById(Long id) {
        return this.reporteRepository.findById(id).orElseThrow(
                () -> new ReporteException("El reporte con id " + id + " no se encuentra en la base de datos")
        );
    }

    @Override
    public List<Reporte> findByGerenteCursoId(Long gerenteCursoId) {
        return this.reporteRepository.findByIdGerenteCurso(gerenteCursoId);
    }

    @Override
    public Reporte save(Reporte reporte) {
        try {
            gerenteCursoClientRest.findById(reporte.getIdGerenteCurso());
        } catch (FeignException ex) {
            throw new ReporteException("El gerente no se encuentra en la base de datos");
        }
        return this.reporteRepository.save(reporte);
    }

    @Override
    public Reporte update(Long id, Reporte reporte) {
        Reporte reporteUpdateEstado = reporteRepository.findById(id).orElseThrow(
                () -> new ReporteException("El reporte con id: " + id + " no se encuentra en la base de datos")
        );
        reporteUpdateEstado.setEstado(reporte.getEstado());
        return reporteRepository.save(reporteUpdateEstado);
    }

    @Override
    public void deleteById(Long id) {
        reporteRepository.deleteById(id);
    }
}
