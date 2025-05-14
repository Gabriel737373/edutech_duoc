package com.edutech.msvc.resenias.repositories;

import com.edutech.msvc.resenias.models.entities.Resenias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReseniasRepository extends JpaRepository<Resenias, Long> {

    List<Resenias> findByIdAlumno(Long idAlumno);

}
