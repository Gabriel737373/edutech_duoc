package com.edutech.msvc.gerenteCurso.services;

import com.edutech.msvc.gerenteCurso.exceptions.GerenteCursoException;
import com.edutech.msvc.gerenteCurso.models.entities.GerenteCurso;
import com.edutech.msvc.gerenteCurso.repositories.GerenteCursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerenteCursoImpl implements GerenteCursoService {

    @Autowired
    private GerenteCursoRepository gerenteRepository;

    // Metodo para listar todos los gerentes
    @Override
    public List<GerenteCurso> findAll() {
        return this.gerenteRepository.findAll();
    }

    // Metodo para buscar gerente por id
    @Override
    public GerenteCurso findById(Long id) {
        return this.gerenteRepository.findById(id).orElseThrow(
                () -> new GerenteCursoException("Gerente con id " + id + " no encontrado")
        );
    }

    // Metodo para guardar gerentes
    @Override
    public GerenteCurso save(GerenteCurso gerenteCurso) {
        return this.gerenteRepository.save(gerenteCurso);
    }

    // Metodo para eliminar gerentes por id
    @Override
    public void deleteById(Long id) {
        this.gerenteRepository.deleteById(id);
    }

    // Metodo par actualizar gerente por id
    @Override
    public GerenteCurso update(Long id, GerenteCurso gerenteCurso) {
        GerenteCurso gerenteEncontrado = gerenteRepository.findById(id).orElseThrow(
                () -> new GerenteCursoException("Gerente con id " + id + " no encontrado")
        );
        gerenteEncontrado.setNombreCompleto(gerenteCurso.getNombreCompleto());
        return gerenteRepository.save(gerenteEncontrado);
    }
}
