package com.edutech.msvc.resenias.repositories;

import com.edutech.msvc.resenias.models.entities.Resenia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReseniaRepository extends JpaRepository<Resenia, Long> {

    List<Resenia> findByIdAlumno(Long idAlumno);

}
