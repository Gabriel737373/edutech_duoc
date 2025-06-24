package com.edutech.msvc.alumno.repositories;


import com.edutech.msvc.alumno.models.entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    Optional<Alumno> findByCorreo(String correo);

}
