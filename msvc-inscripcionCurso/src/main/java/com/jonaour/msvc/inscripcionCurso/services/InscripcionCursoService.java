package com.jonaour.msvc.inscripcionCurso.services;

import com.jonaour.msvc.inscripcionCurso.dtos.InscripcionCursoDTO;
import com.jonaour.msvc.inscripcionCurso.models.entities.InscripcionCurso;

import java.util.List;

public interface InscripcionCursoService {

    InscripcionCurso findById(Long id);
    InscripcionCurso save(InscripcionCurso inscripcionCurso);
    List<InscripcionCurso> findbyAlumnoId(Long alumnoId); //REVISAR EN POST MAN POR EL ARGUMENTO
    List<InscripcionCurso> findbyProfesorId(Long profesorId); //REVISAR EN POST MAN POR EL ARGUMENTO
    List<InscripcionCurso> findbyCursoId(Long cursoId); //REVISAR EN POST MAN POR EL ARGUMENTO
    List<InscripcionCursoDTO> findAll();
    void deleteById(Long id);
    InscripcionCurso update(Long id, InscripcionCurso inscripcionCurso);

}
