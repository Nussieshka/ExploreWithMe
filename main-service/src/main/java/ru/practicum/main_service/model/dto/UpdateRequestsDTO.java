package ru.practicum.main_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.main_service.model.RequestStatus;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateRequestsDTO {
    private List<Long> requestsIds;
    private RequestStatus requestStatus;
}
