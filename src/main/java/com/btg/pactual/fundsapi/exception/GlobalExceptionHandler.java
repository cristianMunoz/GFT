package com.btg.pactual.fundsapi.exception;

import com.btg.pactual.fundsapi.dto.ApiErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiErrorDTO error = new ApiErrorDTO("Recurso no encontrado", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiErrorDTO> handleInsufficientBalance(InsufficientBalanceException ex) {
        ApiErrorDTO error = new ApiErrorDTO("Conflicto de negocio", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}