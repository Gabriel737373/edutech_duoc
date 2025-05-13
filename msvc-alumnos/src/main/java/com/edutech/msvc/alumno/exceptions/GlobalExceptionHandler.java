package com.edutech.msvc.alumno.exceptions;

import com.edutech.msvc.alumno.dtos.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlumnoException.class)
    public ResponseEntity<ErrorDTO> handleAlumnoException(AlumnoException exception) {

        if (exception.getMessage().contains("no se encuentra en la base de datos")) {
            Map<String, String> errorMap = Collections.singletonMap("Alumno no encontrado", exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(this.createErrorDTO(HttpStatus.NOT_FOUND, new Date(), errorMap));

        } else {
            Map<String, String> errorMap = Collections.singletonMap("Alumno existente", exception.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(this.createErrorDTO(HttpStatus.CONFLICT, new Date(), errorMap));
        }
    }

    private ErrorDTO createErrorDTO(HttpStatus status, Date date, Map<String, String> errors) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(status.value());
        errorDTO.setDate(date);
        errorDTO.setErrors(errors);
        return errorDTO;
    }
}