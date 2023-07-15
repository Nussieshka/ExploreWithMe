package ru.practicum.main_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.main_service.model.ApiError;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<ApiError> handleResponseStatusException(ResponseStatusException exception) {
        ApiError apiError = new ApiError(exception);
        return new ResponseEntity<>(apiError, exception.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> handleException(Exception exception) {
        ApiError apiError = new ApiError(exception, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
