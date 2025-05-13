package com.edutech_duoc.msvc_profesor.services;

import com.edutech_duoc.msvc_profesor.models.Profesor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfesorServiceMpl implements ProfesorService{

    @Autowired
    private ProfesorRepository profesorRepository;

}
