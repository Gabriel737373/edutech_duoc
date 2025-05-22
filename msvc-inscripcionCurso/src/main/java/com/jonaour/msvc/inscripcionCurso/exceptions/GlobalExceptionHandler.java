package com.jonaour.msvc.inscripcionCurso.exceptions;

import com.jonaour.msvc.inscripcionCurso.dtos.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InscripcionCursoException.class)
    public ResponseEntity<ErrorDTO> handleAlumnoException(InscripcionCursoException exception) {

        if (exception.getMessage().contains("no se encuentra en la base de datos")) {
            Map<String, String> errorMap = Collections.singletonMap("Inscripcion curso no encontrada", exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(this.createErrorDTO(HttpStatus.NOT_FOUND, new Date(), errorMap));

        } else {
            Map<String, String> errorMap = Collections.singletonMap("Inscripcion curso existente", exception.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(this.createErrorDTO(HttpStatus.CONFLICT, new Date(), errorMap));
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorMap.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(this.createErrorDTO(HttpStatus.BAD_REQUEST, new Date(), errorMap));
    }


    private ErrorDTO createErrorDTO(HttpStatus status, Date date, Map<String, String> errors) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(status.value());
        errorDTO.setDate(date);
        errorDTO.setErrors(errors);
        return errorDTO;
    }
}
