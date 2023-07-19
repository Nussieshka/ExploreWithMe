package ru.practicum.main_service.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventDTO {
    @NotBlank
    private String annotation;

    @NotNull
    private CategoryDTO category;
    private Long confirmedRequests = 0L;

    @Future
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Long id;
    private EventUserDTO initiator;

    @NotNull
    private Boolean paid;

    @NotBlank
    private String title;
    private Long views = 0L;
}
