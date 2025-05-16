package com.edutech.msvc.resenias.services;

import com.edutech.msvc.resenias.clients.AlumnoClientRest;
import com.edutech.msvc.resenias.dtos.AlumnoDTO;
import com.edutech.msvc.resenias.dtos.ReseniaDTO;
import com.edutech.msvc.resenias.exceptions.ReseniaException;
import com.edutech.msvc.resenias.models.Alumno;
import com.edutech.msvc.resenias.models.entities.Resenia;
import com.edutech.msvc.resenias.repositories.ReseniaRepository;
import feign.FeignException;
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
        return this.reseniaRepository.findById(id).orElseThrow(
                ()->new ReseniaException("La reseña con id: " + id + " no existe en la base de datos")
        );
    }

    //DESARROLLAR MÉTODOS
    @Override
    public Resenia save(Resenia resenia) {
        try{
            Alumno alumno=this.alumnoClientRest.findById(resenia.getIdAlumno());
        }catch (FeignException ex){
            throw new ReseniaException("Existen problemas con la asociación reseña-alumno");
        }
        return this.reseniaRepository.save(resenia);
    }

    @Override
    public List<Resenia> findByAlumnoId(Long alumnoId) {
        return this.reseniaRepository.findByIdAlumno(alumnoId);
    }

    @Override
    public List<ReseniaDTO> findAll() {
        return this.reseniaRepository.findAll().stream().map(resenia -> {
            Alumno alumno = null;
            try{
                alumno = this.alumnoClientRest.findById(resenia.getIdAlumno());
            }catch (FeignException ex){
                throw new ReseniaException("El alumno no existe en la base de datos");
            }

            AlumnoDTO alumnoDTO = new AlumnoDTO();
            alumnoDTO.setCorreo(alumno.getCorreo());
            alumnoDTO.setNombreCompleto(alumno.getNombreCompleto());

            ReseniaDTO reseniaDTO = new ReseniaDTO();
            reseniaDTO.setAlumnoDTO(alumnoDTO);
            return reseniaDTO;
        }).toList();
    }
}
