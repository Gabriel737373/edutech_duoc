package com.edutech_duoc.msvc_profesor.services;

import com.edutech_duoc.msvc_profesor.models.entities.Profesor;

import java.util.List;

public interface ProfesorService {

    List<Profesor> findAll();
    Profesor findById(Long id);
    Profesor save (Profesor profesor);
    void deleteById(Long id);
    Profesor update(Long id, Profesor profesor);
}
