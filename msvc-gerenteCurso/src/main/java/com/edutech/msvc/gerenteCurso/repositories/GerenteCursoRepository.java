package com.edutech.msvc.gerenteCurso.repositories;

import com.edutech.msvc.gerenteCurso.models.entities.GerenteCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GerenteCursoRepository extends JpaRepository<GerenteCurso, Long> {
}
