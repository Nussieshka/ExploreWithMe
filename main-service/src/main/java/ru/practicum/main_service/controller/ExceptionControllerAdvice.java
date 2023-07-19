package ru.practicum.main_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import ru.practicum.main_service.model.ApiError;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {
    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<ApiError> handleResponseStatusException(ResponseStatusException exception) {
        log.warn(exception.getMessage());

        ApiError apiError = new ApiError(exception);
        return new ResponseEntity<>(apiError, exception.getStatus());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> handleException(Exception exception) {
        log.error(exception.getMessage());

        ApiError apiError = new ApiError(exception, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class, IllegalArgumentException.class,
            MissingServletRequestParameterException.class})
    public ResponseEntity<ApiError> handleBadRequestExceptionTypes(Exception exception) {
        log.warn(exception.getMessage());
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiError apiError = new ApiError(exception, "Incorrectly made request.", status);
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class})
    public ResponseEntity<ApiError> handleConflictExceptionTypes(Exception exception) {
        log.warn(exception.getMessage());
        HttpStatus status = HttpStatus.CONFLICT;

        ApiError apiError = new ApiError(exception, "Integrity constraint has been violated.", status);
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<ApiError> handleNotFoundExceptionTypes(Exception exception) {
        log.warn(exception.getMessage());
        HttpStatus status = HttpStatus.NOT_FOUND;

        ApiError apiError = new ApiError(exception, "The required object was not found.", status);
        return new ResponseEntity<>(apiError, status);
    }
}
