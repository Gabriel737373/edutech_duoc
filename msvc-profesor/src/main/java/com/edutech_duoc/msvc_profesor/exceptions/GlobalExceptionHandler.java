package com.edutech_duoc.msvc_profesor.exceptions;

import com.edutech_duoc.msvc_profesor.dtos.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProfesorException.class)
    public ResponseEntity<ErrorDTO> handProfesorException(ProfesorException exception){

        if (exception.getMessage().contains("No se encuentra al profesor")){

            Map<String, String> errorMap = Collections.singletonMap("Profesor no encontrado", exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(this.createErrorDTO(HttpStatus.NOT_FOUND, new Date(), errorMap));
        } else {
            Map<String, String> errorMap
            return null;
        }

    }

    private ErrorDTO createErrorDTO(HttpStatus status, Date date, Map<String, String> errors) {
        return null;
    }
}
