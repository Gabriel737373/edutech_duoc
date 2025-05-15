package com.edutech_duoc.msvc_profesor.repositories;

import com.edutech_duoc.msvc_profesor.models.entities.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
}
