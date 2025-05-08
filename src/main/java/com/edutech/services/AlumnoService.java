package com.edutech.services;


import com.edutech.models.Alumno;

import java.util.List;

public interface AlumnoService {

    List<Alumno> findAll();
    Alumno findById(Long id);
    Alumno save (Alumno alumno);

}
