package com.edutech.msvc.resenias.services;

import com.edutech.msvc.resenias.dtos.ReseniaDTO;
import com.edutech.msvc.resenias.models.entities.Resenia;

import java.util.List;

public interface ReseniaService {

    Resenia findById(Long id);
    Resenia save(Resenia resenia);
    List<Resenia> findByAlumnoId(Long alumnoId);   //REVISAR EN POSTMAN POR EL ARGUMENTO
    List<ReseniaDTO> findAll();
    void deleteById(Long id);
    Resenia update(Long id, Resenia resenia);

}
