package com.edutech.msvc.curso.services;

import com.edutech.msvc.curso.exceptions.CursoException;
import com.edutech.msvc.curso.models.Curso;
import com.edutech.msvc.curso.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoServiceImpl implements CursoService{

    @Autowired
    private CursoRepository cursoRepository;

    // Listar todos los cursos
    @Override
    public List<Curso> findAll() { return cursoRepository.findAll();}

    // Buscar curso por Id
    @Override
    public Curso findById(Long id) {
        return this.cursoRepository.findById(id).orElseThrow(
                () -> new CursoException("El curso con id "+id+" no existe")
        );
    }

    // Guardar curso
    @Override
    public Curso save(Curso curso) {
        return this.cursoRepository.save(curso);
    }

    // Eliminar por Id
    @Override
    public void deleteById(Long id) {
        cursoRepository.deleteById(id);
    }

    // Actualizar curso por Id
    @Override
    public Curso update(Long id, Curso curso) {
        Curso cursoEncontrado = cursoRepository.findById(id)
                .orElseThrow(() -> new CursoException("Curso con id "+id+" no encontrado"));
        cursoEncontrado.setNombreCurso(curso.getNombreCurso());
        cursoEncontrado.setDescripcionCurso(curso.getDescripcionCurso());
        cursoEncontrado.setPrecioCurso(curso.getPrecioCurso());
        return cursoRepository.save(cursoEncontrado);
    }

}
