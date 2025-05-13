package com.edutech.msvc.curso.repositories;

import com.edutech.msvc.curso.models.Curso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long> {
}
