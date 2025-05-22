package com.jonaour.msvc.inscripcionCurso.services;

import com.jonaour.msvc.inscripcionCurso.clients.AlumnoClientRest;
import com.jonaour.msvc.inscripcionCurso.clients.CursoClientRest;
import com.jonaour.msvc.inscripcionCurso.clients.ProfesorClientRest;
import com.jonaour.msvc.inscripcionCurso.dtos.AlumnoDTO;
import com.jonaour.msvc.inscripcionCurso.dtos.CursoDTO;
import com.jonaour.msvc.inscripcionCurso.dtos.InscripcionCursoDTO;
import com.jonaour.msvc.inscripcionCurso.dtos.ProfesorDTO;
import com.jonaour.msvc.inscripcionCurso.exceptions.InscripcionCursoException;
import com.jonaour.msvc.inscripcionCurso.models.Alumno;
import com.jonaour.msvc.inscripcionCurso.models.Curso;
import com.jonaour.msvc.inscripcionCurso.models.Profesor;
import com.jonaour.msvc.inscripcionCurso.models.entities.InscripcionCurso;
import com.jonaour.msvc.inscripcionCurso.repositories.InscripcionCursoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscripcionCursoServiceImpl implements InscripcionCursoService{

    @Autowired
    private AlumnoClientRest alumnoClientRest;

    @Autowired
    private ProfesorClientRest profesorClientRest;

    @Autowired
    private InscripcionCursoRepository inscripcionCursoRepository;

    @Autowired
    private CursoClientRest cursoClientRest;

    @Override
    public InscripcionCurso findById(Long id) {
        return this.inscripcionCursoRepository.findById(id).orElseThrow(
                ()->new InscripcionCursoException("La inscripcion con ID: "+id+" no existen en la base de datos")
        );
    }

    @Override
    public InscripcionCurso save(InscripcionCurso inscripcionCurso) {
        try{
            Alumno alumno=this.alumnoClientRest.findById(inscripcionCurso.getIdAlumno());
            Profesor profesor=this.profesorClientRest.findById(inscripcionCurso.getIdProfesor());
            Curso curso=this.cursoClientRest.findById(inscripcionCurso.getIdCurso());
        }catch (FeignException ex){
            throw new InscripcionCursoException("Existen problemas al asociar alumno-profesor-curso");
        }
        return this.inscripcionCursoRepository.save(inscripcionCurso);
    }

    @Override
    public List<InscripcionCurso> findbyAlumnoId(Long alumnoId) {
        return this.inscripcionCursoRepository.findByIdAlumno(alumnoId);
    }

    @Override
    public List<InscripcionCurso> findbyProfesorId(Long profesorId) {
        return this.inscripcionCursoRepository.findByIdProfesor(profesorId);
    }

    @Override
    public List<InscripcionCurso> findbyCursoId(Long cursoId) {
        return this.inscripcionCursoRepository.findByIdCurso(cursoId);
    }

    @Override
    public List<InscripcionCursoDTO> findAll() {
        return this.inscripcionCursoRepository.findAll().stream().map(inscripcionCurso -> {

            Alumno alumno=null;
            try{
                alumno=this.alumnoClientRest.findById(inscripcionCurso.getIdAlumno());
            }catch (FeignException ex){
                throw new InscripcionCursoException("El alumno buscado no existe");
            }

            Profesor profesor=null;
            try{
                profesor=this.profesorClientRest.findById(inscripcionCurso.getIdProfesor());
            }catch (FeignException ex){
                throw new InscripcionCursoException("El profesor buscado no existe");
            }

            Curso curso=null;
            try {
                curso=this.cursoClientRest.findById(inscripcionCurso.getIdCurso());
            }catch (FeignException ex){
                throw new InscripcionCursoException("El curso buscado no existe");
            }

            AlumnoDTO alumnoDTO=new AlumnoDTO();
            alumnoDTO.setCorreo(alumno.getCorreo());
            alumnoDTO.setNombreCompleto(alumno.getNombreCompleto());

            ProfesorDTO profesorDTO=new ProfesorDTO();
            profesorDTO.setNombreProfesor(profesor.getNombreProfesor());
            profesorDTO.setApellidoProfesor(profesor.getApellidoProfesor());
            profesorDTO.setCorreoProfesor(profesor.getCorreoProfesor());

            CursoDTO cursoDTO=new CursoDTO();
            cursoDTO.setPrecioCurso(curso.getPrecioCurso());
            cursoDTO.setNombreCurso(curso.getNombreCurso());
            cursoDTO.setDescripcionCurso(curso.getDescripcionCurso());

            InscripcionCursoDTO inscripcionCursoDTO=new InscripcionCursoDTO();
            inscripcionCursoDTO.setAlumno(alumnoDTO);
            inscripcionCursoDTO.setProfesor(profesorDTO);
            inscripcionCursoDTO.setCurso(cursoDTO);
            return inscripcionCursoDTO;

        }).toList();
    }

    @Override
    public void deleteById(Long id) {
        inscripcionCursoRepository.deleteById(id);
    }

    @Override
    public InscripcionCurso update(Long id, InscripcionCurso inscripcionCurso) {
        InscripcionCurso inscripcionCursoFind=inscripcionCursoRepository.findById(id).orElseThrow(
                ()->new InscripcionCursoException("Inscripcion con ID: "+id+" no encontrada"));
        inscripcionCursoFind.setCostoInscripcion(inscripcionCurso.getCostoInscripcion());
        inscripcionCursoFind.setFechaInscripcion(inscripcionCurso.getFechaInscripcion());
        inscripcionCursoFind.setIdProfesor(inscripcionCurso.getIdProfesor());
        inscripcionCursoFind.setIdAlumno(inscripcionCurso.getIdAlumno());
        inscripcionCursoFind.setIdCurso(inscripcionCurso.getIdCurso());
        return inscripcionCursoRepository.save(inscripcionCursoFind);
    }
}
