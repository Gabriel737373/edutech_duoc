package com.jonaour.msvc.inscripcionCurso.services;

import com.jonaour.msvc.inscripcionCurso.dtos.InscripcionCursoDTO;
import com.jonaour.msvc.inscripcionCurso.models.entities.InscripcionCurso;

import java.util.List;

public interface InscripcionCursoService {

    InscripcionCurso findById(Long id);
    InscripcionCurso save(InscripcionCurso inscripcionCurso);
    List<InscripcionCurso> findbyAlumnoId(Long alumnoId);
    List<InscripcionCurso> findbyProfesorId(Long profesorId);
    List<InscripcionCurso> findbyCursoId(Long cursoId);
    List<InscripcionCurso> findByGerenteCursoId(Long gerenteCursoId);
    List<InscripcionCursoDTO> findAll();
    void deleteById(Long id);
    InscripcionCurso update(Long id, InscripcionCurso inscripcionCurso);

}
