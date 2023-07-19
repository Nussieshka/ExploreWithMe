package ru.practicum.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Void> handleException(Exception exception) {
        log.error(exception.getMessage());

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<Void> handleException(ResponseStatusException exception) {
        log.error(exception.getMessage());

        return new ResponseEntity<>(exception.getStatus());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity<Void> handleException(MissingServletRequestParameterException exception) {
        log.error(exception.getMessage());

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
