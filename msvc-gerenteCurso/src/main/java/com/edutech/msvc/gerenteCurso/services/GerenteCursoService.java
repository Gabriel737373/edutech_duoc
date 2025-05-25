package com.edutech.msvc.gerenteCurso.services;

import com.edutech.msvc.gerenteCurso.models.entities.GerenteCurso;

import java.util.List;

public interface GerenteCursoService {
    List<GerenteCurso> findAll();
    GerenteCurso findById(Long id);
    GerenteCurso save (GerenteCurso gerenteCurso);
    void deleteById(Long id);
    GerenteCurso update(Long id, GerenteCurso gerenteCurso);
}
