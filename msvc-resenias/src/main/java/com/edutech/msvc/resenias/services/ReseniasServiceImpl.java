package com.edutech.msvc.resenias.services;

import com.edutech.msvc.resenias.clients.AlumnoClientRest;
import com.edutech.msvc.resenias.dtos.ReseniaDTO;
import com.edutech.msvc.resenias.models.entities.Resenias;
import com.edutech.msvc.resenias.repositories.ReseniasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReseniasServiceImpl implements ReseniasService {

    @Autowired
    private ReseniasRepository reseniasRepository;

    //ERROR NO SÉ POR QUÉ
    @Autowired
    private AlumnoClientRest alumnoClientRest;

    @Override
    public Resenias findById(Long id) {
        return null;
    }

    //DESARROLLAR MÉTODOS
    @Override
    public Resenias save(Resenias resenias) {
        return null;
    }

    @Override
    public List<Resenias> findByAlumno(Long alumnoId) {
        return List.of();
    }

    @Override
    public List<ReseniaDTO> findAll() {
        return List.of();
    }
}
