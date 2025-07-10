package com.edutech.msvc.evaluacion.repositories;

import com.edutech.msvc.evaluacion.models.entities.Evaluacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Long> {
    Optional<Evaluacion> findById(Long idEvaluacion);
}
