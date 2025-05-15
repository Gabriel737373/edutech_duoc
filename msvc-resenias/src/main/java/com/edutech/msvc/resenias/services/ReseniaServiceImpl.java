package com.edutech.msvc.resenias.services;

import com.edutech.msvc.resenias.clients.AlumnoClientRest;
import com.edutech.msvc.resenias.dtos.ReseniaDTO;
import com.edutech.msvc.resenias.models.entities.Resenia;
import com.edutech.msvc.resenias.repositories.ReseniaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReseniaServiceImpl implements ReseniaService {

    @Autowired
    private ReseniaRepository reseniaRepository;

    //ERROR NO SÉ POR QUÉ
    @Autowired
    private AlumnoClientRest alumnoClientRest;

    @Override
    public Resenia findById(Long id) {
        return null;
    }

    //DESARROLLAR MÉTODOS
    @Override
    public Resenia save(Resenia resenia) {
        return null;
    }

    @Override
    public List<Resenia> findByAlumnoId(Long alumnoId) {
        return List.of();
    }

    @Override
    public List<ReseniaDTO> findAll() {
        return List.of();
    }
}
