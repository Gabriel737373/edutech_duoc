package com.edutech.msvc.evaluacion.services;

import com.edutech.msvc.evaluacion.exceptions.EvaluacionException;
import com.edutech.msvc.evaluacion.models.entities.Evaluacion;
import com.edutech.msvc.evaluacion.repositories.EvaluacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluacionServiceMpl implements EvaluacionService{

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Override
    public List<Evaluacion> findAll() {
        return this.evaluacionRepository.findAll();
    }

    @Override
    public Evaluacion findById(Long id) {
        return this.evaluacionRepository.findById(id).orElseThrow(
                () -> new EvaluacionException("Profesor con id "+id+" no encontrado")
        );
    }

    @Override
    public Evaluacion save(Evaluacion evaluacion) {
        return this.evaluacionRepository.save(evaluacion);
    }

    @Override
    public void deleteById(Long id) {
        evaluacionRepository.deleteById(id);
    }

    @Override
    public Evaluacion update(Long id, Evaluacion evaluacion) {
        Evaluacion evaluacion1 = evaluacionRepository.findById(id)
                .orElseThrow(() -> new EvaluacionException("Evaluacion con id "+id+" no encontrada"));
        evaluacion1.setNombreEvaluacion(evaluacion.getNombreEvaluacion());
        evaluacion1.setMateriaEvaluacion(evaluacion.getMateriaEvaluacion());
        return evaluacionRepository.save(evaluacion1);
    }
}
