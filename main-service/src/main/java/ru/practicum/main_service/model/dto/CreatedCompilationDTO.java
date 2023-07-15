package ru.practicum.main_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatedCompilationDTO {
    private List<Long> events;
    private Boolean pinned;
    private String title;
}
