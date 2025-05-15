package com.edutech_duoc.msvc_profesor.services;

import com.edutech_duoc.msvc_profesor.exceptions.ProfesorException;
import com.edutech_duoc.msvc_profesor.models.Profesor;
import com.edutech_duoc.msvc_profesor.repositories.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesorServiceMpl implements ProfesorService{

    @Autowired
    private ProfesorRepository profesorRepository;


    @Override
    public List<Profesor> findAll() {
        return this.profesorRepository.findAll();
    }

    @Override
    public Profesor findById(Long id) {
        return this.profesorRepository.findById(id).orElseThrow(
                () -> new ProfesorException("Profesor con id "+id+" no encontrado")
        );

    }

    @Override
    public Profesor save(Profesor profesor) {
        return this.profesorRepository.save(profesor);

    }

    @Override
    public void deleteById(Long id) {
        profesorRepository.deleteById(id);
    }

    @Override
    public Profesor update(Long id, Profesor profesor) {
        Profesor profesor1 = profesorRepository.findById(id)
                .orElseThrow(() -> new ProfesorException("Profesor con id \"+id+\" no encontrado"));
        profesor1.setNombreProfesor(profesor.getNombreProfesor());
        profesor1.setApellidoProfesor(profesor.getApellidoProfesor());
        profesor1.setCorreoProfesor(profesor.getCorreoProfesor());
        return profesorRepository.save(profesor1);
    }
}
