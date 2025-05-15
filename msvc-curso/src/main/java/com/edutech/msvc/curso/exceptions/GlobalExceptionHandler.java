package com.edutech.msvc.curso.exceptions;

import com.edutech.msvc.curso.dtos.ErrorDTO;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CursoException.class)
    public ResponseEntity<ErrorDTO> handleCursoException(CursoException exception) {
        Map<String, String> errorMap;

        if (exception.getMessage().contains("no se encuentra en la base de datos")) {
            errorMap = Collections.singletonMap("Curso no encontrado", exception.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(this.createErrorDTO(HttpStatus.NOT_FOUND, new Date(), errorMap));
        } else {
            errorMap = Collections.singletonMap("Curso existente", exception.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(this.createErrorDTO(HttpStatus.CONFLICT, new Date(), errorMap));
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorMap.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(this.createErrorDTO(HttpStatus.BAD_REQUEST, new Date(), errorMap));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, String> errorMap = new HashMap<>();

        Throwable mostSpecificCause = ex.getMostSpecificCause();
        if (mostSpecificCause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) mostSpecificCause;
            String field = ife.getPath().get(0).getFieldName();
            String value = ife.getValue().toString();

            errorMap.put(field, "El valor '" + value + "' no es válido para el campo '" + field + "'.");
        } else {
            errorMap.put("json", "El formato del JSON no es válido.");
        }

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
