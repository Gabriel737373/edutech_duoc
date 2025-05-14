package com.edutech.msvc.alumno.services;

import com.edutech.msvc.alumno.models.entities.Alumno;

import java.util.List;

public interface AlumnoService {

    List<Alumno> findAll();
    Alumno findById(Long id);
    Alumno save (Alumno alumno);
    void deleteById(Long id);
    Alumno update(Long id, Alumno alumno);
}
