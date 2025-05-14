package com.edutech.msvc.resenias.services;

import com.edutech.msvc.resenias.dtos.ReseniaDTO;
import com.edutech.msvc.resenias.models.entities.Resenias;

import java.util.List;

public interface ReseniasService {

    Resenias findById(Long id);
    Resenias save(Resenias resenias);
    List<Resenias> findByAlumno(Long alumnoId);
    List<ReseniaDTO> findAll();
}
