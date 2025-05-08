package com.edutech.services;

import com.edutech.models.Alumno;
import com.edutech.repositories.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlumnoServiceImpl implements AlumnoService{

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Override
    public List<Alumno> findAll(){ return this.alumnoRepository.findAll();}

    @Override
    public Alumno findById(Long id) {
        return this.alumnoRepository.findById(id).orElseThrow(
                () -> new RuntimeException() //Crear alumnoexeption
        )
    }

    @Override
    public Alumno save(Alumno alumno) {
        return null;
    }

}
