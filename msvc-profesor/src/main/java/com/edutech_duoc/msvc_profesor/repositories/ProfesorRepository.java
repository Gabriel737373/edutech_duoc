package com.edutech_duoc.msvc_profesor.repositories;

import com.edutech_duoc.msvc_profesor.models.entities.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    Optional<Profesor> findByCorreoProfesor(String correoProfesor);
}
