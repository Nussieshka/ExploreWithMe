package ru.practicum.main_service.util;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MessageResponseStatusException extends ResponseStatusException {

    private final String message;

    public MessageResponseStatusException(HttpStatus status, String reason, String message) {
        super(status, reason);
        this.message = message;
    }

    public MessageResponseStatusException(HttpStatus status, String reason, Exception nested) {
        super(status, reason);
        this.message = nested.getMessage();
    }

    @Override
    public String getMessage() {
        return NestedExceptionUtils.buildMessage(message, this.getCause());
    }
}
