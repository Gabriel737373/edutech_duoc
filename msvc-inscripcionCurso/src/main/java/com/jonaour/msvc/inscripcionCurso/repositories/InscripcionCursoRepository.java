package com.jonaour.msvc.inscripcionCurso.repositories;

import com.jonaour.msvc.inscripcionCurso.models.entities.InscripcionCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscripcionCursoRepository extends JpaRepository<InscripcionCurso,Long> {
    List<InscripcionCurso> findByIdAlumno(Long idAlumno);
    List<InscripcionCurso> findByIdProfesor(Long idProfesor);
}
