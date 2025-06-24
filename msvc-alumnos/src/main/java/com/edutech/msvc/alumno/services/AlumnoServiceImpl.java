package com.edutech.msvc.alumno.services;

import com.edutech.msvc.alumno.exceptions.AlumnoException;
import com.edutech.msvc.alumno.models.entities.Alumno;
import com.edutech.msvc.alumno.repositories.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoServiceImpl implements AlumnoService{

    @Autowired
    private AlumnoRepository alumnoRepository;

    // Listar todos los Alumnos
    @Override
    public List<Alumno> findAll(){ return this.alumnoRepository.findAll();}

    // Buscar Alumnos por Id
    @Override
    public Alumno findById(Long id) {
        return this.alumnoRepository.findById(id).orElseThrow(
                () -> new AlumnoException("Alumno con id " +id+ " no encontrado")
        );
    }

    // Guardar Alumno
    @Override
    public Alumno save(Alumno alumno) {
        Optional<Alumno> alumnoExistente = alumnoRepository.findByCorreo(alumno.getCorreo());

        if (alumnoExistente.isPresent()) {
            throw new AlumnoException("Alumno existente");
        }

        alumno.setIdAlumno(null);
        return alumnoRepository.save(alumno);
    }

    // Eliminar Alumno por Id
    @Override
    public void deleteById(Long id) {
        alumnoRepository.deleteById(id);
    }

    // Actualizar Alumno por Id
    @Override
    public Alumno update(Long id, Alumno alumno) {
        Alumno alumnoEncontrado = alumnoRepository.findById(id)
                .orElseThrow(() -> new AlumnoException("Alumno con id "+id+" no encontrado"));
        alumnoEncontrado.setNombreCompleto(alumno.getNombreCompleto());
        alumnoEncontrado.setCorreo(alumno.getCorreo());
        return alumnoRepository.save(alumnoEncontrado);
    }
}
