package ru.practicum.main_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompilationDTO {
    private List<EventDTO> events;
    private Long id;
    private Boolean pinned;
    private String title;
}
