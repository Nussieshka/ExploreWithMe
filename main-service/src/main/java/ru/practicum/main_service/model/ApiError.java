package ru.practicum.main_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiError {
    private HttpStatus status;
    private String reason;
    private String message;

    private List<StackTraceElement> errors;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public ApiError(Exception exception, String reason, HttpStatus status) {
        initialize(exception);
        this.reason = reason;
        this.status = status;
    }

    public ApiError(ResponseStatusException exception) {
        initialize(exception);
        this.reason = exception.getReason();
        this.status = exception.getStatus();
    }

    private void initialize(Exception exception) {
        this.timestamp = LocalDateTime.now();
        this.errors = Arrays.asList(exception.getStackTrace());
        this.message = exception.getMessage();
    }
}
