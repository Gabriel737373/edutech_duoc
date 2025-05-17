package com.edutech.msvc.evaluacion.services;

import com.edutech.msvc.evaluacion.models.entities.Evaluacion;

import java.util.List;

public interface EvaluacionService {

    List<Evaluacion> findAll();
    Evaluacion findById(Long id);
    Evaluacion save (Evaluacion evaluacion);
    void deleteById(Long id);
    Evaluacion update(Long id, Evaluacion evaluacion);
}
