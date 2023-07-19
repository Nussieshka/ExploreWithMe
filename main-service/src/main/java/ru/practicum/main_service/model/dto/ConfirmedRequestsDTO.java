package ru.practicum.main_service.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConfirmedRequestsDTO {
    private List<RequestDTO> confirmedRequests;
    private List<RequestDTO> rejectedRequests;
}
